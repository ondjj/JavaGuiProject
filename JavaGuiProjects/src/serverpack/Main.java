package serverpack;

public class Main {
	
	LoginPage lp;
	ChattingServer cs;
	LoginPanel lgp;

	public static void main(String[] args) {
		
		LoginPage lp = new LoginPage();
		lp.setFrame(lp);
		LoginPanel lgp = new LoginPanel(lp);
		
		if(lgp.flag) {
			showServer();
		}
	}
	
	public static void showServer() {
		Main main = new Main();
		new ChattingServer();

	}
	
	

}
