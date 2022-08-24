package View;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class invoiceHeaderForm {
	private JFrame frame1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					invoiceHeaderForm window = new invoiceHeaderForm();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
}
}
