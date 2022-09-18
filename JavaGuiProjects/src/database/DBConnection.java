package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnection() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","ckdwns4028");
			
			
		}catch(Exception e) {
			System.out.println("데이터 베이스 연결 오류 :" + e.getMessage());
		}
	}

}
