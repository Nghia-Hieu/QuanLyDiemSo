package com.example.qldiemso.frame;

import com.example.qldiemso.model.GiaoVien;
import com.example.qldiemso.string.ConfigUserSetting;
import com.example.qldiemso.string.GiaoVienDtb;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;

public class DangNhapFrame extends JFrame {

	public JFrame frame;
	private JTextField userText;
	private JPasswordField passText;
	// private Account thisAccount;
	private JPanel panel, panel_1;
	private JLabel passLabel, idLabel, lblNewLabel_2, titleLabel;
	private JButton btnLogin;

	private String connectionUrl = "";
	boolean connectedDB = false;

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
		
		if(!connectedDB){
			connectedDB = configDatabase();
		}

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
				System.out.println(userText.getText() + " " + passText.getText());
				String user = userText.getText().toString();
				String pass = passText.getText().toString();
				if (checkLogin(user, pass) == 1) {
					closeLogin();
					HocSinhScreen new_student = new HocSinhScreen(getStudentID(user));
					new_student.setVisible(true);
				}
				// else if(checkLogin(user, pass)==2)
				else if (userText.getText().equals("teacher") && passText.getText().equals("1")) {
					closeLogin();
					GiaoVienDtb db = new GiaoVienDtb();

					GiaoVien teacher = db.getTeacher("CaoMinhPhuc");
					GiaoVienScreen new_teacher = new GiaoVienScreen(teacher);
					// new_teacher.setVisible(true);
				}

				else {
					// Login_Log.info( id + ": Login Fail");
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

	private int checkLogin(String user, String pass) {
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("SELECT * FROM TaiKhoan WHERE TenDangNhap = '%s' AND MatKhau = '%s'", user, pass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);
			if (resultSet.next()) {
				//resultSet.next();
				System.out.println(resultSet.getString(4));
				if (resultSet.getString(4).equals("HS"))
					return 1;
				if (resultSet.getString(4).equals("GV"))
					return 2;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private int getStudentID(String tdn) {
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("SELECT * FROM TaiKhoan WHERE TenDangNhap = '%s' ", tdn);
			System.out.println(SQL);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);
			if (resultSet.next()) {
				//resultSet.next();
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private boolean configDatabase() {
		boolean isSuccess = false;
		JPanel dialogPanel = new JPanel(new GridBagLayout());

		JTextField server = new JTextField(15);
		server.setText("localhost");
		JTextField port = new JTextField(15);
		port.setText("1433");
		JTextField db_name = new JTextField(15);
		db_name.setText("ManageScore");
		JTextField user = new JTextField(15);
		JPasswordField pass = new JPasswordField(15);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3, 5, 3, 15);
		gbc.gridx = 0;
		gbc.gridy = 0;
		dialogPanel.add(new JLabel("Server: "), gbc);
		gbc.gridx = 1;
		dialogPanel.add(server, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		dialogPanel.add(new JLabel("Port: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		dialogPanel.add(port, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		dialogPanel.add(new JLabel("Database name: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		dialogPanel.add(db_name, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		dialogPanel.add(new JLabel("Username: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		dialogPanel.add(user, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		dialogPanel.add(new JLabel("Password: "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		dialogPanel.add(pass, gbc);

		int result = JOptionPane.showConfirmDialog(panel, dialogPanel, "Config database", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			connectionUrl = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s",
					server.getText(), port.getText(), db_name.getText(), user.getText(),
					String.valueOf(pass.getPassword()));
			Connection connection = null;

			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(connectionUrl);

				if (connection != null) {
					isSuccess = true;

					ConfigUserSetting.databaseName = db_name.getText();
					ConfigUserSetting.username = user.getText();
					ConfigUserSetting.password = String.valueOf(pass.getPassword());

					JOptionPane.showMessageDialog(panel, "Connected to database " + db_name.getText(), "Success",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(panel, "Connect to database fail !", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(panel, "Connect to database fail !", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		return isSuccess;
	}

}
