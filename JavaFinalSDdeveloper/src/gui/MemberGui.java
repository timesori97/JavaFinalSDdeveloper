package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberGui extends JFrame  {
   private JLabel label1;
   private JLabel label2;
   private JLabel label3;
   private JLabel label4;
   MemberGui() {
      setTitle("회원관리");
      setSize(300,200);
      setLocationRelativeTo(null);
      setLayout(new GridBagLayout());
      getContentPane().setBackground(Color.WHITE);
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(4,4,4,4);
      setResizable(false);  // 창 크기 변경 불가
      
      label1 = new JLabel("1.회원 등록");
      label2 = new JLabel("2.회원 삭제");
      label3 = new JLabel("3.아이디 찾기");
      label4 = new JLabel("4.비밀번호 찾기");
      
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 2;
      JSeparator separator1 = new JSeparator();
      separator1.setForeground(Color.BLACK);
      separator1.setPreferredSize(new Dimension(250, 1)); // 직선의 크기 명확히 설정
      add(separator1, gbc);
      
      gbc.gridx = 0;
      gbc.gridy = 2;
      gbc.anchor = GridBagConstraints.WEST;
      label1.setForeground(Color.BLACK);
      add(label1,gbc);
      
      gbc.gridx = 0;
      gbc.gridy = 3;
      label2.setForeground(Color.BLACK);
      add(label2,gbc);
      
      gbc.gridx = 0;
      gbc.gridy = 4;
      label3.setForeground(Color.BLACK);
      add(label3,gbc);
      
      gbc.gridx = 0;
      gbc.gridy = 5;
      label4.setForeground(Color.BLACK);
      add(label4,gbc);
      
      gbc.gridx = 0;
      gbc.gridy = 7;
      gbc.gridwidth = 2;
      JSeparator separator2 = new JSeparator();
      separator2.setPreferredSize(new Dimension(250, 1)); // 직선의 크기 명확히 설정
      separator2.setForeground(Color.BLACK);
      add(separator2, gbc);
      setVisible(true);
      
      label1.addMouseListener(new MouseAdapter() {
    	  @Override
    	  public void mouseClicked(MouseEvent e) {
    		  new AddGui(); 
    	  }
    	  
    	  @Override
          public void mouseEntered(MouseEvent e) {
             label1.setForeground(Color.BLUE); // 마우스를 올렸을 때 색 변경
          }

          @Override
          public void mouseExited(MouseEvent e) {
             label1.setForeground(Color.BLACK); // 마우스가 벗어났을 때 원래 색으로 복구
          }
      });
   
      
      
      
      label2.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
             new DGui();
          }
          @Override
          public void mouseEntered(MouseEvent e) {
             label2.setForeground(Color.BLUE); // 마우스를 올렸을 때 색 변경
          }

          @Override
          public void mouseExited(MouseEvent e) {
             label2.setForeground(Color.BLACK); // 마우스가 벗어났을 때 원래 색으로 복구
          }
       });
      
      
      
      
      label3.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
        	  new SearchIdGui();
          }
          @Override
          public void mouseEntered(MouseEvent e) {
             label3.setForeground(Color.BLUE); // 마우스를 올렸을 때 색 변경
          }

          @Override
          public void mouseExited(MouseEvent e) {
             label3.setForeground(Color.BLACK); // 마우스가 벗어났을 때 원래 색으로 복구
          }
       });
      
      
      
      label4.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
        	  new SearchPassGui();
          }
          @Override
          public void mouseEntered(MouseEvent e) {
             label4.setForeground(Color.BLUE); // 마우스를 올렸을 때 색 변경
          }

          @Override
          public void mouseExited(MouseEvent e) {
             label4.setForeground(Color.BLACK); // 마우스가 벗어났을 때 원래 색으로 복구
          }
       });
   }
   




   
}

