package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.entities.EntityA;
import com.game.src.main.entities.EntityB;



public class Game extends Canvas implements Runnable {
	
	public static int WIDTH = 500;
	public static  int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Hands On - PhysioGame";
	public static final String SIGNAL_TXT = "signal.txt";
	public static final File SIGNAL_FILE = new File(SIGNAL_TXT);
	
	public static final String HIGHSCORE_TXT = "highscore.txt";
	
	public static FileReader HIGHSCORE_FILE;
	public static BufferedReader HIGHSCORE_READER;
	public static int HIGHSCORE;
	public static String DIFFICULTY = "NORMAL";

	private boolean running = false;
	private Thread thread; // threads allow  more than one task to be performed at one time
	
	// images to buffer the window
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	
	// varivable to only allow one bullet shoot per signal
	private boolean isShooting = false;
	// varivable to only allow one skin change per signal
	private boolean skinChanged = false;
	
	// enemies
	private int enemy_count = 4;
	private int enemy_killcount = 0;	
	private int enemy_respawn = 0;
	
	// initialize player
	private Player p;
	
	// initalize controller
	private Controller c;
	
	// init textures
	private Textures tex;
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
		
	// Font
	private Font fnt  = new Font("arial", Font.BOLD, 20);
	
	// game states
	public enum STATE{
		MENU,
		GAME,
		GAMEHAND
	};
	public static STATE State = STATE.MENU;
	
	private long skinTimer = System.currentTimeMillis();
	
	
	// load buffered sprite sheet (with all images)
	public void init() {
		requestFocus(); // brings focus to the jframe so we don't have to click it to start
		BufferedImageLoader loader = new BufferedImageLoader();
		String path = "/spritesheet.png";
		
		// load sprite sheet
		try {
			spriteSheet = loader.loadImage(path);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("can't load spritesheet");
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("can't access spritesheet");
		}
		
		// keyboard input listener
		this.addKeyListener(new KeyInput(this));
		
		// keyboard input listener
		this.addMouseListener(new MouseInput(this));
		
		tex = new Textures(this);
		
		c = new Controller(this, tex);
		p = new Player(475, 800, tex, this, c);

		
		ea = c.getEntityA();
		eb = c.getEntityB();
		
		
		c.createEnemy(enemy_count);
		
		
	}
	
	
	synchronized void start() {
		if(running)
			return; // if a thread is started, it doensn't need to be started again
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	private synchronized void stop() {
		if(!running)
			return; // if a thread is stopped I don't want to stop it again
		
		running = false;
		
		try {
			thread.join(); //joins all running threads
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		
		
		System.exit(1);
	}
	
	// necessary method of runnable -> its where the game loop is the main part of the code
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; // 60 ticks per second -> I only want to update (or tick) the game 60 times per second. I want to restrict the updates too just 60x per second
		double ns = 1000000000 / amountOfTicks; 
		double delta = 0; // for the timer
		int updates = 0; // tick counter
		int frames = 0; // frame counter (how many times does it render per second)
		long timer = System.currentTimeMillis();
		
		while(running) {
			// This is game loop
			//System.out.println("WORKING");
			

			long now = System.nanoTime();
			delta += (now - lastTime)/ ns; // same thing has delta += (now - lastTime)/ 1 000 000 000 * amounttOfTicks -> time is in nanosecond by dividing we get seconds
			lastTime = now;
			
			// check if 1/60 s have passed to update the game, if delta >= 1, it means (now - lastTime) = 1 000 000 000 / 60 (1sec/60)
			if(delta >= 1) {
				tick();
				updates++; //increase tick counter
				delta--; //bring delta back to 0 (or very close to it)
			}
			
			// the game renders every time the loop happens -> the no. of frames increases or decreases depending on the processing power of the user
			render();
			frames++; // increment the fps counter
			
			if(System.currentTimeMillis() - timer > 1000) { //1000 milis = 1 s - print values every second
				timer += 1000;
				System.out.println(updates + " Ticks, FPS " + frames);
				//System.out.println("Working Directory = " + System.getProperty("user.dir"));
				updates = 0;
				frames = 0;
			}
			
			//reset skin if 1sec since shooting
			if(System.currentTimeMillis() - this.skinTimer > 500) { //1000 milis = 1 s - print values every second
				if (this.p.skinIndex == 1) {
					this.p.setSkinIndex(2);
				}
			}
			
		}
		stop();
	}
	
	// everything in the game that updated
	public void tick() {
		
		if(State == STATE.GAME) {
			p.tick();
			c.tick();	
			signalFileListener(c,p);
			
			if(this.enemy_respawn >= this.enemy_count) {
				enemy_respawn = 0;
				enemy_count += 2;
				c.createEnemy(enemy_count);
			}
			
		}

	}
	
	// everything in the game that renders
	public void render() {	
		
		
		// buffer strategy to handle all the buffering behnd the scenes
		BufferStrategy bs = this.getBufferStrategy(); // this refers to the canvas class we are extending
		
		// create strategy only on the 1st render iteration
		if(bs==null) {
			createBufferStrategy(3); //triple buffer -> allows for loading the next 2 frames before leaving the current frame -> decreases fps performance but allow for smoother change
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		// start of render
	
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // black screen
		
		if(State == STATE.GAME) {	

			p.render(g);
			c.render(g);
			
			// Show Kill Count
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Kill Count: " + this.enemy_killcount, 5, 100);
			
			// Show Health Bar
			g.setColor(Color.gray);
			g.fillRect(5, 5, 200, 50);
			
			g.setColor(Color.green);
			g.fillRect(5, 5, this.p.health, 50);
						
			g.setColor(Color.gray);
			g.drawRect(5, 5, 200, 50);

			
			
		} else if (State == STATE.MENU) {

		}
		// end of render
		
		g.dispose(); //causes the graphics to be destroyed and cleaned up by the operating system
		bs.show();
		
	}
	
	//@TODO change arrows to wasd
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(State == STATE.GAME) {	

			if(key == KeyEvent.VK_RIGHT) {
				p.setVelX(5);
			} else if(key == KeyEvent.VK_LEFT) {
				p.setVelX(-5);
			} else if(key == KeyEvent.VK_DOWN) {
				p.setVelY(5);
			} else if(key == KeyEvent.VK_UP) {
				p.setVelY(-5);
			} else if(key == KeyEvent.VK_SPACE && !isShooting) {
				//creating signal file
				try {
					SIGNAL_FILE.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.p.setSkinIndex(1);
				//isShooting = true;
				//c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
			} else if(key == KeyEvent.VK_ENTER && !skinChanged) {
				skinChanged = true;
				this.nextPlayerSkin();
			} else if(key == KeyEvent.VK_M) {
				
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(State == STATE.GAME) {	
		
			  if(key == KeyEvent.VK_RIGHT) { 
				  p.setVelX(0); } 
			  else if(key == KeyEvent.VK_LEFT){
				  p.setVelX(0); 
			  } else if(key == KeyEvent.VK_DOWN) { 
				  p.setVelY(0); 
			  } else if(key == KeyEvent.VK_UP) { 
				  p.setVelY(0);
			  } else if(key == KeyEvent.VK_SPACE) {
				  this.p.setSkinIndex(2);
				  //isShooting = false;
			} else if(key == KeyEvent.VK_ENTER) {
				skinChanged = false;
			} else if(key == KeyEvent.VK_M) {
				frmMain.frameGame.dispose();  //Remove JFrame 
				frmMain.frameMenu.setVisible(true);
				frmMain.frameMenu.toFront();
				frmMain.frameMenu.requestFocus();
			}
			  
		}
	}
	
	public void signalFileListener(Controller c, Player p) {
		if(State == STATE.GAME) {	
			if(SIGNAL_FILE.exists() && !SIGNAL_FILE.isDirectory() && !isShooting) {
				isShooting = true;
				this.p.setSkinIndex(1);
				c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
				SIGNAL_FILE.delete();
				isShooting = false;
				this.skinTimer = System.currentTimeMillis();
			}
		}
	}

	
	public static void main(String args[]) {
		/*
		 * Game game = new Game();
		 * 
		 * game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		 * game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		 * game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		 * 
		 * JFrame frame = new JFrame(game.TITLE); frame.add(game); // adds dimension to
		 * the frame frame.pack(); // packs everything together
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows for exit
		 * button to work frame.setResizable(false); frame.setLocationRelativeTo(null);
		 * frame.setVisible(true);
		 * 
		 * game.start();
		 */
	}

	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public void nextPlayerSkin() {
		this.p.nextSkin();
	}


	public int getEnemy_count() {
		return enemy_count;
	}


	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}


	public int getEnemy_killcount() {
		return enemy_killcount;
	}


	public void setEnemy_killcount(int enemy_killcount) {
		this.enemy_killcount = enemy_killcount;
	}
	
	public int getEnemy_respawn() {
		return enemy_respawn;
	}


	public void setEnemy_respawn(int enemy_respawn) {
		this.enemy_respawn = enemy_respawn;
	}


	public void reset() {
		this.p.reset();
		this.c.reset();
		this.enemy_count = 4;
		if (this.enemy_killcount > Game.HIGHSCORE) {
			Game.HIGHSCORE = this.enemy_killcount;
		};
		this.enemy_killcount = 0;
		this.enemy_respawn = 0;
		c.createEnemy(this.enemy_count);
		frmMain.frameGame.dispose();  //Remove JFrame 
		frmMain.frameMenu.setVisible(true);
		frmMain.frameMenu.toFront();
		frmMain.frameMenu.requestFocus();
		this.stop();
	}

	
	
	
}
