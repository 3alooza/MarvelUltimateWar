package views;

import java.awt.*;
import javax.swing.*;

public class InsertNames extends JPanel  {
	JTextField insertname1;
	JTextField insertname2;
	JButton play;
	
	InsertNames() {
		insertname1 = new JTextField(15);
		insertname1.setSize(400, 300);
		
		insertname2 = new JTextField(15);
		insertname2.setSize(400, 300);
		
		play = new JButton("play!!!");
		play.setSize(400, 300);
		
		this.add(insertname1);
		this.add(insertname2);
		this.setBackground(Color.orange);
		this.add(play);
		this.setLayout(new FlowLayout());
		this.validate();
		this.repaint();
	}
}
