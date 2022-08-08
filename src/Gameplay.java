import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	//Variables/Objects
	private static final long serialVersionUID = 1L;
	private ImageIcon titleImage;
	
	private int[] snakexlength = new int[750]; 		//x position of the snake's head
	private int[] snakeylength = new int[750]; 		//x position of the snake's snake's head
	
	private int[] enemyxpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325,
							   350,	375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650,
							   675, 700, 725, 750, 775, 800, 825, 850};		//enemy's x position
	private int[] enemyypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325,
			   350,	375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};		//enemy's x position

	private ImageIcon enemyimage;					//food
	private Random random = new Random();			//Used to generate random positions
	
	private int xpos = random.nextInt(34);			//Generates x random position
	private int ypos = random.nextInt(23);			//Generates y random position
	
	
	private boolean left = false;					// Direction ID
	private boolean right = false;					// Direction ID
	private boolean up = false;						// Direction ID
	private boolean down = false;					// Direction ID
	
	private ImageIcon rightmouth;					// Snake head
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	
	private ImageIcon snakeimage;					//Snake body
	
	private int lengthofsnake = 3;					//Snake Default Length
	
	private Timer timer; 							//To manage the speed of the snake inside the panel
	private int delay = 100;
	
	private int moves = 0;
	
	private int score = 0;
	
	//Constructor
	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);				//Initiate the timer
		timer.start();
	}
	
	//Methods
	public void paint(Graphics g) {
		
		if(moves == 0) {			//Checks if the game just started
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
		}
		
		// draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		
		//draw the title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw the border for the play area
		g.setColor(Color.white);
		g.drawRect(24, 74, 851, 577);
		
		//draw the background for the play area
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		//draw the scores
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Score: "+score, 780, 30);
		
		//draw the length of the snake
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: "+ lengthofsnake, 780, 50);
		
		//draw the snake
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]); 	//Default starting position
		
		for(int a = 0; a < lengthofsnake; a++) {
			if(a == 0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			
			if(a == 0 && left) {
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			
			if(a == 0 && right) {
				upmouth = new ImageIcon("upmouth");
				upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			
			if(a == 0 && right) {
				downmouth = new ImageIcon("downmouth");
				downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			
			if(a != 0) {
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
		}
		
		enemyimage = new ImageIcon("enemy.png");
		
		if(enemyxpos[xpos] == snakexlength[0] && enemyypos[ypos] == snakeylength[0]){
			score++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
			
		}
		enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
		
		//Checks the collision between the snakes head and it's body
		for(int b = 1; b < lengthofsnake; b++) {
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0] ) {
				right = false;						//Stops the game
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("Game Over", 300, 300);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Space to RESTART", 350, 340);
			}
		}
		
		g.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(right) {
			for(int r = lengthofsnake; r >=0; r --){
				snakeylength[r + 1] = snakeylength[r];			//Moves the snakes head to the right, y position
			}
			for(int r = lengthofsnake; r >=0; r --){
				if(r == 0) {
					snakexlength[r] = snakexlength[r] + 25;			//Moves the snakes head to the right
				}
				else {
					snakexlength[r] = snakexlength[r - 1];			//Moves the snakes head to the right
				}
				if(snakexlength[r] > 850) {						//Checks if the snake touches the right border
					snakexlength[r] = 25;
				}
			}
			
			repaint();												//Calls the paint method
		}
		
		if(left) {
			for(int r = lengthofsnake; r >=0; r --){
				snakeylength[r + 1] = snakeylength[r];			//Moves the snakes head to the left, y position
			}
			for(int r = lengthofsnake; r >=0; r --){
				if(r == 0) {
					snakexlength[r] = snakexlength[r] - 25;			//Moves the snakes head to the left
				}
				else {
					snakexlength[r] = snakexlength[r - 1];			//Moves the snakes head to the right
				}
				if(snakexlength[r] < 25) {						//Checks if the snake touches the right border
					snakexlength[r] = 850;
				}
			}
			
			repaint();												//Calls the paint method
			
		}
		
		if(up) {
			for(int r = lengthofsnake; r >=0; r --){
				snakexlength[r + 1] = snakexlength[r];			//Moves the snakes head to the up, x position
			}
			for(int r = lengthofsnake; r >=0; r --){
				if(r == 0) {
					snakeylength[r] = snakeylength[r] - 25;			//Moves the snakes head to the down
				}
				else {
					snakeylength[r] = snakeylength[r - 1];			//Moves the snakes head to the down
				}
				if(snakeylength[r] < 75) {						//Checks if the snake touches the bottom border
					snakeylength[r] = 625;
				}
			}
			
			repaint();												//Calls the paint method
			
		}
		
		if(down) {
			for(int r = lengthofsnake; r >=0; r --){
				snakexlength[r + 1] = snakexlength[r];			//Moves the snakes head to the up, x position
			}
			for(int r = lengthofsnake; r >=0; r --){
				if(r == 0) {
					snakeylength[r] = snakeylength[r] + 25;			//Moves the snakes head to the down
				}
				else {
					snakeylength[r] = snakeylength[r - 1];			//Moves the snakes head to the down
				}
				if(snakeylength[r] > 625) {						//Checks if the snake touches the bottom border
					snakeylength[r] = 75;
				}
			}
			
			repaint();												//Calls the paint method
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			repaint();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//System.out.println("Right");
			moves++;
			right = true;
			if(!left) {
				right = true;
			}
			else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			left = true;
			if(!right) {
				left = true;
			}
			else {
				left = false;
				right = true;
			}
			up = false;
			down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			up = true;
			if(!down) {
				up = true;
			}
			else {
				up = false;
				down = true;
			}
			left = false;
			right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			down = true;
			if(!up) {
				down = true;
			}
			else {
				down = false;
				up = true;
			}
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
