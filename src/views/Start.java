package views;

import java.awt.*;
import javax.swing.*;

public class Start extends JPanel {
	Main m;
	JButton start;
	
	Start() {
		start = new JButton("start");
		start.setSize(30, 40);
		start.setLayout(new GridBagLayout());
		this.add(start);
	}
}
