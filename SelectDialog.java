package project;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SelectDialog extends JDialog {

	private JButton jbtnView;
	private JButton jbtnReport;

	private JLabel jlblLine;
	private JLabel jlblFirstLine;
	private JLabel jlblLastLine;

	private JTextField jtfFirstLine;
	private JTextField jtfLastLine;
	private ImageIcon jbtnImage, backImage;

	private Login sl; // has a


	public SelectDialog(Login sl) {
		super(sl, "로그분석 선택창" ,true);
		sl.dispose();
		
		// FDD5C9 EDFDF3 <<- view report 색깔
		jbtnView = new JButton("VIEW");
		jbtnView.setBackground(new Color(0xEDFDF3));
		jbtnReport = new JButton("REPORT");
		jbtnReport.setBackground(new Color(0xF9DFD2));
		jlblLine = new JLabel("LINE을 입력하세요");
		jlblFirstLine = new JLabel("시작 LINE : ");
		jlblLastLine = new JLabel("끝 LINE : ");
		jtfFirstLine = new JTextField();
		jtfLastLine = new JTextField();
		backImage = new ImageIcon("e:/dev/img.png");

		JLabel background;
		background = new JLabel("", backImage, JLabel.CENTER);
		background.setBounds(0, 0, 700, 500);
		add(background);

		// 값추가
		background.add(jbtnView);
		background.add(jbtnReport);
		background.add(jlblLine);
		background.add(jlblFirstLine);
		background.add(jlblLastLine);

		background.add(jtfFirstLine);
		background.add(jtfLastLine);

		// 폰트 변경
		jbtnView.setFont(new Font("", Font.BOLD, 25));
		jbtnReport.setFont(new Font("", Font.BOLD, 25));
		jlblLine.setFont(new Font("", Font.BOLD, 17));
		jlblFirstLine.setFont(new Font("", Font.BOLD, 17));
		jlblLastLine.setFont(new Font("", Font.BOLD, 17));
		jtfFirstLine.setFont(new Font("", Font.PLAIN, 14));
		jtfLastLine.setFont(new Font("", Font.PLAIN, 14));

		// 수동배치
		setLayout(null);

		// 이벤트 등록
		SelectDialogEvt sde = new SelectDialogEvt(this);

		// 버튼이벤트
		jbtnView.addActionListener(sde);
		jbtnReport.addActionListener(sde);

		// 윈도우이벤트
		addWindowListener(sde);

		// 배치
		jbtnView.setBounds(60, 30, 250, 200);
		jbtnReport.setBounds(360, 30, 250, 200);

		jlblLine.setBounds(110, 300, 150, 50);
		jlblFirstLine.setBounds(320, 270, 100, 50);
		jlblLastLine.setBounds(320, 330, 100, 50);

		jtfFirstLine.setBounds(420, 270, 190, 50);
		jtfLastLine.setBounds(420, 330, 190, 50);

		setBounds(sl.getX() + 100, sl.getY() + 100, sl.getWidth(), sl.getHeight());
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	}// SelectDialog

	public JButton getJbtnView() {
		return jbtnView;
	}// getJbtnView

	public JButton getJbtnReport() {
		return jbtnReport;
	}// getJbtnReport

	public JTextField getJtfFirstLine() {
		return jtfFirstLine;
	}// getJtfFirstLine

	public JTextField getJtfLastLine() {
		return jtfLastLine;
	}// getJtfLastLine

}// class
