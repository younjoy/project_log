package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewPrintEvt extends WindowAdapter implements ActionListener {
	private ViewPrint vp;
	
	public ViewPrintEvt( ViewPrint vp) {
		this.vp=vp;
		
		
	}//ViewPrintEvt

	@Override
	public void actionPerformed(ActionEvent ae) {
		vp.dispose();

	}//actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		vp.dispose();
	}//windowClosing


}//ViewPrintEvt
