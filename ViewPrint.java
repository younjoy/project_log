package project;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ViewPrint extends JDialog {
	private JButton jbtnClose;
	static JTextArea jtaView;
	static JLabel jlblResult;
	private SelectDialogEvt sde;
	private ImageIcon backImage;
	
	
	
	
	public ViewPrint(SelectDialog sd, SelectDialogEvt sde) throws IOException {
		super(sd, sd.getTitle(),true); //getŸ��Ʋ�� ���� �Է�
		
		//������ ����
		backImage = new ImageIcon("e:/dev/img.png");
		
		JLabel background;
		background=new JLabel( "",backImage,JLabel.CENTER ); //�� ���� �̹���
		background.setBounds(0, 0, 700, 500); //�� ũ��
		add(background);
		
		//"<html>����<br />   <-�ٹٲ� ���� "
		jbtnClose = new JButton("�ݱ�");
		jlblResult=new JLabel("\n\t\t\t�� �� �� ��");
		jtaView = new JTextArea(    //
				"\n\n\n\n\t1. �ִٻ�� Ű�� �̸��� Ƚ��  |  " + sde.maxUsedKey() + "\n\n"
                + "\t2.�������� ����Ƚ��, ����\n\t"+sde.connectBrowser() + "\n"
                + "\t3."+ sde.servicePerform() + "\n\n"
                + "\t4. ��û�� ���� ���� �ð� | " + sde.mostRequestTime() + "\n\n"
                + "\t5." + sde.unNormalRequest() + "\n\n"
                + "\t6." + sde.requestError() + "\n\n"
                + "\t7. �ԷµǴ� ���ο� �ش��ϴ� ������� | "
                + sde.maxUsedValue()
    );
		
		jtaView.setOpaque(false);
		jlblResult.setOpaque(false);
		
		jtaView.setFont(new Font( "�������", Font.BOLD ,  14 )); //��°� �� ��Ʈ
		
		jlblResult.setFont(new Font( "�ü�ü", Font.BOLD, 24 ));//��°�� �� ��Ʈ

		jbtnClose.setFont(new Font( "�ü�ü", Font.BOLD,  16 ));//��ư ��Ʈ
		jbtnClose.setBackground( new Color( 0xCB1209 ) );//��ư ���� 
		jbtnClose.setForeground( new Color ( 0xF3D11D ) );//�� ���ڻ�
		
		background.add(jbtnClose);
		background.add(jtaView);
		background.add(jlblResult);
		
		
		//'�ݱ�'��ư, ������¶� ũ�⼳��.
		jbtnClose.setBounds(300, 500, 70, 40);
		jtaView.setBounds(0, 0, 700, 500);
		jlblResult.setBounds(260, 10, 200, 50 );
		
		//�̺�Ʈ ���
		ViewPrintEvt vpe=new ViewPrintEvt(this);
		
		//��ư�̺�Ʈ
		jbtnClose.addActionListener(vpe);
		
		//�������̺�Ʈ
		addWindowListener(vpe);
		
		//������ ������ ����.
		setBounds(sd.getX()+100, sd.getY()+20, 700, 600);
		setVisible( true );
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		
	}//ViewPrint
	
	
	
	
	

}//class
