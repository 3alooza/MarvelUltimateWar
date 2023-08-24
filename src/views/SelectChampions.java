package views;

import java.awt.*;
import javax.swing.*;

public class SelectChampions extends JPanel {
	JButton button1;
	JPanel next;
	JPanel chmp;
	
	SelectChampions() {
		this.setLayout(new BorderLayout());
		button1 = new JButton("next");
		button1.setBackground(Color.BLACK);
		button1.setForeground(Color.RED);
		button1.setBounds(0, 0, 40, 30);
		next = new JPanel();
		next.add(button1);
		chmp = new JPanel();
		chmp.setLayout(new GridLayout(0,3));
		chmp.setBackground(Color.ORANGE);
		this.add(next,BorderLayout.NORTH);
		this.add(chmp,BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
		
		
	
	}
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setBounds(10, 10, 600, 600);
		f.add(new SelectChampions());
	}
}