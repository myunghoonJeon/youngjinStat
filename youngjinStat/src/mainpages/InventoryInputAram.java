package mainpages;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InventoryInputAram extends JFrame implements ActionListener{
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JButton jb = new JButton("OK");
	public InventoryInputAram(String msg) {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(200,100);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setLayout(new GridLayout(2,0));
		super.add(jp1);
		jb.addActionListener(this);
		jp1.add(new JLabel(msg));
		jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp2.add(jb);
		super.add(jp2);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jb){
			this.dispose();
		}
		
	}
}
