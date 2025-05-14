package superconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import membership.MembershipDTO;

public class SuperCon {
	private Connection conn = null;
	private MembershipDTO dto;
	private String id;
	private String pass;
	private String url = "jdbc:oracle:thin:@//192.168.0.9:1521/orcl";
	Scanner in = new Scanner(System.in);

	public void init() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		id = "system";
		pass = "1111";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 로드 실패");
			e.printStackTrace();
		}

	}

	public Connection getConnection() {

		try {
			if (conn == null) {

				conn = DriverManager.getConnection(url, id, pass);
			}
		} catch (Exception e) {
			System.out.println("연결 오류");
			conn = null;
		}
		return conn;
	}

	public Connection getConn() {
		return conn;
	}

}