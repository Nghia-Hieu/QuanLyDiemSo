package com.example.qldiemso.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;


import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.SwingConstants;



public class DangNhapFrame extends JFrame {

	public JFrame frame;
	private JTextField userText;
	private JPasswordField passText;
	//private Account thisAccount;
	private JPanel panel, panel_1;
	private JLabel passLabel, idLabel, lblNewLabel_2, titleLabel;
	private JButton btnLogin;
	
	/**
	 * Create the application.
	 */
	void closeLogin() {
		frame.setVisible(false);
		frame.dispose();
	}
	public DangNhapFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 255, 255));
		frame.setBounds(100, 100, 673, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 204, 255));
		panel.setBounds(10, 10, 639, 413);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 639, 413);
		panel_1.setBackground(new Color(0, 153, 204));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		userText = new JTextField();
		userText.setBounds(222, 166, 197, 34);
		panel_1.add(userText);
		userText.setColumns(10);
		
		passText = new JPasswordField();
		passText.setBounds(222, 250, 197, 34);
		passText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		panel_1.add(passText);
		
		passLabel = new JLabel("M\u1EADt kh\u1EA9u");
		passLabel.setBounds(179, 221, 68, 24);
		passLabel.setForeground(new Color(255, 255, 255));
		passLabel.setBackground(new Color(255, 255, 255));
		passLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_1.add(passLabel);
		
		idLabel = new JLabel("T\u00E0i kho\u1EA3n");
		idLabel.setBounds(179, 126, 80, 42);
		idLabel.setForeground(new Color(255, 255, 255));
		idLabel.setBackground(new Color(102, 204, 153));
		idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_1.add(idLabel);
		
		btnLogin = new JButton("\u0110\u0103ng Nh\u1EADp");
		btnLogin.setBounds(246, 322, 155, 34);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(userText.getText()+" "+passText.getText());
				if(userText.getText().equals("student") && passText.getText().toString().equals("1")) {
					HocSinhFrame new_student = new HocSinhFrame();
					new_student.setVisible(true);
				}
				
				if(userText.getText().equals("teacher") && passText.getText().equals("1")) {
					GiaoVienScreen new_teacher = new GiaoVienScreen();
					new_teacher.setVisible(true);
				}
				
				else {
					//Login_Log.info( id + ": Login Fail");
					JOptionPane.showMessageDialog(null, "Invalid Username or Password!!");
					userText.setText("");
					passText.setText("");
				}
			}
		});
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		panel_1.add(btnLogin);
		

		
		titleLabel = new JLabel("QU\u1EA2N L\u00CD \u0110I\u1EC2M S\u1ED0");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		titleLabel.setBounds(10, 10, 619, 91);
		panel_1.add(titleLabel);
		titleLabel.setBackground(new Color(102, 204, 255));
		titleLabel.setOpaque(true);
	}
	
	public ResultSet checkLogin() {
		return null;
	}
	
}
