package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import engine.*;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.world.*;
import javax.swing.text.html.*;

public class Main extends JFrame implements ActionListener {
	Game g;
	InsertNames i; // panel el text field wel play
	SelectChampions s; // grid el champions
	Board b;
	Start start;
	String name1;
	String name2;
	Player p1;
	Player p2;
	ArrayList<JButton> chmpBtns = new ArrayList();
	JOptionPane pane = new JOptionPane();
	JOptionPane ability = new JOptionPane();
	JLabel label;
	ToolTipManager tool;
	ArrayList<JButton> brdBtns = new ArrayList();
	ArrayList<Champion> pickedChmps = new ArrayList();
	JTextField x = new JTextField(15);
	JTextField y = new JTextField(15);
	JTextField direction = new JTextField(15);

	Main() {
		start = new Start();
		start.setBackground(Color.orange);
		start.start.addActionListener(this);

		this.setTitle("Marvel - Ultimate War");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.add(start);

		this.revalidate();
		this.repaint();
	}
	
	public void effectsOfCurrentChampion() {
		String q = "";
		for (int i = 0; i < g.getCurrentChampion().getAppliedEffects().size(); i++) {
			q += g.getCurrentChampion().getAppliedEffects().get(i).getName() + " (" + g.getCurrentChampion().getAppliedEffects().get(i).getDuration() + "), ";
		}
		b.effects.setToolTipText(q);
	}
	
	public void remainingChampions() {
	String s = "";
	String c = "";
	for (int i = 0; i < g.getFirstPlayer().getTeam().size(); i++) {
		s += infoOfFirstTeam(i) + "; "; // get info of first team, bc infoOfSelected loops over availablechampions
	}
	for (int i = 0; i < g.getSecondPlayer().getTeam().size(); i++) {
		c += infoOfSecondTeam(i); // get info of second team, bc infoOfSelected loops over
									// availablechampions
	}
	b.player1.setToolTipText(s);
	b.player2.setToolTipText(c);
	}
	
	public String printPQ(PriorityQueue a) {
		String ranroon= "";
		int i = 1;
		PriorityQueue tmp = new PriorityQueue(6);
		while (!a.isEmpty()) {
		Champion c = (Champion) a.peekMin();
		ranroon += i + ". " + c.getName() + " ";
		tmp.insert(a.remove());
		i++;
		}
		while (!tmp.isEmpty()) {
			a.insert(tmp.remove());
		}
		return ranroon;
	}
	
	public String infoOfSelected(int i) {
		Champion c = Game.getAvailableChampions().get(i);
		String info = c.getName();
		if (c instanceof Hero) {
			info += ", hero";
		} else if (c instanceof Villain) {
			info += ", villain";
		} else if (c instanceof AntiHero) {
			info += ", antihero";
		}
		info += ", health: " + c.getMaxHP() + ", mana: " + c.getMana() + ", action points: "
				+ c.getCurrentActionPoints() + ", abilities: ";
		for (int j = 0; j < c.getAbilities().size(); j++) {
			info += c.getAbilities().get(j).getName() + ", ";
		}
		info += ", attack range: " + Game.getAvailableChampions().get(i).getAttackRange() + ", attack damage: "
				+ Game.getAvailableChampions().get(i).getAttackDamage();

		return info;
	}

	public String infoOfFirstTeam(int i) {
		Champion c = g.getFirstPlayer().getTeam().get(i);
		String info = c.getName();
		if (c instanceof Hero) {
			info += ", hero";
		} else if (c instanceof Villain) {
			info += ", villain";
		} else if (c instanceof AntiHero) {
			info += ", antihero";
		}
		info += ", health: " + c.getMaxHP() + ", mana: " + c.getMana() + ", action points: "
				+ c.getCurrentActionPoints() + ", abilities: ";
		for (int j = 0; j < c.getAbilities().size(); j++) {
			info += c.getAbilities().get(j).getName() + ", ";
		}
		info += ", attack range: " + c.getAttackRange() + ", attack damage: " + c.getAttackDamage();

		return info;
	}

	public String infoOfSecondTeam(int i) {
		Champion c = g.getSecondPlayer().getTeam().get(i);
		String info = c.getName();
		if (c instanceof Hero) {
			info += ", hero";
		} else if (c instanceof Villain) {
			info += ", villain";
		} else if (c instanceof AntiHero) {
			info += ", antihero";
		}
		info += ", health: " + c.getMaxHP() + ", mana: " + c.getMana() + ", action points: "
				+ c.getCurrentActionPoints() + ", abilities: ";
		for (int j = 0; j < c.getAbilities().size(); j++) {
			info += c.getAbilities().get(j).getName() + ", ";
		}
		info += ", attack range: " + c.getAttackRange() + ", attack damage: " + c.getAttackDamage();

		return info;
	}

//	public String currentChampionInfo() {
//		String info = "players:" + g.getFirstPlayer().getName() + ", " + g.getSecondPlayer().getName() + "\n";
//		Champion c = g.getCurrentChampion();
//		info += c.getName() +
//				", health: " +
//				c.getCurrentHP() +
//				", mana: " +
//				c.getMana() +
//				", action points: " +
//				c.getCurrentActionPoints() +
//				", abilities: ";
//		for (int i = 0; i < c.getAbilities().size(); i++) {
//			info += c.getAbilities().get(i).getName();
//		}
//		info += ", attack range: " +
//				c.getAttackRange() +
//				", attack damage: " +
//				c.getAttackDamage();
//		int i = 0;
//		while (i < c.getAbilities().size()) {
//			Ability a = c.getAbilities().get(i);
//			info += a.getName();
//			
//			if (a instanceof DamagingAbility) {
//				info += "damaging";
//			}
//			else if (a instanceof HealingAbility) {
//				info += "healing";
//			}
//			else if (a instanceof CrowdControlAbility) {
//				info += "crowd control";
//			}
//			
//			info += ", area of effect: " +
//			a.getCastArea() +
//			", cast range: " +
//			a.getCastRange() +
//			", mana cost: " +
//			a.getManaCost() +
//			", action points cost: " +
//			a.getRequiredActionPoints() +
//			", cooldown: " +
//			a.getCurrentCooldown() +
//			"/" + a.getBaseCooldown();
//			
//			if (a instanceof DamagingAbility) {
//				info += ((DamagingAbility) a).getDamageAmount();
//			}
//			else if (a instanceof HealingAbility) {
//				info += ((HealingAbility) a).getHealAmount();
//			}
//			else if (a instanceof CrowdControlAbility) {
//				info += ((CrowdControlAbility) a).getEffect().getType();
//			}			
//		}
//		
//		return info;
//	}
//
//	public Game getG() {
//		return g;
//	}

	public boolean isDisabled() {
		boolean flag = true;
		for (int i = 0; i < chmpBtns.size(); i++) {
			if (chmpBtns.get(i).isEnabled()) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start.start) {
			i = new InsertNames();
			this.getContentPane().removeAll();
			this.getContentPane().add(i);
			this.validate();
			this.repaint();
		}
		i.play.addActionListener(this);
		if (e.getSource() == i.play) {
			g = new Game(new Player(i.insertname1.getText()), new Player(i.insertname2.getText()));
			pane.showMessageDialog(null, "please note that the first champion that you select will be the leader",
					"important", pane.PLAIN_MESSAGE);
			s = new SelectChampions();
			try {
				Game.loadAbilities("Abilities.csv");
				Game.loadChampions("Champions.csv");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			for (int i = 0; i < Game.getAvailableChampions().size(); i++) {
				JButton b = new JButton();
				Champion c = Game.getAvailableChampions().get(i);
				b.setText(c.getName());
				b.addActionListener(this);
				chmpBtns.add(b);
				s.chmp.add(b);
			}
			this.getContentPane().removeAll();
			this.getContentPane().add(s);
			this.revalidate();
			this.repaint();
		}
		if (s != null) {
			if (chmpBtns.contains(e.getSource())) {
				if (g.getFirstPlayer().getTeam().size() < 3) {
					int index = chmpBtns.indexOf(e.getSource());
					JButton x = (JButton) e.getSource();
					x.setEnabled(false);
					Champion selected = Game.getAvailableChampions().get(index);
					g.getFirstPlayer().getTeam().add(selected);
					g.getFirstPlayer().setLeader(g.getFirstPlayer().getTeam().get(0)); // fixed the hovering and set the
																						// leader for player 1
				}

				else if (g.getSecondPlayer().getTeam().size() < 3) {
					int index = chmpBtns.indexOf(e.getSource());
					JButton x = (JButton) e.getSource();
					x.setEnabled(false);
					Champion selected = Game.getAvailableChampions().get(index);
					g.getSecondPlayer().getTeam().add(selected);
					g.getSecondPlayer().setLeader(g.getSecondPlayer().getTeam().get(0)); // fixed the hovering and set
																							// the leader for player 2

				}
				g.placeChampions();

				if (g.getFirstPlayer().getTeam().size() == 3 && g.getSecondPlayer().getTeam().size() == 3) {
					for (int k = 0; k < chmpBtns.size(); k++) {
						chmpBtns.get(k).setEnabled(false);
					}
					g.prepareChampionTurns();
				}
			}
			
			

			s.button1.addActionListener(this);

			for (int i = 0; i < chmpBtns.size(); i++) {
				chmpBtns.get(i).setToolTipText(infoOfSelected(i));
			}
			
			if (e.getSource() == s.button1 && isDisabled()) {
				b = new Board();
				this.getContentPane().removeAll();
				this.getContentPane().add(b);
				b.refresh(g.getBoard());
				if (i.insertname1.getText().equals("")) {
					b.player1.setText("player 1");
				}
				else {
					b.player1.setText(i.insertname1.getText());	
				}
				if (i.insertname2.getText().equals("")) {
					b.player2.setText("player 2");
				}
				else {
					b.player2.setText(i.insertname2.getText());	
				}				
				remainingChampions();
				

			}
		}
			if (b != null) {
				if (g.isFirstLeaderAbilityUsed() == true) {
					b.Leader1.setEnabled(false);
				}
				if (g.isSecondLeaderAbilityUsed() == true) {
					b.Leader2.setEnabled(false);
				}
				
				b.whoLeader1.setText(g.getFirstPlayer().getLeader().getName());
				b.whoLeader2.setText(g.getSecondPlayer().getLeader().getName());
				b.turn.setText(printPQ(g.getTurnOrder()));
				effectsOfCurrentChampion();
				
				if (b.movedown.getActionListeners().length == 0) {
					b.movedown.addActionListener(this);
				}
				
				if (b.attackup.getActionListeners().length == 0) {
					b.attackup.addActionListener(this);
				}
				
				if (b.attackdown.getActionListeners().length == 0) {
					b.attackdown.addActionListener(this);	
				}
				
				if (b.attackleft.getActionListeners().length == 0) {
					b.attackleft.addActionListener(this);	
				}
				
				if (b.attackright.getActionListeners().length == 0) {
					b.attackright.addActionListener(this);
				}
				
				if (b.moveup.getActionListeners().length == 0){
					b.moveup.addActionListener(this);
				}
				if(b.moveleft.getActionListeners().length == 0){
					b.moveleft.addActionListener(this);
				}
				if(b.moveright.getActionListeners().length == 0){
					b.moveright.addActionListener(this);
				}
				if(b.Leader1.getActionListeners().length == 0){
					b.Leader1.addActionListener(this);
				}
				if(b.Leader2.getActionListeners().length == 0){
					b.Leader2.addActionListener(this);
				}
				if(b.endturn.getActionListeners().length == 0){
					b.endturn.addActionListener(this);
				}
				if(b.castabilitya.getActionListeners().length == 0){
					b.castabilitya.addActionListener(this);
				}
				if(b.castabilityad.getActionListeners().length == 0){
					b.castabilityad.addActionListener(this);
				}
				if(b.castabilityaxy.getActionListeners().length == 0){
					b.castabilityaxy.addActionListener(this);
				}
				
				
			
					if (e.getSource() == b.moveup) {
						try {
							g.move(Direction.UP);
							//b.moveup = new BasicArrowButton(BasicArrowButton.NORTH);
						} catch (UnallowedMovementException z) {
							JOptionPane.showMessageDialog(null, "unallowed movement", "error",
									JOptionPane.ERROR_MESSAGE);
						} catch (NotEnoughResourcesException z) {
							JOptionPane.showMessageDialog(null, "not enough resources", "error",
									JOptionPane.ERROR_MESSAGE);
						}

						b.refresh(g.getBoard());
					}

					if (e.getSource() == b.movedown) {
						try {
							g.move(Direction.DOWN);
							//b.movedown = new BasicArrowButton(BasicArrowButton.SOUTH);
						} catch (UnallowedMovementException z) {
							JOptionPane.showMessageDialog(null, "unallowed movement", "error",
									JOptionPane.ERROR_MESSAGE);
						} catch (NotEnoughResourcesException z) {
							JOptionPane.showMessageDialog(null, "not enough resources", "error",
									JOptionPane.ERROR_MESSAGE);
						}
						b.refresh(g.getBoard());
					}

					if (e.getSource() == b.moveleft) {
						try {
							g.move(Direction.LEFT);
							//b.moveleft = new BasicArrowButton(BasicArrowButton.WEST);
						} catch (UnallowedMovementException z) {
							JOptionPane.showMessageDialog(null, "unallowed movement", "error",
									JOptionPane.ERROR_MESSAGE);
						} catch (NotEnoughResourcesException z) {
							JOptionPane.showMessageDialog(null, "not enough resources", "error",
									JOptionPane.ERROR_MESSAGE);
						}
						b.refresh(g.getBoard());
					}

					if (e.getSource() == b.moveright) {
						try {
							g.move(Direction.RIGHT);
							//b.moveright = new BasicArrowButton(BasicArrowButton.EAST);
						} catch (UnallowedMovementException z) {
							JOptionPane.showMessageDialog(null, "unallowed movement", "error",
									JOptionPane.ERROR_MESSAGE);
						} catch (NotEnoughResourcesException z) {
							JOptionPane.showMessageDialog(null, "not enough resources", "error",
									JOptionPane.ERROR_MESSAGE);
						}
						b.refresh(g.getBoard());
					}
					
						if (e.getSource() == b.attackup) {
							try {
								g.attack(Direction.UP);
							} catch (ChampionDisarmedException z) {
								JOptionPane.showMessageDialog(null, "champion is disarmed", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (NotEnoughResourcesException z) {
								JOptionPane.showMessageDialog(null, "not enough resources", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (InvalidTargetException z) {
								JOptionPane.showMessageDialog(null, "invalid target", "error",
										JOptionPane.ERROR_MESSAGE);
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}

						 if (e.getSource() == b.attackdown) {
							try {
								g.attack(Direction.DOWN);
							} catch (ChampionDisarmedException z) {
								JOptionPane.showMessageDialog(null, "champion is disarmed", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (NotEnoughResourcesException z) {
								JOptionPane.showMessageDialog(null, "not enough resources", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (InvalidTargetException z) {
								JOptionPane.showMessageDialog(null, "invalid target", "error",
										JOptionPane.ERROR_MESSAGE);
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}

						if (e.getSource() == b.attackleft) {
							try {
								g.attack(Direction.LEFT);
							} catch (ChampionDisarmedException z) {
								JOptionPane.showMessageDialog(null, "champion is disarmed", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (NotEnoughResourcesException z) {
								JOptionPane.showMessageDialog(null, "not enough resources", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (InvalidTargetException z) {
								JOptionPane.showMessageDialog(null, "invalid target", "error",
										JOptionPane.ERROR_MESSAGE);
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}

						if (e.getSource() == b.attackright) {
							try {
								g.attack(Direction.RIGHT);
							} catch (ChampionDisarmedException z) {
								JOptionPane.showMessageDialog(null, "champion is disarmed", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (NotEnoughResourcesException z) {
								JOptionPane.showMessageDialog(null, "not enough resources", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (InvalidTargetException z) {
								JOptionPane.showMessageDialog(null, "invalid target", "error",
										JOptionPane.ERROR_MESSAGE);
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}
						
						if (e.getSource() == b.endturn) {
							g.endTurn();
							b.turn.setText(printPQ(g.getTurnOrder()));
							effectsOfCurrentChampion();
						}

						if (e.getSource() == b.Leader1) {
							try {
								g.useLeaderAbility();
								b.Leader1.setEnabled(false);
								b.refresh(g.getBoard());
							} catch (LeaderNotCurrentException z) {
								JOptionPane.showMessageDialog(null, "it is currently not the leader's turn", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (LeaderAbilityAlreadyUsedException z) {
								JOptionPane.showMessageDialog(null, "the leader ability has already been used", "error",
										JOptionPane.ERROR_MESSAGE);
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}
						
						if (e.getSource() == b.Leader2) {
							try {
								g.useLeaderAbility();
								b.Leader2.setEnabled(false);
							} catch (LeaderNotCurrentException z) {
								JOptionPane.showMessageDialog(null, "it is currently not the leader's turn", "error",
										JOptionPane.ERROR_MESSAGE);
							} catch (LeaderAbilityAlreadyUsedException z) {
								JOptionPane.showMessageDialog(null, "the leader ability has already been used", "error",
										JOptionPane.ERROR_MESSAGE);
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}
						
						if (e.getSource() == b.castabilitya) { // the if conditions inside of this are based on me luckily finding out that pressing on a certain button in the joptionpane prints out a number (2, 1 and 0), so i used that knowledge to cast the ability :D
							Champion c = g.getCurrentChampion();
							Object[] abilities = {c.getAbilities().get(0).getName(), c.getAbilities().get(1).getName(),
									c.getAbilities().get(2).getName()}; //array for the buttons on the joptionpane
							if (ability.showOptionDialog(null, "please select ability", null,
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities,
									0) == 2) {
								try {
									g.castAbility(c.getAbilities().get(2));
								} catch (NotEnoughResourcesException e1) {
									JOptionPane.showMessageDialog(null, "not enough resources", "error",
											JOptionPane.ERROR_MESSAGE);
								} catch (CloneNotSupportedException e1) {
									e1.printStackTrace();
								} catch (AbilityUseException e1) {
									if (c.getAppliedEffects().contains("Silence")) {
										JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
												JOptionPane.ERROR_MESSAGE);
									} else if (c.getAbilities().get(2).getCurrentCooldown() != 0) {
										JOptionPane.showMessageDialog(null,
												c.getAbilities().get(2).getName() + "has not cooled down yet", "error",
												JOptionPane.ERROR_MESSAGE);
									}
								}
							}
							
							else if (ability.showOptionDialog(null, "please select ability", null,
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities,
									0) == 1) {
								try {
									g.castAbility(c.getAbilities().get(1));
								} catch (NotEnoughResourcesException e1) {
									JOptionPane.showMessageDialog(null, "not enough resources", "error",
											JOptionPane.ERROR_MESSAGE);
								} catch (CloneNotSupportedException e1) {
									e1.printStackTrace();
								} catch (AbilityUseException e1) {
									if (c.getAppliedEffects().contains("Silence")) {
										JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
												JOptionPane.ERROR_MESSAGE);
									} else if (c.getAbilities().get(1).getCurrentCooldown() != 0) {
										JOptionPane.showMessageDialog(null,
												c.getAbilities().get(1).getName() + "has not cooled down yet", "error",
												JOptionPane.ERROR_MESSAGE);
									}
								}
							}
							
							else if (ability.showOptionDialog(null, "please select ability", null,
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities,
									0) == 0) {
								try {
									g.castAbility(c.getAbilities().get(0));
								} catch (NotEnoughResourcesException e1) {
									JOptionPane.showMessageDialog(null, "not enough resources", "error",
											JOptionPane.ERROR_MESSAGE);
								} catch (CloneNotSupportedException e1) {
									e1.printStackTrace();
								} catch (AbilityUseException e1) {
									if (c.getAppliedEffects().contains("Silence")) {
										JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
												JOptionPane.ERROR_MESSAGE);
									} else if (c.getAbilities().get(0).getCurrentCooldown() != 0) {
										JOptionPane.showMessageDialog(null,
												c.getAbilities().get(0).getName() + "has not cooled down yet", "error",
												JOptionPane.ERROR_MESSAGE);
									}
								}
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}
						
						if (e.getSource() == b.castabilityad) { //this follows the same concept, but instead it inserts a direction
							Champion c = g.getCurrentChampion();
							Object[] abilities = {g.getCurrentChampion().getAbilities().get(0).getName(),
									g.getCurrentChampion().getAbilities().get(1).getName(),
									g.getCurrentChampion().getAbilities().get(2).getName(), direction};
							ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0);
							String s = direction.getText().toUpperCase(); //touppercase is used bc the enum is written in caps (LEFT, RIGHT, ETC.) but the user might insert the direction in small letters, which might cause problems <3 (i tested the method and it works!!)
							if (s.equals("UP")) {
								if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 2) {
									try {
										g.castAbility(c.getAbilities().get(2), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(2).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(2).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 1) {
									try {
										g.castAbility(c.getAbilities().get(1), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(1).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(1).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 0) {
									try {
										g.castAbility(c.getAbilities().get(0), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(0).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(0).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								
							}
							
							else if (s.equals("DOWN")) {
								if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 2) {
									try {
										g.castAbility(c.getAbilities().get(2), Direction.DOWN);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(2).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(2).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 1) {
									try {
										g.castAbility(c.getAbilities().get(1), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(1).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(1).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 0) {
									try {
										g.castAbility(c.getAbilities().get(0), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(0).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(0).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
							}
							
							else if (s.equals("LEFT")) {
								if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 2) {
									try {
										g.castAbility(c.getAbilities().get(2), Direction.LEFT);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(2).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(2).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 1) {
									try {
										g.castAbility(c.getAbilities().get(1), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(1).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(1).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 0) {
									try {
										g.castAbility(c.getAbilities().get(0), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(0).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(0).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}	
							}
							
							else if (s.equals("RIGHT")) {
								if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 2) {
									try {
										g.castAbility(c.getAbilities().get(2), Direction.RIGHT);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(2).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(2).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 1) {
									try {
										g.castAbility(c.getAbilities().get(1), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(1).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(1).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
								
								else if (ability.showOptionDialog(null, "please select ability and direction (up, down, left, right)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0) == 0) {
									try {
										g.castAbility(c.getAbilities().get(0), Direction.UP);
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null, "not enough resources", "error",
												JOptionPane.ERROR_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										e1.printStackTrace();
									} catch (AbilityUseException e1) {
										if (c.getAppliedEffects().contains("Silence")) {
											JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
													JOptionPane.ERROR_MESSAGE);
										} else if (c.getAbilities().get(0).getCurrentCooldown() != 0) {
											JOptionPane.showMessageDialog(null,
													c.getAbilities().get(0).getName() + "has not cooled down yet", "error",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								}
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}
						
						if (e.getSource() == b.castabilityaxy) { //very similar to b.castabilitya, with just a few additional things
							Champion c = g.getCurrentChampion();
							Object[] abilities = {g.getCurrentChampion().getAbilities().get(0).getName(),
									g.getCurrentChampion().getAbilities().get(1).getName(),
									g.getCurrentChampion().getAbilities().get(2).getName(), x, y};
							ability.showOptionDialog(null, "please select ability, x (0-4), and y (0-4)", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities, 0);
							int a = Integer.parseInt(x.getText()); //here the user clicks on a button in the joptionpane AND inserts x and y, so these two lines are used to store the numbers entered (parseint is used bc the input is logically a string)
							int w = Integer.parseInt(y.getText());
							
							if (ability.showOptionDialog(null, "please select ability", null,
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities,
									0) == 2) {
								try {
									g.castAbility(c.getAbilities().get(2), w, a); // a and b are lines 606 and 607
								} catch (NotEnoughResourcesException e1) {
									JOptionPane.showMessageDialog(null, "not enough resources", "error",
											JOptionPane.ERROR_MESSAGE);
								} catch (CloneNotSupportedException e1) {
									e1.printStackTrace();
								} catch (AbilityUseException e1) {
									if (c.getAppliedEffects().contains("Silence")) {
										JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
												JOptionPane.ERROR_MESSAGE);
									} else if (c.getAbilities().get(2).getCurrentCooldown() != 0) {
										JOptionPane.showMessageDialog(null,
												c.getAbilities().get(2).getName() + "has not cooled down yet", "error",
												JOptionPane.ERROR_MESSAGE);
									}
								} catch (InvalidTargetException e1) {
									JOptionPane.showMessageDialog(null, "cell is empty", "error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							
							else if (ability.showOptionDialog(null, "please select ability", null,
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities,
									0) == 1) {
								try {
									g.castAbility(c.getAbilities().get(1), w, a);
								} catch (NotEnoughResourcesException e1) {
									JOptionPane.showMessageDialog(null, "not enough resources", "error",
											JOptionPane.ERROR_MESSAGE);
								} catch (CloneNotSupportedException e1) {
									e1.printStackTrace();
								} catch (AbilityUseException e1) {
									if (c.getAppliedEffects().contains("Silence")) {
										JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
												JOptionPane.ERROR_MESSAGE);
									} else if (c.getAbilities().get(1).getCurrentCooldown() != 0) {
										JOptionPane.showMessageDialog(null,
												c.getAbilities().get(1).getName() + "has not cooled down yet", "error",
												JOptionPane.ERROR_MESSAGE);
									}
								} catch (InvalidTargetException e1) {
									JOptionPane.showMessageDialog(null, "cell is empty", "error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							
							else if (ability.showOptionDialog(null, "please select ability", null,
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abilities,
									0) == 0) {
								try {
									g.castAbility(c.getAbilities().get(0), w, a);
								} catch (NotEnoughResourcesException e1) {
									JOptionPane.showMessageDialog(null, "not enough resources", "error",
											JOptionPane.ERROR_MESSAGE);
								} catch (CloneNotSupportedException e1) {
									e1.printStackTrace();
								} catch (AbilityUseException e1) {
									if (c.getAppliedEffects().contains("Silence")) {
										JOptionPane.showMessageDialog(null, c.getName() + " is silenced", "error",
												JOptionPane.ERROR_MESSAGE);
									} else if (c.getAbilities().get(0).getCurrentCooldown() != 0) {
										JOptionPane.showMessageDialog(null,
												c.getAbilities().get(0).getName() + "has not cooled down yet", "error",
												JOptionPane.ERROR_MESSAGE);
									}
								} catch (InvalidTargetException e1) {
									JOptionPane.showMessageDialog(null, "cell is empty", "error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							b.refresh(g.getBoard());
							b.turn.setText(printPQ(g.getTurnOrder()));
							remainingChampions();
							if (g.checkGameOver() == g.getFirstPlayer()) {
								JOptionPane.showMessageDialog(null, b.player1.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
							else if (g.checkGameOver() == g.getSecondPlayer()) {
								JOptionPane.showMessageDialog(null, b.player2.getText(), null,
										JOptionPane.OK_OPTION);
								System.exit(0);
							}
						}
						
				}
			
				this.validate();
				this.repaint();
			}
			
	public static void main(String[] args) {
		Main m = new Main();
	}
		}

