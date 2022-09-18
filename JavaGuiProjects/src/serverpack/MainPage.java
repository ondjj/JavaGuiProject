package serverpack;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class MainPage extends JFrame {

	JLabel lbl, la1, la2, la3;
	JTextField id;
	JPasswordField passwd;
	JPanel idPanel, paPanel, loginPanel;
	JButton b1, b2;
	JTextArea content;

	public static void main(String[] args) {

		new MainPage();

	}

	MainPage() {

		setTitle("JAVA-CHAT-PROGRAM");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocation(450, 100);
		setLayout(null);

		// FlowLayout사용
		setLayout(new FlowLayout(FlowLayout.CENTER, 130, 30));

		// Border로 영역 생성
		EtchedBorder eborder = new EtchedBorder();

		// 레이블 생성
		lbl = new JLabel("아이디와 패스워드를 입력해 주세요");

		// 레이블에 영역 만들기
		lbl.setBorder(eborder);
		// 레이블 추가
		add(lbl);
		// id패널과 pw 패널생성
		idPanel = new JPanel();
		paPanel = new JPanel();
		la3 = new JLabel("아이디");
		la2 = new JLabel("패스워드");
		// id텍스트필드와 pw텍스트 필드 선언
		id = new JTextField(10);
		passwd = new JPasswordField(10);
		idPanel.add(la3);
		idPanel.add(id);
		paPanel.add(la2);
		paPanel.add(passwd);
		// 로그인과 회원가입을 위한 패널 생성
		loginPanel = new JPanel();
		b1 = new JButton("로그인");
		b2 = new JButton("회원가입");
		loginPanel.add(b1);
		loginPanel.add(b2);
		add(idPanel);
		add(paPanel);
		add(loginPanel);

		// 3행 20열 영역의 텍스트에어리어
		content = new JTextArea(15, 40);
		String str = "The quick, brown fox jumps over a lazy dog. DJs flock by when MTV ax quiz prog. Junk MTV quiz graced by fox whelps. Bawds jog, flick quartz, vex nymphs. Waltz, bad nymph, for quick jigs vex! Fox nymphs grab quick-jived waltz. Brick quiz whangs jumpy veldt fox. Bright vixens jump; dozy fowl quack. Quick wafting zephyrs vex bold Jim. Quick zephyrs blow, vexing daft Jim. Sex-charged fop blew my junk TV quiz. How quickly daft jumping zebras vex. Two driven jocks help fax my big quiz. Quick, Baz, get my woven flax jodhpurs! \"Now fax quiz Jack!\" my brave ghost pled. Five quacking zephyrs jolt my wax bed. Flummoxed by job, kvetching W. zaps Iraq. Cozy sphinx waves quart jug of bad milk. A very bad quack might jinx zippy fowls. Few quips galvanized the mock jury box. Quick brown dogs jump over the lazy fox. The jay, pig, fox, zebra, and my wolves quack! Blowzy red vixens fight for a quick jump. Joaquin Phoenix was gazed by MTV for luck. A wizard’s job is to vex chumps quickly in fog. Watch \"Jeopardy!\", Alex Trebek's fun TV quiz game. Woven silk pyjamas exchanged for blue quartz. Brawny gods just'";
		for (int i = 0; i < str.length(); i++) {
			int dat = str.indexOf("The");
			String in = str.substring(dat);
			content.append(in+"\n");
		}
		JScrollPane s = new JScrollPane(content);
		add(s);
		setVisible(true);
	}

}
