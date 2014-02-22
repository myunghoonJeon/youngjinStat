import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InventoryInputPopup extends JFrame implements ActionListener{
	/********************************************/
	JButton purchaseBtn = new JButton("Purchase");
	JButton suppliedBtn = new JButton("Supplied");
	/********************************************/
	JPanel p;
	public void addactionListner(){
		purchaseBtn.addActionListener(this);
		suppliedBtn.addActionListener(this);
	}
	
	public InventoryInputPopup() {
		super("");
		super.setVisible(true);
		super.setResizable(false);
		super.setSize(300,200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int y = (int)(screen.height/2 - frm.height/2);
		int x = (int)(screen.width/2 - frm.width/2);
		super.setLocation(x,y);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addactionListner();
		setLayout();
	}
	
	public void setLayout(){
		p = new JPanel();
		JPanel center = new JPanel();
		autoCreateBorderLayout(p, 10, 10, 10, 30);
		super.add(p);
		centerLayout(center);
		p.add("Center",center);		
	}
	
	public void centerLayout(JPanel jp){
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		jp.setLayout(new GridLayout(2,0));
			jp.add(p1);
				p1.add(new JLabel("[ SELECT ]"));
			jp.add(p2);
				p2.setLayout(new GridLayout(0,2,10,10));
				p2.add(purchaseBtn);
				p2.add(suppliedBtn);
	}
	
	public void autoCreateBorderLayout(JPanel a,int wx, int ex, int ny, int sy){
		a.setLayout(new BorderLayout());
		JPanel[] j =  new JPanel[4];
		Dimension d=null;
		for(int i=0;i<4;i++){
			j[i] = new JPanel();
			if(i == 0){
				d = new Dimension(wx,0);
			}
			if(i == 1){
				d = new Dimension(ex,0);
			}
			if(i==2){
				d = new Dimension(0,ny);
			}
			if(i==3){
				d = new Dimension(0,sy);
			}
			j[i].setPreferredSize(d);
			
		}
		a.add("North",j[2]);
		a.add("West",j[0]);
		a.add("East",j[1]);
		a.add("South",j[3]);
		validate();
	}
	
	public void purchase(){
		p.removeAll();
//		p = new JPanel();
		p.setLayout(new GridLayout(4,0));
		JPanel[] jl = new JPanel[4];
		for(int i=0;i<4;i++){
			jl[i] = new JPanel();
			jl[i].add(new JLabel(i+""));
			p.add(jl[i]);
		}
		super.add(p);
		validate();
	}
	public void supplied(){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == purchaseBtn){
			System.out.println("purchase click");
			purchase();
		}
		
		else if(e.getSource() == suppliedBtn){
			System.out.println("supplied click");
			supplied();
		}
		else{
			System.out.println("???");
		}
	}
	
}
