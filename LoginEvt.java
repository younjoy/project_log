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
		String pw = new String(sl.getJtfPw().getPassword()); //*��� String ���ڿ� ��������
		
		if(id.isEmpty()) {
			JOptionPane.showMessageDialog( null , "Id�� �Է����ּ���.");
		}else if(pw.isEmpty()) {
			JOptionPane.showMessageDialog( null , "PassWord�� �Է����ּ���.");
		}else {
			compareLogin();
		}//end if
	}//inputPlz

	public void compareLogin() {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("admin", "1234");
		hm.put("root", "1111");
		hm.put("administrator", "12345");
		String key = sl.getJtfId().getText(); //id ��
		//sl.getJtfPw()�� JtfPw�� �ּ�
		//JPasswordField�� getPassword�޼ҵ�� ���� �����ü� �ְ� �� ���� char[]�� ����ִ�.
		//StringŬ���� �����ڿ� String(char[] value)�� ��й�ȣ�� ������
		//String�� �־��ش�.
		String value = new String(sl.getJtfPw().getPassword()); //pw �� 
		if(hm.containsKey(key)) {
		    if(hm.get(key).equals(value)) {
		    	new SelectDialog(sl);
		    	sl.dispose();
		    	
		    }else {
		       JOptionPane.showMessageDialog( null , "PassWord ������ �߸��Ǿ����ϴ�.");
		    }//end else
		 }else {
		    JOptionPane.showMessageDialog( null , "�α��� ������ �߸��Ǿ����ϴ�.");
		 }//end else
	
	}//compareLogin
	
	@Override
	public void windowClosing(WindowEvent e) {
		sl.dispose();
	}//windowClosing

	

}//SistLoginEvt
