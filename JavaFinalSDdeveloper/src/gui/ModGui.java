package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import membership.MembershipDAO;
import membership.MembershipDTO;

public class ModGui extends JFrame implements ActionListener {
   private MembershipDTO member = new MembershipDTO();
   private MembershipDTO dto = new MembershipDTO();
   private MembershipDAO dao = new MembershipDAO();
   private JLabel sid = new JLabel("수정할 아이디");
   private JLabel spass = new JLabel("수정할 비밀번호");
   private JLabel spnum = new JLabel("수정할 전화번호");
   private JButton ok = new JButton("수정하기");
   private JTextField id;
   private JTextField pass;
   private JTextField pnum;
   private ImageIcon backgroundImage;
   private JPanel panel;

   ModGui(MembershipDTO member) {
      this.member = member;
      backgroundImage = new ImageIcon("D:\\javasrc\\Oracle123\\image/image.jpg");

      this.setSize(400, 400);

      id = new JTextField();
      pass = new JTextField();
      pnum = new JTextField();

      id.setBounds(130, 40, 130, 30);
      sid.setBounds(150, 65, 130, 30);
      pass.setBounds(130, 115, 130, 30);
      spass.setBounds(150, 140, 130, 30);
      pnum.setBounds(130, 190, 130, 30);
      spnum.setBounds(150, 215, 130, 30);
      ok.setBounds(130, 300, 130, 30);
      this.add(id);
      this.add(pass);
      this.add(pnum);
      this.add(spnum);
      this.add(sid);
      this.add(spass);
      this.add(ok);

      setLayout(null);
      ok.addActionListener(this);
      setLocationRelativeTo(null); // 화면 중앙에 배치

      setVisible(true);

   }

   @Override
   public void actionPerformed(ActionEvent arg0) {
      // TODO Auto-generated method stub
      boolean a = true;
   
      if (arg0.getSource() == ok) {
         if (id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "수정할 아이디를 작성하세요.", "알림", JOptionPane.ERROR_MESSAGE);
            a = false;
            return;
         }
         if (pass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "수정할 비밀번호를 작성하세요.", "알림", JOptionPane.ERROR_MESSAGE);
            a = false;
            return;
         }
         if (pnum.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "수정할 전화번호를 작성하세요.", "알림", JOptionPane.ERROR_MESSAGE);
            a = false;
            return;
         }
         if (a == true) {

            dto.setId(id.getText());
            dto.setPass(pass.getText());
            dto.setPnum(pnum.getText());
            dao.update(dto, member);
            setVisible(false);
         }
      }

   }
}