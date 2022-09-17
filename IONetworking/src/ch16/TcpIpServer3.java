package ch16;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		
		try {
			// 서버 소켓을 생성하여 7777번 포트와 결합 시킨다.
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime()+"서버가 준비되었습니다.");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				System.out.println(getTime()+"연결 요청을 기다립니다.");
				
				// 요청 대기 시간 설정 -> 5초
				// 5초동안 접속 요청이 없으면 SocketTimeOutException 발
				serverSocket.setSoTimeout(5*1000);
				Socket socket = serverSocket.accept();
				System.out.println(getTime()+socket.getInetAddress() + "로부터 연결 요청이 들어왔습니다.");
				
				// 소켓의 출력 스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);
				
				// 원격 소켓에 데이터 전
				dos.writeUTF("[NOTICE] Test Message1 from Server ");
				System.out.println(getTime()+"데이터를 전송했습니다.");
				
				// 스트림 / 소켓 종료
				dos.close();
				socket.close();
			}catch(SocketTimeoutException e) {
				System.out.println("지정된 시간동안 접속 요청이 없어 서버를 종료합니다.");
				System.exit(0);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static String getTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

}
