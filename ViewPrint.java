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
		super(sd, sd.getTitle(),true); //get타이틀로 내용 입력
		
		//바탕색 설정
		backImage = new ImageIcon("e:/dev/img.png");
		
		JLabel background;
		background=new JLabel( "",backImage,JLabel.CENTER ); //라벨 바탕 이미지
		background.setBounds(0, 0, 700, 500); //라벨 크기
		add(background);
		
		//"<html>내용<br />   <-줄바꾸 내용 "
		jbtnClose = new JButton("닫기");
		jlblResult=new JLabel("\n\t\t\t출 력 결 과");
		jtaView = new JTextArea(    //
				"\n\n\n\n\t1. 최다사용 키의 이름과 횟수  |  " + sde.maxUsedKey() + "\n\n"
                + "\t2.브라우저별 접속횟수, 비율\n\t"+sde.connectBrowser() + "\n"
                + "\t3."+ sde.servicePerform() + "\n\n"
                + "\t4. 요청이 가장 많은 시간 | " + sde.mostRequestTime() + "\n\n"
                + "\t5." + sde.unNormalRequest() + "\n\n"
                + "\t6." + sde.requestError() + "\n\n"
                + "\t7. 입력되는 라인에 해당하는 정보출력 | "
                + sde.maxUsedValue()
    );
		
		jtaView.setOpaque(false);
		jlblResult.setOpaque(false);
		
		jtaView.setFont(new Font( "맑은고딕", Font.BOLD ,  14 )); //출력값 라벨 폰트
		
		jlblResult.setFont(new Font( "궁서체", Font.BOLD, 24 ));//출력결과 라벨 폰트

		jbtnClose.setFont(new Font( "궁서체", Font.BOLD,  16 ));//버튼 폰트
		jbtnClose.setBackground( new Color( 0xCB1209 ) );//버튼 배경색 
		jbtnClose.setForeground( new Color ( 0xF3D11D ) );//라벨 글자색
		
		background.add(jbtnClose);
		background.add(jtaView);
		background.add(jlblResult);
		
		
		//'닫기'버튼, 내용출력라벨 크기설정.
		jbtnClose.setBounds(300, 500, 70, 40);
		jtaView.setBounds(0, 0, 700, 500);
		jlblResult.setBounds(260, 10, 200, 50 );
		
		//이벤트 등록
		ViewPrintEvt vpe=new ViewPrintEvt(this);
		
		//버튼이벤트
		jbtnClose.addActionListener(vpe);
		
		//윈도우이벤트
		addWindowListener(vpe);
		
		//윈도우 사이즈 설정.
		setBounds(sd.getX()+100, sd.getY()+20, 700, 600);
		setVisible( true );
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
		
	}//ViewPrint
	
	
	
	
	

}//class
