package serverpack;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginPage extends JFrame{

	JPanel cardPanel;
	LoginPage lp;
	CardLayout card;
	Container c = getContentPane();
	
	public boolean getFlag(boolean flag) {
		
		return flag;
		
	}
//	public static void main(String[] args) {
//		
//		LoginPage lp = new LoginPage();
//		lp.setFrame(lp);
//
//	}

	// 기본 레이아웃 설정
	 void setFrame(LoginPage lp2) {
		
		JFrame jf = new JFrame();
		LoginPanel lp = new LoginPanel(lp2);
		SingupPage sp = new SingupPage(lp2);
		// cardLayout
		card = new CardLayout();
		
		jf.setTitle("Multi Caht Server");
		// cardLayout -> JPanel -> cardPanel
		cardPanel = new JPanel(card);
		cardPanel.add(lp.mainPanel, "Login");
		cardPanel.add(sp.mainPanel, "Register");

		// 제공 되는 Frame에 cardPanel 추가
		jf.add(cardPanel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(500, 700);
		jf.setVisible(true);
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {

		Connection conn = null;

		// 1. JDBC Driver Class
		String driver = "com.mysql.jdbc.Driver";

		// 2. 데이터베이스에 연결하기 위한 정보
		String url = "jdbc:mysql://localhost:3306/chat"; // 연결문자열, localhost - 127.0.0.1
		String user = "root"; // 데이터베이스 ID
		String pw = "ckdwns4028"; // 데이터베이스 PW

		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, pw);

		return conn;
	}
}

class LoginPanel extends JPanel implements ActionListener {

	JPanel mainPanel;
	JTextField idTextField;
	JPasswordField pwTextField;

	String userMode = "일반";
	LoginPage lp;
	Font font = new Font("회원가입", Font.BOLD, 30);
	String admin = "admin";
	String status = "";
	
			

	public LoginPanel(LoginPage lp) {
		this.lp = lp;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5, 1));

		JPanel centerPanel = new JPanel();
		JLabel loginLabel = new JLabel("JAVA-CHAT-PROGRAM");
		loginLabel.setFont(font);
		centerPanel.add(loginLabel);

		JPanel userPanel = new JPanel();

		// GridBagLayout -- ?
		JPanel gridBagidInfo = new JPanel(new GridBagLayout());
		gridBagidInfo.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		// GridBagConstraints -- ?
		GridBagConstraints c = new GridBagConstraints();

		JLabel idLabel = new JLabel("아이디 :");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		gridBagidInfo.add(idLabel, c);

		idTextField = new JTextField(15);
		c.insets = new Insets(0, 5, 0, 0);
		c.gridx = 1;
		c.gridy = 0;
		gridBagidInfo.add(idTextField, c);

		JLabel passLabel = new JLabel(" 비밀번호 : ");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20, 0, 0, 0);
		gridBagidInfo.add(passLabel, c);

		pwTextField = new JPasswordField(15);
		c.insets = new Insets(20, 5, 0, 0);
		c.gridx = 1;
		c.gridy = 1;
		gridBagidInfo.add(pwTextField, c);

		JPanel loginPanel = new JPanel();
		JButton loginButton = new JButton("로그인");
		loginPanel.add(loginButton);

		JPanel signupPanel = new JPanel();
		JButton signupButton = new JButton("회원가입");
		loginPanel.add(signupButton);

		mainPanel.add(centerPanel);
		mainPanel.add(userPanel);
		mainPanel.add(gridBagidInfo);
		mainPanel.add(loginPanel);
		mainPanel.add(signupPanel);
		

		loginButton.addActionListener(this);

		signupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				lp.card.next(lp.cardPanel);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton jb = (JButton) e.getSource();

		switch (e.getActionCommand()) {

		case "일반":
			userMode = "일반";
			break;

		case "관리자":
			userMode = "관리자";
			break;

		case "로그인":

			String id = idTextField.getText();
			String pass = pwTextField.getText();

			try {

				String sql_query = String.format("SELECT pw FROM REGISTER_TABLE WHERE id = '%s' AND pw = '%s'", id,
						pass);
				Connection conn = lp.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(sql_query);
				rset.next();
				
//				LoginPage lp = new LoginPage();
				
				if (pass.equals(rset.getString(1))) {
					JOptionPane.showMessageDialog(this, "Login Success", "로그인 성공", 1);
//					System.out.println(e.getActionCommand()); 
//					status = e.getActionCommand();
//					lp.card.next(new ChattingServer(lp));
//					Client ci = new Client();
//					Main.showServer(flag);
				
//					lp.getFlag(flag);
					
				} else {
					JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 1);
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 1);
				System.out.println("SQLException" + ex);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			break;
		}
		
		System.out.println(status);
		if(e.getActionCommand().equals("로그인")) {
		}
		status = "로그인";

	}
//	System.out.println(status);
}
