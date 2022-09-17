package ch16;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;

		try {
			// 서버 소켓을 생성해 7777번 포트와 결합(bind)
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime() + "서버가 준비되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				System.out.println(getTime() + "연결 요청을 기다립니다.");
				// 서버 소켓은 클라이언트의 연결 요청이 올 때까지 실행을 멈추고 기다린다.
				// 클라이언트의 연결 요청이 오면 클라이언트 소켓과 통신할 새로 소켓을 생성한다.
				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress() + "로부터 연결 요청이 들어왔습니다.");

				// 소켓의 출력 스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);

				// 원격 소켓(remote socket)에 데이터를 보낸다.
				
				dos.writeUTF("[NOTICE] Test Message from Server.");
				System.out.println(getTime() + "데이터를 전송했습니다.");

				// 스트림과 소켓을 닫아준다.
				dos.close();
				socket.close();
//				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 현재 시간을 문자열로 반환하는 함
	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

}
