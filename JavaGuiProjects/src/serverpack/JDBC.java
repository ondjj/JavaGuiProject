package serverpack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

	public static void main(String[] args) {
		
		Connection conn = null;
		
		try {
			
			String driver = "com.mysql.jdbc.Driver";
			// MySQL 드라이버 로딩
			Class.forName(driver);
			System.out.println("Driver Loading Success.");
			
			// DB 연결
			String url = "jdbc:mysql://localhost:3306/chat";
			
			String user = "root"; // 데이터베이스 ID
			String pw = "ckdwns4028"; // 데이터베이스 PW
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			// USE SQL
			String sql = "CREATE TABLE REGISTER_TABLE (";
				sql += "id varchar(20) primary key,"; // id
				sql += "pw varchar(20) not null,"; // pw
				sql += "ckpw varchar(20) not null,"; // check pw
				sql += "name varchar(10) not null,"; // name
				sql += "birth varchar(8) not null,"; // birth day
//				sql += "gender radio, not null,"; // gender
				sql += "phone int(11) not null);";
				
			// Statement 객체를 통해 sql문 실행
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("use chat"); //chat라는 데이터 베이스에 접속
//			stmt.executeUpdate("create table if not exists REGISTER_TABLE"); //Example 이라는 테이블이 있을 경우 삭제
			// query 만들기
			StringBuilder sb = new StringBuilder();
//			String sql = sb
//					.append("id varchar(15) primary key,")
//					.append("pw varchar(20) not null,")
//					.append("ckpw varchar(20) not null,")
//					.append("name varchar(10), not null,")
//					.append("birth varchar(8), not null,")
//					.append("phone number(11), not null)")
//					.append(");").toString();
			
			// Statement의 execute(String sql)메소드를 사용 sql문 실행
			stmt.execute(sql);
//			boolean b = stmt.execute(sql);
//			
//			System.out.println("b="+b);
//			
			System.out.println("REGISTER_TABLE 생성 성공");
			
			// db 관련 자원 반납
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
			

			
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("SQL오류 - 테이블이 생성되어 있는지 확인하세요.");
			e.printStackTrace();
		}
	}

}
