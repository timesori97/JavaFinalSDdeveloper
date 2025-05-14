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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import membership.MembershipDAO;
import membership.MembershipDTO;

public class SearchPassGui extends JFrame implements ActionListener {
	private MembershipDAO membershipdao = new MembershipDAO();
	private JButton in;
	private JLabel id;
	private JLabel pass;
	private JLabel pnum;
	private JTextField idField;
	private JTextField passField;
	private JTextField pnumField;

	SearchPassGui() {
		setTitle("비밀번호 찾기");
		setSize(250, 200); // 윈도우 크기 확대
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		setResizable(false); // 창 크기 변경 불가

		in = new JButton("입력");
		id = new JLabel("아이디  :");
		pnum = new JLabel("전화번호  :");

		// 가로 크기를 늘린 필드
		idField = new JTextField(10); // 가로 크기 20으로 설정
		pnumField = new JTextField(10); // 가로 크기 20으로 설정

		// 아이디 라벨 및 필드
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST; // 왼쪽 정렬
		add(id, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // 텍스트 필드가 넓게 확장되도록
		gbc.gridwidth = 1;
		add(idField, gbc);

		// 전화번호 라벨 및 필드
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		add(pnum, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1.0; // 필드 넓히기
		gbc.gridwidth = 1;
		add(pnumField, gbc);

		// 버튼 추가 (한 줄 전체 차지)
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2; // 두 개의 열 병합
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		add(in, gbc);

		// ActionListener 등록

		in.addActionListener(this);

		this.setLocationRelativeTo(null); // 화면 중앙에 배치
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == in) {
	        String id = idField.getText();
	        String pnum = pnumField.getText();

	        // DAO에서 비밀번호 조회
	        String password = membershipdao.  getPasswordByIdAndPnum(id, pnum);

	        if (password != null) {
	            // 비밀번호를 SearchPassGuiwindow에 전달하여 출력
	            new SearchPassGuiWindow(password);
	            setVisible(false);
	        } else {
	            JOptionPane.showMessageDialog(null, "아이디 또는 전화번호가 잘못되었습니다.", "오류", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
}
