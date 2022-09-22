package serverpack;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

public class ChattingServer extends LoginPage {
	private static final long serialVersionUID = -551553759319169328L;
	private ArrayList<MultiServerThread> list;
	private Socket socket;
	private JTextArea ta;
	private JTextField tf;
	LoginPage lp;
	private JFrame jframe; // 윈도우창

	private ServerSocket serverSocket = null;

	public ChattingServer() {
//		this.lp = lp;
//		JPanel panel3 = new JPanel();
		setTitle("Multi Caht Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ta = new JTextArea();
		add(new JScrollPane(ta));
		tf = new JTextField();
		tf.setEditable(false);
		add(tf, BorderLayout.SOUTH);
		setSize(300, 300);
		setVisible(true);

		list = new ArrayList<MultiServerThread>();
		
		String setPort = JOptionPane.showInputDialog(jframe, "서버 포트를 입력하세요.", "포트입력 다이얼로그", JOptionPane.YES_NO_OPTION);
		int port = Integer.parseInt(setPort);
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			MultiServerThread mst = null;
			boolean isStop = true;
			tf.setText("Server is All Clean - port :" + port);
			System.out.println("isStop : " + isStop);
			while (isStop) {
				socket = serverSocket.accept();
				mst = new MultiServerThread();
				list.add(mst);
				mst.start();
			} // while
		} catch (IOException e) {
			e.printStackTrace();
		} // catch
	}// 생성자

	public static void main(String[] args) {
		new ChattingServer();
	}

	public class MultiServerThread extends Thread {
		InputStream is;
		BufferedReader br_in;
		BufferedWriter bw;
		PrintWriter pw;
		OutputStream os;
		String message;

		public void run() {
			System.out.println("MultiServerThread is called");
			boolean isStop = false;

			try {
				is = socket.getInputStream();
				os = socket.getOutputStream();

				br_in = new BufferedReader(new InputStreamReader(is));
				bw = new BufferedWriter(new OutputStreamWriter(os));
				pw = new PrintWriter(bw, true); // 자동으로 flush 한다.

				while (!isStop) {
					message = br_in.readLine();
					String[] str = message.split("#");

					if (str[1].equals("exit")) {
						broadCasting(message);
						isStop = true;
					} else {
						broadCasting(message);
					} // else
				} // while

				list.remove(this);
				ta.append(getTime()+"$"+socket.getInetAddress() + "IP주소의 사용자께서 종료하셨습니다.\n");
				tf.setText(getTime()+"$남은 사용자 수 : " + list.size());
			} catch (Exception e) {
				list.remove(this);
				ta.append(getTime()+"$"+socket.getInetAddress() + "IP주소의 사용자께서 비정상 종료하셨습니다.");
				tf.setText(getTime()+"$남은 사용자 수 : " + list.size());
			}
		} /// run

		public void broadCasting(String message) {
			System.out.println("broadcasting is called");
			for (MultiServerThread ct : list) {
				ct.send(message);
			} // for
		} // broadCasting

		public void send(String message) {
			pw.println(getTime()+"$"+message);
		}
	} // MultiServerThread
	
	private String getTime() {
//		String name = Thread.currentThread().getName();
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");

		return f.format(new Date());
	}
}