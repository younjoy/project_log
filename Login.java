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
//1. ������ ������Ʈ ���
public class Login extends JFrame {
   //�̺�Ʈ���� ����� ������Ʈ �ν��Ͻ� ������ ����
   private ImageIcon jbtnImage,sistImage,backImage;
   private JButton jbtnLogin;
   private JLabel jlblId;
   private JLabel jlblPw;
   private JLabel jlblSist;
   static JTextField jtfId;
   private JPasswordField jtfPw;
   

   public Login() {
      super("�α׺м� �α���â");
      
     
      
      //2. ������Ʈ ����
      backImage = new ImageIcon("e:/dev/img.png");
      sistImage = new ImageIcon("e:/dev/img1.png");
      jbtnImage = new ImageIcon("e:/dev/img2.jpg");
      jbtnLogin = new JButton("�α���");
      jbtnLogin.setBackground( new Color( 0xEDFDF3 ) );
      jlblId = new JLabel("���̵�");
      jlblPw = new JLabel("��й�ȣ");
      jtfId = new JTextField();
      jlblSist = new JLabel(sistImage);
      jtfPw = new JPasswordField();
      //����Է½� ���̰��� ����. *
      jtfPw.setEchoChar('*');
      
      JLabel background;
      background=new JLabel( "",backImage,JLabel.CENTER );
      background.setBounds(0, 0, 700, 500);
      add(background);
      
      //���߰�
      background.add(jbtnLogin);
      background.add(jlblId);
      background.add(jlblPw);
      background.add(jtfId);
      background.add(jtfPw);
      background.add(jlblSist);
      //3. ��ġ ����
      setLayout(null);
      
      //��Ʈ ����
      jbtnLogin.setFont(new Font("",Font.BOLD, 17));
      jlblId.setFont(new Font("",Font.BOLD, 17));
      jlblPw.setFont(new Font("",Font.BOLD, 17));
      jtfId.setFont(new Font("",Font.PLAIN, 14));
      jtfPw.setFont(new Font("",Font.PLAIN, 14));
      
      
      //ũ�� ����
      jbtnLogin.setBounds(450,180,148,138);
      jlblId.setBounds(80,180,150,50);
      jtfId.setBounds(210, 180, 220, 50);
      jlblPw.setBounds(80, 270, 150, 50);
      jtfPw.setBounds(210, 270, 220, 50);
      jlblSist.setBounds(210, 30,220,100);
      
      //�̺�Ʈ ó��
      LoginEvt sle = new LoginEvt(this);////////////has a ����
      
      //�̺�Ʈ ����
      jbtnLogin.addActionListener(sle);
      jtfPw.addActionListener(sle);
      jtfId.addActionListener(sle);
      
      addWindowListener( sle );
      
      
      //������ ũ�� ����
      setBounds(200, 300, 700, 500);
      
      //����ȭ
      setVisible(true);
      
      //����
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