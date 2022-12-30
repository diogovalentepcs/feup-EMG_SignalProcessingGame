package com.game.src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class frmMain {

	public static JFrame frameMenu;
	public static JFrame frameGame;
	public static frmSettings frameSettings;
	public static final String SETTINGS_FILE_PATH = "res/settings.txt";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmMain window = new frmMain();
					window.frameMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public frmMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//Main frame
		frameMenu = new JFrame();
		frameMenu.getContentPane().setBackground(Color.BLACK);
		frameMenu.setBounds(100, 100, 450, 300);
		frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMenu.getContentPane().setLayout(null);
		
		
		// getting Game variables from 'settings.txt'
		FileReader settingsFileReader;
		BufferedReader settingsBufferedReader;
		
		try {
			settingsFileReader = new FileReader(this.SETTINGS_FILE_PATH);
			settingsBufferedReader = new BufferedReader(settingsFileReader);
			Game.WIDTH = Integer.parseInt(settingsBufferedReader.readLine());
			Game.HIGHSCORE = Integer.parseInt(settingsBufferedReader.readLine());
			Game.DIFFICULTY = settingsBufferedReader.readLine();
			//Game.HIGHSCORE = highscore
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game game = new Game();
				
				game.setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
				game.setMaximumSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
				game.setMinimumSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
				
				frameGame = new JFrame(game.TITLE);
				frameGame.getContentPane().add(game); // adds dimension to the frame
				frameGame.pack(); // packs everything together
				frameGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows for exit button to work
				frameGame.setResizable(false);
				frameGame.setLocationRelativeTo(null);
				frameGame.setVisible(true);
			
				Game.State = Game.STATE.GAME;
				game.start();
				frameMenu.setVisible(false);
			}
		});
		btnStartGame.setBounds(166, 95, 117, 29);
		frameMenu.getContentPane().add(btnStartGame);
		
		JLabel lblTitle = new JLabel("Hands On - The Physio Game");
		lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(6, 43, 438, 33);
		frameMenu.getContentPane().add(lblTitle);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameMenu.setVisible(false);
				frameSettings = new frmSettings();
				frameSettings.setVisible(true);
			}
		});
		btnSettings.setBounds(166, 136, 117, 29);
		frameMenu.getContentPane().add(btnSettings);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//write to file
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(frmMain.SETTINGS_FILE_PATH));
					writer.write(Integer.toString(Game.WIDTH));
					writer.newLine();
					writer.write(Integer.toString(Game.HIGHSCORE));
					writer.newLine();
					writer.write(Game.DIFFICULTY);
					writer.flush();
					writer.close();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}				
				System.exit(0);
			}
		});
		btnQuit.setBounds(307, 220, 117, 29);
		frameMenu.getContentPane().add(btnQuit);
	}
}
