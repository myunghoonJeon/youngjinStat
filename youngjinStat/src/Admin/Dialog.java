package Admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dialog extends JFrame implements ActionListener{
	JButton okBtn= new JButton("OK");
	JLabel information = new JLabel();
	JPanel mainP = new JPanel();
	JPanel southP = new JPanel();
	public Dialog(String msg){
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(300,100);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initLayout(msg);
		addActionListner();
	}
	
	public void initLayout(String msg){
		super.setLayout(new BorderLayout());
		information.setText(msg);
		super.add("Center",mainP);
			mainP.add(information);
		super.add("South",southP);
			southP.add(okBtn);
	}
	
	public void addActionListner(){
		okBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == okBtn){
			this.dispose();
		}
		
	}
	
}
