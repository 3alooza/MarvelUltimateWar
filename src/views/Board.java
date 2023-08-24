package views;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import model.world.*;

public class Board extends JPanel {
	JPanel names;
	JPanel grid;
	JPanel moves;
	BasicArrowButton moveup;
	BasicArrowButton movedown;
	BasicArrowButton moveright;
	BasicArrowButton moveleft;
	JButton attackup;
	JButton attackdown;
	JButton attackright;
	JButton attackleft;
	ToolTipManager tool;
	JButton player1;
	JButton player2;
	JButton Leader1;
	JButton Leader2;
	JButton endturn;
	JButton castabilitya;
	JButton castabilityad;
	JButton castabilityaxy;
	JButton effects;
	JButton turn;
	JButton whoLeader1;
	JButton whoLeader2;

	
	Board() {
		this.setLayout(new BorderLayout());
		moveup=new BasicArrowButton(BasicArrowButton.NORTH);
		movedown=new BasicArrowButton(BasicArrowButton.SOUTH);
		moveright=new BasicArrowButton(BasicArrowButton.EAST);
		moveleft=new BasicArrowButton(BasicArrowButton.WEST);
		attackdown = new JButton("attack down");
		attackup = new JButton("attack up");
		attackleft = new JButton("attack left");
		attackright = new JButton("attack right");
		castabilitya = new JButton("cast ability (a)");
		castabilityad = new JButton("cast ability (a, d)");
		castabilityaxy = new JButton("cast ability (a, x, y)");
		whoLeader1 = new JButton();
		whoLeader2 = new JButton();
		effects = new JButton("effects of current champion");
		turn = new JButton();

		names = new JPanel();
		names.setBackground(new Color(173,216,230));

		grid = new JPanel();
		grid.setBackground(Color.WHITE);
		moves = new JPanel();
		moves.setBackground(new Color(173,216,230));
		this.add(names, BorderLayout.NORTH);
		this.add(grid, BorderLayout.CENTER);
		this.add(moves, BorderLayout.SOUTH);
		grid.setLayout(new GridLayout(5, 5, 20, 20));

		Leader1 = new JButton();
		Leader1.setText("Use Leader Ability Player 1");
		Leader2 = new JButton();
		Leader2.setText("Use Leader Ability Player 2");
		player1 = new JButton(); // name?
		player2 = new JButton(); //name?
		endturn = new JButton("end turn");
		endturn.setBackground(Color.black);
		Leader1.setBackground(Color.red);
		Leader2.setBackground(Color.red);

		names.add(Leader1);
		names.add(Leader2);
		names.add(player1);
		names.add(player2);
		names.add(endturn);
		names.add(effects);
		names.add(whoLeader1);
		names.add(whoLeader2);
		names.add(turn);
		moves.add(attackup);
		moves.add(attackdown);
		moves.add(attackright);
		moves.add(attackleft);
		moves.add(moveup);
		moves.add(movedown);
		moves.add(moveleft);
		moves.add(moveright);
		moves.add(castabilitya);
		moves.add(castabilityad);
		moves.add(castabilityaxy);
	}
	
	public void refresh(Object[][] ba2looz) {
		grid.removeAll();
//		moves.removeAll();
//		names.removeAll();
//		names.add(Leader1);
//		names.add(Leader2);
//		names.add(player1);
//		names.add(player2);
//		names.add(endturn);
//		moves.add(attackup);
//		moves.add(attackdown);
//		moves.add(attackright);
//		moves.add(attackleft);
//		moves.add(moveup);
//		moves.add(movedown);
//		moves.add(moveleft);
//		moves.add(moveright);
//		moves.add(castabilitya);
//		moves.add(castabilityad);
//		moves.add(castabilityaxy);

		for (int x = 4; x >=0; x--) {
			for (int y = 0; y < 5; y++) {
				Object bo2loz = ba2looz[x][y];
				JButton button = new JButton();
				String s="";
				if (bo2loz instanceof Cover) {
					Cover c= (Cover) bo2loz;
					s= "cover, health: " + c.getCurrentHP();
				}
				
				else if (bo2loz instanceof Champion) {
					Champion c = (Champion) bo2loz;
					s = c.getName();
					String info = "";
					if (c instanceof Hero) {
						info += "hero";
					} else if (c instanceof Villain) {
						info += "villain";
					} else if (c instanceof AntiHero) {
						info += "antihero";
					}
					info += ", health: " + c.getCurrentHP() + ", mana: " + c.getMana() + ", action points: "
							+ c.getCurrentActionPoints() + ", abilities: ";
					for (int j = 0; j < c.getAbilities().size(); j++) {
						info += c.getAbilities().get(j).getName() + ", ";
					}
					info += ", attack range: " + c.getAttackRange() + ", attack damage: "
							+ c.getAttackDamage();
					
					button.setToolTipText(info);
				}

				button.setText(s);
				button.setBackground(Color.PINK);
				grid.add(button);
				grid.revalidate();
				names.revalidate();
				moves.revalidate();
		
			}
		}
	}
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.add(new Board());
	}
}
