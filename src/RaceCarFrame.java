/*
 * Wei Han's New File
 *  Created on 25/09/2011
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;

public class RaceCarFrame extends JFrame {
	JButton jbtStart = new JButton("Start");
	JButton jbtStop  = new JButton("Stop");
	JButton jbtAcc   = new JButton("Speed Up");
	JButton jbtDec   = new JButton("Slow Down");
	JButton jbtLeft  = new JButton("<=Left");
	JButton jbtRight = new JButton("Right=>");
	
	TrackPane jpTrack = new TrackPane();

	private String car1Str = "image/car1/car0.gif";
	private String car2Str = "image/car2/car0.gif";

	RaceCar car1 = new RaceCar(car1Str);
	RaceCar car2 = new RaceCar(car2Str);
	
	private JMenuItem jmiRestart,jmiAbout;

	static final double PI = 3.14159;
	
	static Timer spinTimer;

	public RaceCarFrame(){
		JPanel jpStart = new JPanel();
		jpStart.setLayout(new GridLayout(1,2));
		jpStart.add(jbtStart);
		jpStart.add(jbtStop);
		
		JPanel jpControl = new JPanel();
		jpControl.setLayout(new BorderLayout());
		jpControl.add(jbtLeft,BorderLayout.WEST);
		jpControl.add(jbtRight,BorderLayout.EAST);
		jpControl.add(jbtAcc,BorderLayout.NORTH);
		jpControl.add(jbtDec,BorderLayout.CENTER);
		
		JPanel jpButton = new JPanel();
		jpButton.setLayout(new BorderLayout());
		jpButton.add(jpStart,BorderLayout.WEST);
		jpButton.add(jpControl,BorderLayout.EAST);
		jpButton.setBorder(new LineBorder(Color.BLACK));
		
		add(jpTrack,BorderLayout.CENTER);
		add(jpButton,BorderLayout.NORTH);
		
		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);
		
		JMenu operationMenu = new JMenu("Operation");
		operationMenu.add(jmiRestart = new JMenuItem("Restart",'R'));
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(jmiAbout = new JMenuItem("About",'A'));
		
		jmb.add(operationMenu);
		jmb.add(helpMenu);

		initPane(jpTrack);
		
		spinTimer = new Timer(20,new TimerListener());
		
		addKeyListener(new keyListener());
		
		jbtStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spinTimer.start();
			}
		});
		jbtStart.addKeyListener(new keyListener());
		
		jbtStop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spinTimer.stop();
			}
		});
		jbtStop.addKeyListener(new keyListener());

		jbtLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				car1.turnLeft();
			}
		});
		jbtLeft.addKeyListener(new keyListener());
	
		jbtRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				car1.turnRight();
			}
		});
		jbtRight.addKeyListener(new keyListener());

		jbtAcc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				car1.speedUp();
			}
		});
		jbtAcc.addKeyListener(new keyListener());

		jbtDec.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				car1.slowDown();
			}
		});
		jbtDec.addKeyListener(new keyListener());
		
		jmiRestart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				spinTimer.stop();
				initPane(jpTrack);
			}
		});
		jmiRestart.addKeyListener(new keyListener());

		jmiAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "Race Car Game");
			}
		});
		jmiAbout.addKeyListener(new keyListener());
	}
	
	
	public void initPane(TrackPane panel){
		System.out.println("System.getProperty(\"user.dir\") is:"+System.getProperty("user.dir"));

		car1.setCrashed(false);
		car2.setCrashed(false);
		
		car1.setCur_Car(car1Str);
		car2.setCur_Car(car2Str);

		panel.setCar1(car1);
		panel.setCar2(car2);
		
		panel.repaint();
	}
	
	class keyListener implements KeyListener{
		public void keyPressed(KeyEvent e){
			switch(e.getKeyCode()){
				case KeyEvent.VK_DOWN:  car1.slowDown();break;
				case KeyEvent.VK_UP:    car1.speedUp();break;
				case KeyEvent.VK_LEFT:  car1.turnLeft();break;
				case KeyEvent.VK_RIGHT: car1.turnRight();break;
			}
		}
		
		public void keyReleased(KeyEvent e){
		}

		public void keyTyped(KeyEvent e){
		}
	}
	
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			int car1Index = car1.getIndex();
			int speed1 = car1.getSpeed();
			
			int car2Index = car2.getIndex();
			int speed2 = car2.getSpeed();
			
			int car1DeltaX = 0-(int)(speed1*Math.cos(car1Index*22.5/360.0*2*PI));
			int car1DeltaY = 0-(int)(speed1*Math.sin(car1Index*22.5/360.0*2*PI));
			
			int car2DeltaX = 0-(int)(speed2*Math.cos(car2Index*22.5/360.0*2*PI));
			int car2DeltaY = 0-(int)(speed2*Math.sin(car2Index*22.5/360.0*2*PI));
			
			if(collision(car1)) car1.setCrashed(true);
			if(collision(car2)) car2.setCrashed(true);

			if(collision(car1,car2)){
				car1.setCrashed(true);
				car2.setCrashed(true);
			}
			
			if(!car1.isCrashed()){
				car1.setX_pos(car1.getX_pos()+car1DeltaX);
				car1.setY_pos(car1.getY_pos()+car1DeltaY);
			}
			
			if(!car2.isCrashed()){
				car2.setX_pos(car2.getX_pos()+car2DeltaX);
				car2.setY_pos(car2.getY_pos()+car2DeltaY);
			}
			
			if(car1.isWin()){
				spinTimer.stop();
			}
			
			if(car2.isWin()){
				spinTimer.stop();
			}
			
			jpTrack.repaint();
		}
		
		public boolean collision(RaceCar car1,RaceCar car2){  //two car collision
			if((Math.abs(car1.getX_pos()-car2.getX_pos())<40)&&(Math.abs(car1.getY_pos()-car2.getY_pos())<40))return true;
			else return false;
		}

		public boolean collision(RaceCar car){  //collision with track
			int x_pos  = car.getX_pos();
			int y_pos  = car.getY_pos();
			
			if((x_pos<50)||(y_pos<50)||(x_pos>760)||(y_pos>510)) return true;
			else if((x_pos>110)&&(y_pos>110)&&(x_pos<700)&&(y_pos<450)) return true;
			else return false;
		}
	}
	
	public static void main(String[] args) {
		RaceCarFrame frame = new RaceCarFrame();
		frame.setTitle("Race Car");
		frame.setSize(870,700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class TrackPane extends JPanel {
	
	private RaceCar car1;
	private RaceCar car2;
	
	int x1_base;
	int y1_base;
	
	int x2_base;
	int y2_base;

	public TrackPane(){
		   setBackground(Color.white);
	}

	public void setCar1(RaceCar car1) {
		x1_base = 425;
		y1_base = 450;
		
		car1.setX_pos(x1_base);
		car1.setY_pos(y1_base);
		this.car1 = car1;
	}

	public void setCar2(RaceCar car2) {
		x2_base = 200;
		y2_base = 500;

		car2.setX_pos(x2_base);
		car2.setY_pos(y2_base);
		this.car2 = car2;
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Color c1 = Color.green;
		g.setColor(c1);
		g.fillRect(150, 150, 550, 300);  //innner side
		
		Color c2 = Color.black;
		g.setColor(c2);
		g.drawRect(50, 50, 750, 500);    //outer side
		g.drawRect(150, 150, 550, 300);

		Color c3 = Color.yellow;
		g.setColor(c3);
		g.drawRect(100, 100, 650, 400);

		Color c4 = Color.RED;
		g.setColor(c4);
		g.drawLine(425, 450, 425, 550);

		ImageIcon car1Icon = new ImageIcon(this.car1.getCur_Car());
		ImageIcon car2Icon = new ImageIcon(this.car2.getCur_Car());

		Image car1 = car1Icon.getImage();
		Image car2 = car2Icon.getImage();

		g.drawImage(car1,this.car1.getX_pos(),this.car1.getY_pos(),40,40,this);
		g.drawImage(car2,this.car2.getX_pos(),this.car2.getY_pos(),40,40,this);
		
	}
}

