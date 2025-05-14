package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import gui.GUi;
import gui.QuaryDAOGui;
import membership.MembershipDTO;
import superconn.SuperCon;

public class Login extends SuperCon {
   private Connection conn = null;
   

   public Login(MembershipDTO member) {
	      init();
	      conn = getConnection();
	      if (conn != null) {
	         PreparedStatement stmt = null;
	         ResultSet rs = null;
	         String sql = "SELECT pass FROM membership WHERE id=? and pnum=?";
	         try {
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, member.getId());
	            stmt.setString(2, member.getPnum());
	            rs = stmt.executeQuery();
	            if (rs.next()) { 
	               String storedPass = rs.getString("pass");
	               if (storedPass != null && storedPass.equals(member.getPass())) {
	                  
	                 new QuaryDAOGui(member);
	                  
	               } else {
	                  // 비밀번호 불일

	                  
	                  JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
	               
	               }
	            } else {
	               // 아이디가 존재하지 않음
	               JOptionPane.showMessageDialog(null, "아이디또는 전화번호가 일치하지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
	            }
	         } catch (SQLException e) {
	            
	         } finally {
	            // 자원 해제
	            try {
	               if (rs != null)
	                  rs.close();
	               if (stmt != null)
	                  stmt.close();
	               if (conn != null)
	                  conn.close();
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         }
	      }

	   }


}
