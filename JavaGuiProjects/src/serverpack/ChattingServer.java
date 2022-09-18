package serverpack;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChattingServer extends JFrame {
	private ArrayList<MultiServerThread> list;
	private Socket socket;
	private JTextArea ta;
	private JTextField tf;

	private ServerSocket serverSocket = null;

	public ChattingServer() {
		setTitle("다중 채팅 서버");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ta = new JTextArea();
		add(new JScrollPane(ta));
		tf = new JTextField();
		tf.setEditable(false);
		add(tf, BorderLayout.SOUTH);
		setSize(300, 300);
		setVisible(true);

		list = new ArrayList<MultiServerThread>();
		try {
			ServerSocket serverSocket = new ServerSocket(5000);
			MultiServerThread mst = null;
			boolean isStop = true;
			tf.setText("서버 정상 실행 중입니다. 관리자님!!!");
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
				ta.append(socket.getInetAddress() + "IP주소의 사용자께서 종료하셨습니다.\n");
				tf.setText("남은 사용자 수 : " + list.size());
			} catch (Exception e) {
				list.remove(this);
				ta.append(socket.getInetAddress() + "IP주소의 사용자께서 비정상 종료하셨습니다.");
				tf.setText("남은 사용자 수 : " + list.size());
			}
		} /// run

		public void broadCasting(String message) {
			System.out.println("broadcasting is called");
			for (MultiServerThread ct : list) {
				ct.send(message);
			} // for
		} // broadCasting

		public void send(String message) {
			pw.println(message);
		}
	} // MultiServerThread
}
