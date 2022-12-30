package com.game.src.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class frmSettings extends JFrame {

	private JPanel contentPane;
	private JTextField txtHighscore;
	private JTextField txtWidth;
	private JTextField txtHeight;

	
	JComboBox<String> cbxDiffculty = new JComboBox<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmSettings frame = new frmSettings();
					frame.setVisible(true);
					
					frame.addWindowListener(new WindowListener() {
						@Override
				        public void windowOpened(WindowEvent e) {
				            
				        }

						@Override
						public void windowClosing(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

					});
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}

	/**
	 * Create the frame.
	 */
	public frmSettings() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lblGameWidth = new JLabel("Game Width: ");
		lblGameWidth.setForeground(Color.WHITE);
		lblGameWidth.setBounds(44, 88, 98, 16);
		contentPane.add(lblGameWidth);
		
		JLabel lblGameHeight = new JLabel("Game Height: ");
		lblGameHeight.setForeground(Color.WHITE);
		lblGameHeight.setBounds(44, 130, 98, 16);
		contentPane.add(lblGameHeight);
		
		JLabel lblNewLabel = new JLabel("Change Difficulty: ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(44, 173, 135, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.WIDTH = Integer.parseInt(txtWidth.getText());
				Game.HEIGHT = Integer.parseInt(txtHeight.getText());
				Game.DIFFICULTY = (String) cbxDiffculty.getSelectedItem();
				
			}
		});
		btnSaveChanges.setBounds(44, 225, 117, 29);
		contentPane.add(btnSaveChanges);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmMain.frameSettings.dispose();
				frmMain.frameMenu.setVisible(true);
				
			}
		});
		btnMenu.setBounds(316, 225, 117, 29);
		contentPane.add(btnMenu);
		
		JLabel lblHighScore = new JLabel("Current Highscore:");
		lblHighScore.setForeground(Color.WHITE);
		lblHighScore.setBounds(44, 45, 135, 16);
		contentPane.add(lblHighScore);
		
		txtHighscore = new JTextField();
		txtHighscore.setEditable(false);
		txtHighscore.setBounds(191, 40, 130, 26);
		contentPane.add(txtHighscore);
		txtHighscore.setColumns(10);
		
		txtWidth = new JTextField();
		txtWidth.setBounds(191, 83, 130, 26);
		contentPane.add(txtWidth);
		txtWidth.setColumns(10);
		
		txtWidth.addCaretListener(new CaretListener() {

	        @Override
	        public void caretUpdate(CaretEvent e) {
	        	try {
		        	Game.WIDTH = Integer.parseInt(txtWidth.getText());
	            	Game.HEIGHT = Game.WIDTH / 12 * 9;
					txtHeight.setText(Integer.toString(Game.HEIGHT)) ;
	        	} catch (Exception ex) {
	        		ex.printStackTrace();
	        	}

	        }
	    });
		
		
		txtHeight = new JTextField();
		txtHeight.setEditable(false);
		txtHeight.setBounds(191, 125, 130, 26);
		contentPane.add(txtHeight);
		txtHeight.setColumns(10);

		
		cbxDiffculty.setModel(new DefaultComboBoxModel(new String[] {"EASY", "NORMAL", "HARD"}));
		cbxDiffculty.setBounds(191, 169, 130, 27);
		contentPane.add(cbxDiffculty);
		
		JButton btnResetHighscore = new JButton("Reset");
		btnResetHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.HIGHSCORE = 0;
				txtHighscore.setText("0");
			}
		});
		btnResetHighscore.setBounds(346, 40, 67, 29);
		contentPane.add(btnResetHighscore);
		
		
		txtHighscore.setText(Integer.toString(Game.HIGHSCORE));
        txtWidth.setText(Integer.toString(Game.WIDTH));
        txtHeight.setText(Integer.toString(Game.HEIGHT));
        cbxDiffculty.setSelectedItem(Game.DIFFICULTY);
	}
}
