package serverpack;

public class Main {

	LoginPage lp;
	Client ci;
	LoginPanel lgp;

	public static void main(String[] args) {

		LoginPage lp = new LoginPage();
		lp.setFrame(lp);
	}
	
	public void showClient(LoginPage loginPage) {
		loginPage.dispose();
		Client ci = new Client();
		ci.init();
	}

}
