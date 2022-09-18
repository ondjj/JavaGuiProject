package serverpack;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Client implements ActionListener, Runnable {
	private static final String SERVER_IP = "127.0.0.1";
	// 멤버 변수 정의
	private Socket socket; // 소켓
	private JFrame jframe; // 윈도우창
	private JTextField jtf; // 채팅 입력란
	private JTextArea jta; // 채팅 내용 보여주는 객체
	private JLabel jlb1, jlb2; // 라벨
	private JPanel jp1, jp2; // 판넬
	private String ip; // IP 주소를 저장할 변수
	private String chatName; // 닉네임(대화명) 저장할 변수
	private JButton jbtn; // 종료 버튼 준비

	InputStream is;
	OutputStream os;
	BufferedReader br_in;
	BufferedWriter bw = null;
	PrintWriter pw = null;

	public Client() {
		// 대화명을 다이얼로그로 입력 받는다.
		chatName = JOptionPane.showInputDialog(jframe, "닉네임을 입력하세요.", "대화명 입력 다이얼로그", JOptionPane.YES_NO_OPTION);
		if (chatName.length() == 0 || chatName == null) {
			System.exit(0);
		}

		ip = SERVER_IP; // IP 주소
		// 대화명
		jframe = new JFrame("Multi Chat Service Program");

		// 아래에 붙는 판넬 코드
		jp1 = new JPanel();// 아래 붙는 판넬
		jp1.setLayout(new BorderLayout());// 동서남북중앙 레이아웃
		jtf = new JTextField(30); // 30 글자
		jbtn = new JButton("종료");// 종료 버튼 생성
		jp1.add(jbtn, BorderLayout.EAST);
		jp1.add(jtf, BorderLayout.CENTER);
		// 위쪽에 붙는 판넬 코드
		jp2 = new JPanel();// 위쪽에 붙는 판넬
		jp2.setLayout(new BorderLayout());
		jlb1 = new JLabel("대화명 : [" + chatName + "]");// 대화명 [[홍길동]]
		jlb1.setBackground(Color.DARK_GRAY);
		jlb2 = new JLabel("서버 IP 주소 : " + ip);// IP 주소 : 127.0.0.1
		jlb2.setBackground(Color.DARK_GRAY);
		jp2.add(jlb1, BorderLayout.CENTER);
		jp2.add(jlb2, BorderLayout.EAST);
		// 프레임에 붙이는 코드
		jta = new JTextArea("", 10, 50); // 초기값, 행(세로), 가로(열)
		jta.setBackground(Color.DARK_GRAY);// 대화창 배경색 설정
		JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jframe.add(jp1, BorderLayout.SOUTH);
		jframe.add(jp2, BorderLayout.NORTH);
		jframe.add(jsp, BorderLayout.CENTER);
		// 감지기 붙이는 코드
		jtf.addActionListener(this);
		jbtn.addActionListener(this);
		jframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					pw.println(chatName + "#exit");// 채팅 종료
				} catch (Exception ee) {
					ee.printStackTrace();
				} // catch
				System.exit(0); // 프로그램 종료
			}// windowClosing

			@Override
			public void windowOpened(WindowEvent e) {
				jtf.requestFocus(); // jtf 에 포커스를 놓는다.
			}// windowOpened
		});// 윈도우 이벤트 처리 끝
		jta.setEditable(false);// 편집 X, 채팅 내용 보여주기만 가능
		// 크기 지정 코드
		jframe.pack();// 자동 크기 지정
		jframe.setResizable(false);// 창 크기 변경 X
		jframe.setVisible(true);// 보이기

	}// 생성자

	@Override
	public void actionPerformed(ActionEvent e) { // 이벤트 처리
		Object obj = e.getSource(); // 에벤트 발생 위치 얻기
		String msg = jtf.getText(); // 채팅 내용 입력 받기
		if (obj == jtf) { // 입력란에서 엔터를 친 경우
			if (msg == null || msg.length() == 0) {// 내용이 없는 경우
				// 경고창 보여주기
				JOptionPane.showMessageDialog(jframe, "글을 쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
			} else { // 내용을 입력하고 엔터한 경우
				try {
					pw.println(chatName + "#" + msg);
				} catch (Exception ee) {
					ee.printStackTrace();
				} // catch
				jtf.setText(""); // jtf 를 지운다.
			} // else : 내용 O
		} else if (obj == jbtn) { // 종료 버튼을 클릭한 경우
			try {
				pw.println(chatName + "#exit");
			} catch (Exception ee) {
				ee.printStackTrace();
			} // catch
			System.exit(0);
		} // else if : 종료 버튼
	}// actionPerformed

	public void init() {
		try {
			socket = new Socket(ip, 5000);
			is = socket.getInputStream();
			os = socket.getOutputStream();

			br_in = new BufferedReader(new InputStreamReader(is));
			bw = new BufferedWriter(new OutputStreamWriter(os));
			pw = new PrintWriter(bw, true); // 자동으로 flush 한다.

			Thread t = new Thread(this);
			t.start(); // 쓰레드 시작
		} catch (Exception e) {
			e.printStackTrace();
		} // catch
	}// init

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		Client cc = new Client();
		cc.init(); // 쓰레드 관련 코드 실행
	}// main

	@Override
	public void run() {
		String message = null;
		String[] receiveMsg = null;
		boolean isStop = false;
		while (!isStop) {
			try {
				message = (String) br_in.readLine();// 채팅내용
				receiveMsg = message.split("#");
			} catch (Exception e) {
				e.printStackTrace();
				isStop = true; // 반복문 종료로 설정
			} // catch
			System.out.println(receiveMsg[0] + ":" + receiveMsg[1]);
			if (receiveMsg[1].equals("exit")) { // 채팅 종료
				if (receiveMsg[0].equals(chatName)) { // 해당 사용자
					System.exit(0);
				} else { // 그 외의 사용자
					jta.append(receiveMsg[0] + " 님이 종료했습니다\n");
					jta.setCaretPosition(jta.getDocument().getLength());
				} // else : 그 외 사용자
			} else { // exit 가 아닐 경우
				// 채팅 내용을 화면에 보여 준다.
				jta.append(receiveMsg[0] + " : " + receiveMsg[1] + "\n");

				jta.setCaretPosition(jta.getDocument().getLength());
			} // else
		} // while
	}// run
}// end
