package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class LoginEvt extends WindowAdapter implements ActionListener {
	private Login sl;
	
	public LoginEvt(Login sl) {
		this.sl = sl;
	}//SistLoginEvt

	@Override
	public void actionPerformed(ActionEvent ae) {
		inputPlz();
	}//actionPerformed
	
	public void inputPlz() {
		String id = sl.getJtfId().getText();
		String pw = new String(sl.getJtfPw().getPassword()); //*대신 String 문자열 받으려고
		
		if(id.isEmpty()) {
			JOptionPane.showMessageDialog( null , "Id를 입력해주세요.");
		}else if(pw.isEmpty()) {
			JOptionPane.showMessageDialog( null , "PassWord를 입력해주세요.");
		}else {
			compareLogin();
		}//end if
	}//inputPlz

	public void compareLogin() {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("admin", "1234");
		hm.put("root", "1111");
		hm.put("administrator", "12345");
		String key = sl.getJtfId().getText(); //id 값
		//sl.getJtfPw()는 JtfPw의 주소
		//JPasswordField는 getPassword메소드로 값을 가져올수 있고 그 값은 char[]에 담겨있다.
		//String클래스 생성자에 String(char[] value)로 비밀번호를 가져와
		//String에 넣어준다.
		String value = new String(sl.getJtfPw().getPassword()); //pw 값 
		if(hm.containsKey(key)) {
		    if(hm.get(key).equals(value)) {
		    	new SelectDialog(sl);
		    	sl.dispose();
		    	
		    }else {
		       JOptionPane.showMessageDialog( null , "PassWord 정보가 잘못되었습니다.");
		    }//end else
		 }else {
		    JOptionPane.showMessageDialog( null , "로그인 정보가 잘못되었습니다.");
		 }//end else
	
	}//compareLogin
	
	@Override
	public void windowClosing(WindowEvent e) {
		sl.dispose();
	}//windowClosing

	

}//SistLoginEvt
