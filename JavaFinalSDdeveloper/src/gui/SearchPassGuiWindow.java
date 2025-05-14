package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import membership.MembershipDAO;

public class SearchPassGuiWindow extends JFrame {
	   private MembershipDAO membershipdao = new MembershipDAO();
	   private JTextField passField;
	  
	   
	   SearchPassGuiWindow(String password) {
	        setTitle("비밀번호 찾기");
	        setSize(250, 200);
	        setLayout(new GridBagLayout());
	        getContentPane().setBackground(Color.WHITE);
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(4, 4, 4, 4);
	        setResizable(false);

	        passField = new JTextField(password, 10);
	        passField.setEditable(false); // 수정 불가 설정

	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        gbc.weightx = 1.0;
	        gbc.gridwidth = 1;
	        gbc.anchor = GridBagConstraints.CENTER;
	        add(passField, gbc);

	        setLocationRelativeTo(null);
	        setVisible(true);
	    }

	
	
}
