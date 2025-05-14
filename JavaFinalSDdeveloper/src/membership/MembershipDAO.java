package membership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import javax.swing.JOptionPane;


import superconn.SuperCon;

public class MembershipDAO extends SuperCon {

	public MembershipDAO() {
	     

	   }

	   public void insert(MembershipDTO mdto) {

	      init();
	      Connection conn = null;
	      ResultSet rs = null;
	      PreparedStatement stmt = null;
	      boolean f = true;

	      try {
	         conn = getConnection(); // 연결을 가져옵니다.

	         if (conn != null) {
	            String sql = "SELECT id FROM membership WHERE pnum = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, mdto.getPnum());
	            
	            rs = stmt.executeQuery();
	            
	            if (mdto != null && rs.next()) {
	               if (rs.getString("id").equals(mdto.getId())) {
	                  JOptionPane.showMessageDialog(null, "회원님이 등록하신 아이디와 중복된 아이디 입니다.", "알림",
	                        JOptionPane.ERROR_MESSAGE);
	                  rs.close();
	                  return;
	               }
	            }
	            System.out.println("!");
	            sql = "INSERT INTO membership (id, pass, pnum) VALUES (?, ?, ?)";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, mdto.getId());
	            stmt.setString(2, mdto.getPass());
	            stmt.setString(3, mdto.getPnum());
	            
	            int result = stmt.executeUpdate();
	           
	            System.out.println(result + "건 삽입");
	            JOptionPane.showMessageDialog(null, "회원등록이 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.out.println("등록 실패");
	         JOptionPane.showMessageDialog(null, "회원등록이 실패하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
	      } finally {
	         // PreparedStatement 자원 해제
	         if (stmt != null)
	            try {
	               stmt.close();
	            

	            } catch (Exception e) {
	               e.printStackTrace();
	            }

	      }
	   }
	   
	   public void delete(MembershipDTO mdto) {
		      // TODO Auto-generated method stub
		      init();
		      ResultSet rs = null;
		      getConnection();
		      PreparedStatement stmt = null;
		      boolean f = true;
		      try {
		         if (getConnection() != null) {
		            String sql = "SELECT * FROM membership WHERE id=? and pass=? and pnum=?";
		            
		            stmt = getConn().prepareStatement(sql);
		            
		            stmt.setString(1, mdto.getId());
		           
		            stmt.setString(2, mdto.getPass());
		            
		            stmt.setString(3, mdto.getPnum());
		            
		            rs = stmt.executeQuery();
		        
		            if (rs.next()) {
		               System.out.println("1");
		               String sql2 = "DELETE FROM membership WHERE id=? AND pass=? AND pnum=?";
		               stmt = getConn().prepareStatement(sql2);
		               
		               stmt.setString(1, mdto.getId());
		               
		               stmt.setString(2, mdto.getPass());
		               
		               stmt.setString(3, mdto.getPnum());
		               
		               int a = stmt.executeUpdate();
		              
		               System.out.println(a + "건 삭제");
		               
		               JOptionPane.showMessageDialog(null, "삭제완료.", "알림", JOptionPane.INFORMATION_MESSAGE);
		            }
		         }
		      } catch (Exception e) {
		         e.printStackTrace();
		         JOptionPane.showMessageDialog(null, "틀린 정보입니다.", "알림", JOptionPane.ERROR_MESSAGE);
		         System.out.println("::::" + e); 
		      
		      } finally {
		         try {
		            stmt.close();
		            rs.close();
		         } catch (Exception e) {

		         }
		      }

		   }

	   public void update(MembershipDTO mdto, MembershipDTO member) {
		      // TODO Auto-generated method stub
		      init();
		      getConnection();
		      ResultSet rs = null;
		      PreparedStatement stmt = null;
		      boolean f = true;

		      try {
		         String sql = "SELECT id FROM membership WHERE pnum=?";
		         stmt = getConn().prepareStatement(sql);
		         stmt.setString(1, mdto.getPnum());
		         rs = stmt.executeQuery();

		         if (rs.next() && rs.getString("id").equals(mdto.getId())) {

		            JOptionPane.showMessageDialog(null, "이미 보유하신 아이디 입니다.", "알림", JOptionPane.ERROR_MESSAGE);

		         } else {
		            sql = "UPDATE membership SET id=?, pass=?, pnum=? WHERE id=? AND pass=? And pnum=?";
		            stmt = getConn().prepareStatement(sql);
		            stmt.setString(1, mdto.getId());
		            stmt.setString(2, mdto.getPass());
		            stmt.setString(3, mdto.getPnum());
		            stmt.setString(4, member.getId());
		            stmt.setString(5, member.getPass());
		            stmt.setString(6, member.getPnum());

		            int result = stmt.executeUpdate();
		            System.out.println(result + "건 수정");
		            JOptionPane.showMessageDialog(null, "수정완료.", "알림", JOptionPane.INFORMATION_MESSAGE);

		         }

		      } catch (Exception e) {
		         JOptionPane.showMessageDialog(null, "수정실패.", "알림", JOptionPane.ERROR_MESSAGE);
		         System.out.println("수정실패 ");
		         e.printStackTrace();
		      } finally {
		         try {
		            stmt.close();
		            rs.close();
		         } catch (Exception e) {

		         }
		      }

		   }


	   public String getPasswordByIdAndPnum(String id, String pnum) {
		      init();
		      getConnection();
		     
		   
		   String password = null;
		    String sql = "SELECT pass FROM membership WHERE id = ? AND pnum = ?";
		    
		    try (Connection conn = getConnection(); 
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setString(1, id);
		        stmt.setString(2, pnum);

		        try (ResultSet rs = stmt.executeQuery()) {
		            if (rs.next()) {
		                password = rs.getString("pass");
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return password;
		}
	   
	   public String getIdByPassAndPnum(String pass, String pnum) {
		      init();
		      getConnection();
		     
		   
		   String sid = null;
		    String sql = "SELECT id FROM membership WHERE pass = ? AND pnum = ?";
		    
		    try (Connection conn = getConnection(); 
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setString(1, pass);
		        stmt.setString(2, pnum);

		        try (ResultSet rs = stmt.executeQuery()) {
		            if (rs.next()) {
		                sid = rs.getString("id");
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return sid;
		}



}
