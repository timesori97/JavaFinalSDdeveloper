package gui;

import javax.swing.*;

import main.Login;
import membership.MembershipDTO;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUi extends JFrame implements ActionListener {

	   private JPanel panel;
	   private ImageIcon backgroundImage;
	   private JButton logBtn;
	   private JTextField idField;
	   private JTextField passField;
	   private String url = "jdbc:oracle:thin:@//192.168.0.9:1521/orcl"; // 데이터베이스 URL
	   private String dbid = "system"; // 데이터베이스 사용자명
	   private String dbPassword = "1111";
	   private String driver = "oracle.jdbc.driver.OracleDriver";
	   private JButton memberBtn;
	   private JTextField pnumField;
	   private JLabel idLabel;
	   private JLabel passLabel ;
	   private JLabel pnumLabel ;
	   private JButton pbutton ;
	   public GUi() {
	      backgroundImage = new ImageIcon("D:\\javasrc\\Oracle13\\image/image2.jpg");
	      panel = new JPanel() {
	         @Override
	         protected void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            if (backgroundImage != null) {
	               g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	         }
	      };
	      panel.setLayout(null); // 패널의 레이아웃을 null로 설정

	      this.setResizable(false);
	      this.setTitle("sql 프로그램");
	      idLabel = new JLabel("ID        :");
	       passLabel = new JLabel("PASS : ");
	       pnumLabel =new JLabel("PHONE :");
	       pnumField = new JTextField(11);
	       idField = new JTextField(11);
	      passField = new JTextField(11);
	      logBtn = new JButton("로그인");
	      memberBtn = new JButton("회원관리");
	      

	      idLabel.setSize(55, 20);
	      idLabel.setLocation(150, 110);
	      passLabel.setSize(55, 20);
	      passLabel.setLocation(150, 150);
	      pnumLabel.setSize(55, 20);
	      pnumLabel.setLocation(150, 190);
	      idField.setSize(100, 20);
	      idField.setLocation(200, 110);
	      passField.setSize(100, 20);
	      passField.setLocation(200, 150);
	      pnumField.setSize(100, 20);
	      pnumField.setLocation(200, 190);
	      logBtn.setSize(100, 20);
	      logBtn.setLocation(140, 240);
	      memberBtn.setSize(100, 20);
	      memberBtn.setLocation(260, 240);

	      setVisible(true);
	      setSize(500, 400);
	      setLocationRelativeTo(null);
	      setDefaultCloseOperation(EXIT_ON_CLOSE);

	      add(panel);
	      panel.add(idLabel);
	      panel.add(idField);
	      panel.add(passLabel);
	      panel.add(passField);
	      panel.add(logBtn);
	      panel.add(memberBtn);
	      panel.add(pnumLabel);
	      panel.add(pnumField);
	      logBtn.addActionListener(this);
	      memberBtn.addActionListener(this);
	      // 프레임의 크기 변경 이벤트 리스너 추가

	   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      // TODO Auto-generated method stub
      if (arg0.getSource() == logBtn) {

         String id = idField.getText();
         String pass = passField.getText();
         String pnum = pnumField.getText();
         MembershipDTO member = new MembershipDTO();
         member.setId(id);
         member.setPass(pass);
         member.setPnum(pnum);
         
         

         new Login(member);

      } else if (arg0.getSource() == memberBtn) {

         new MemberGui();

      }

   }

   

}