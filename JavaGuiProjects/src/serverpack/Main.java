package serverpack;

public class Main {

	LoginPage lp;
	ChattingServer cs;
	Client ci;
	LoginPanel lgp;

//	public boolean showServer(LoginPage lp) {
//
//		LoginPanel lgp = new LoginPanel(lp);
//
//		if (lp.getFlag(lgp.flag)) {
//			return true;
//		}
//		return false;
//	}

	public static void main(String[] args) {

		Main T = new Main();
		LoginPage lp = new LoginPage();
		lp.setFrame(lp);
		LoginPanel lgp = new LoginPanel(lp);
//		
//		if(lgp.flag) {
//			showServer();
//		}
//		Client ci = new Client();
		
		// LoginPage에서 로그인 성공시 flag를 받아 client를 실행 시키는 방향으로 진행
		if (lgp.status.equals("로그인")) {
			lp.setVisible(false);

			lp.dispose();

			Client ci = new Client();
			ci.init();
			
		}
		
		System.out.println(lgp.userMode.equals("로그인"));

	}

}
