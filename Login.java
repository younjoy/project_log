package project;


import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
//1. 윈도우 컴포넌트 상속
public class Login extends JFrame {
   //이벤트에서 사용할 컴포넌트 인스턴스 변수로 만듦
   private ImageIcon jbtnImage,sistImage,backImage;
   private JButton jbtnLogin;
   private JLabel jlblId;
   private JLabel jlblPw;
   private JLabel jlblSist;
   static JTextField jtfId;
   private JPasswordField jtfPw;
   

   public Login() {
      super("로그분석 로그인창");
      
     
      
      //2. 컴포넌트 생성
      backImage = new ImageIcon("e:/dev/img.png");
      sistImage = new ImageIcon("e:/dev/img1.png");
      jbtnImage = new ImageIcon("e:/dev/img2.jpg");
      jbtnLogin = new JButton("로그인");
      jbtnLogin.setBackground( new Color( 0xEDFDF3 ) );
      jlblId = new JLabel("아이디");
      jlblPw = new JLabel("비밀번호");
      jtfId = new JTextField();
      jlblSist = new JLabel(sistImage);
      jtfPw = new JPasswordField();
      //비번입력시 보이게할 문자. *
      jtfPw.setEchoChar('*');
      
      JLabel background;
      background=new JLabel( "",backImage,JLabel.CENTER );
      background.setBounds(0, 0, 700, 500);
      add(background);
      
      //값추가
      background.add(jbtnLogin);
      background.add(jlblId);
      background.add(jlblPw);
      background.add(jtfId);
      background.add(jtfPw);
      background.add(jlblSist);
      //3. 배치 수동
      setLayout(null);
      
      //폰트 변경
      jbtnLogin.setFont(new Font("",Font.BOLD, 17));
      jlblId.setFont(new Font("",Font.BOLD, 17));
      jlblPw.setFont(new Font("",Font.BOLD, 17));
      jtfId.setFont(new Font("",Font.PLAIN, 14));
      jtfPw.setFont(new Font("",Font.PLAIN, 14));
      
      
      //크기 설정
      jbtnLogin.setBounds(450,180,148,138);
      jlblId.setBounds(80,180,150,50);
      jtfId.setBounds(210, 180, 220, 50);
      jlblPw.setBounds(80, 270, 150, 50);
      jtfPw.setBounds(210, 270, 220, 50);
      jlblSist.setBounds(210, 30,220,100);
      
      //이벤트 처리
      LoginEvt sle = new LoginEvt(this);////////////has a 관계
      
      //이벤트 지정
      jbtnLogin.addActionListener(sle);
      jtfPw.addActionListener(sle);
      jtfId.addActionListener(sle);
      
      addWindowListener( sle );
      
      
      //윈도우 크기 설정
      setBounds(200, 300, 700, 500);
      
      //가시화
      setVisible(true);
      
      //종료
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      
   }//SistLogin


   public JTextField getJtfId() {
      return jtfId;
   }



   public JButton getJbtnLogin() {
      return jbtnLogin;
  }




   public JPasswordField getJtfPw() {
      return jtfPw;
   }





}//JFrame