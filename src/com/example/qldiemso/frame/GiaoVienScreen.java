package com.example.qldiemso.frame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class GiaoVienScreen extends JFrame implements ActionListener {

	private JPanel top;
	private JPanel center;
	private JPanel bot;
	private JTabbedPane tab;

	private JTextField id_field;
	private JTextField name_field;
	private JTextField test15MinutesField;
	private JTextField testOnePeriodField;
	private JTextField middleSemesterField;
	private JTextField finalSemesterField;
	private JTextField notes_field;

	private JButton save_btn;
	private JButton clear_btn;
	private JButton load_db_btn;
	private JButton save_to_db_btn;
	private JButton export_to_csv_btn;

	private JButton config_db;

	private JButton id_sort_btn;
	private JButton gpa_sort_btn;

	private String maGV;
	private JFrame frame;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel ClassPanel, ReviewPanel, RatePanel;
	private JTable TableClass, TableReview, TableRate;
	private JScrollPane scrollPane_class, scrollPane_review, scrollPane_rate;
	private DefaultTableModel model_class, model_review, model_rate;
	private JButton successReviewBtn;
	private JButton declineReviewBtn;

	JTable table;
	DefaultTableModel model;

	String[] columnNames = { "ID", "Full name", "15 Minutes", "1 Period", "Middle Semester", "Final Semester","Notes" };

	int selectedRow = -1;

	String connectionUrl = "";
	boolean connectedDB = false;
	private JTextField idCPText;
	private JTextField passCPText;
	private JTextField newPassCPText;
	private JTextField confirmPassCPText;

	public static void main(String[] args){
		GiaoVienScreen mainProgram = new GiaoVienScreen();
		mainProgram.showUI();
	}

	public GiaoVienScreen(){
		super("Student management");
		prepareGUI();
	}

	private void showUI(){
		JLabel text = new JLabel("Student Management System");
		text.setFont(new Font("", Font.PLAIN, 40));

		JPanel inputStudentInfo = inputStudentInfo();

		JScrollPane table = showListStudent();
		JPanel sort_btn_panel = sortButton();

		JPanel config = new JPanel();
		config_db = new JButton("CONFIG DATABASE");
		config_db.addActionListener(this);
		config.add(config_db);
		JPanel controlFunction = controlFunction();

		top.add(text);
		top.setPreferredSize(new Dimension(0,60));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		center.add(inputStudentInfo, gbc);
		gbc.gridx = 1;
		center.add(table, gbc);
		gbc.gridy = 1;
		center.add(sort_btn_panel, gbc);

		bot.add(config);
		bot.add(Box.createHorizontalStrut(250));
		bot.add(controlFunction);

		JPanel tabItem1 = new JPanel(new BorderLayout());
		tabItem1.add(top, BorderLayout.NORTH);
		tabItem1.add(center, BorderLayout.CENTER);
		tabItem1.add(bot, BorderLayout.SOUTH);

		JPanel tabItem2 = reviewFromStudent();

		JPanel tabItem3 = rateFromStudent();
		
		JPanel tabItem4 = ManageAccount();

		tab = new JTabbedPane();
		tab.addTab("Quan ly hoc sinh", null, tabItem1);
		tab.addTab("Phuc khao", null, tabItem2);
		tab.addTab("Danh Gia", null, tabItem3);
		tab.addTab("Quan ly tai khoan", null, tabItem4);

		add(tab);

		setVisible(true);
	}

	private void prepareGUI(){
		setSize(1100,600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(GiaoVienScreen.this,
						"Do you want to Exit ?", "Exit Confirmation",
						JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION){
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else if (result == JOptionPane.NO_OPTION){
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});

		top = new JPanel();
		center = new JPanel();
		center.setLayout(new GridBagLayout());
		bot = new JPanel();

		setVisible(true);
	}

//	private List<Student> readFromDatabase(Connection connection) {
//		Statement statement = null;
//		ResultSet resultSet = null;
//
//		List<Student> listStudent = new ArrayList<Student>();
//
//		try{
//			String SQL = "select * from student ";
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery(SQL);
//
//			while (resultSet.next()) {
//				String id = resultSet.getString(1);
//				String name = resultSet.getString(2);
//				String gpa = resultSet.getString(3);
//				String address = resultSet.getString(4);
//				String img = resultSet.getString(5);
//				String notes = resultSet.getString(6);
//
//				boolean isOk = checkValidInput(id, name, gpa, address, img, notes);
//				if (isOk)
//					listStudent.add(new Student(id, name, Float.parseFloat(gpa), address, img, notes));
//			}
//		} catch (Exception ex){
//			JOptionPane.showMessageDialog(frame, "Having some error while get database !",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//		} finally {
//			if(statement != null){
//				try{
//					connection.close();
//				}
//				catch(Exception ex) {
//					ex.printStackTrace();
//				}
//			}
//			if(resultSet != null){
//				try{
//					connection.close();
//				}
//				catch(Exception ex) {
//					ex.printStackTrace();
//				}
//			}
//		}
//
//		return listStudent;
//	}
//
//	private void saveToDatabase(String connectionUrl){
//		Connection connection = null;
//		Statement statement = null;
//
//		try{
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//			connection = DriverManager.getConnection(connectionUrl);
//
//			if(connection != null) {
//				String SQL = "DELETE FROM student";
//
//				statement = connection.createStatement();
//				statement.executeUpdate(SQL);
//
//				for(Student s : listStudent){
//					String value = String.format("'%s', N'%s', '%s', N'%s', N'%s', N'%s'", s.getId(), s.getName(), s.getGPA(),
//							s.getAddress(), s.getImage_src(), s.getNotes());
//					SQL = "INSERT INTO student (id, name, gpa, address, image_src, notes) " +
//							"VALUES (" + value + ")";
//
//					byte[] bytes = SQL.getBytes(StandardCharsets.UTF_8);
//
//					String SQL_utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
//
//
//					statement = connection.createStatement();
//					statement.executeUpdate(SQL_utf8EncodedString);
//				}
//			} else {
//				System.out.println("Connection fail!");
//				JOptionPane.showMessageDialog(frame, "Connect to database fail !",
//						"Warning", JOptionPane.WARNING_MESSAGE);
//			}
//		}
//		catch (Exception ex){
//			JOptionPane.showMessageDialog(frame, "Connect to database fail !",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//		}
//		finally {
//			if(statement != null){
//				try{
//					statement.close();
//				}
//				catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//			if(connection != null){
//				try{
//					connection.close();
//				}
//				catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	private boolean checkValidInput(String id_ip, String name_ip, String gpa_ip,
//									String address_ip, String imgsrc_ip, String notes_ip){
//		if (id_ip.length() != 8) {
//			JOptionPane.showMessageDialog(frame,
//					"ID invalid! ID have 8 character!",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		if (name_ip.length() > 25) {
//			JOptionPane.showMessageDialog(frame,
//					"Name too long! Input a name with less than 25 character",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		float gpa = 5;
//		try{
//			gpa = Float.parseFloat(gpa_ip);
//		} catch(Exception ex) {
//			JOptionPane.showMessageDialog(frame,
//					"Please input a float number <= 4.0 into GPA field!",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		if(gpa > 4) {
//			JOptionPane.showMessageDialog(frame,
//					"Please input a float number <= 4.0 into GPA field!",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		if (address_ip.length() > 50) {
//			JOptionPane.showMessageDialog(frame,
//					"Address too long! Input an address with less than 50 character",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		if (imgsrc_ip.length() > 40) {
//			JOptionPane.showMessageDialog(frame,
//					"Image source too long! Input a image source with less than 40 character",
//					"Warning", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		if (notes_ip.length() > 50) {
//			JOptionPane.showMessageDialog(frame,
//					"Notes too long! Input notes with less than 50 character",
//					"Invalid Notes input", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
//
//		return true;
//	}
//
//	private boolean configDatabase(){
//		boolean isSuccess = false;
//		JPanel dialogPanel = new JPanel(new GridBagLayout());
//
//		JTextField server = new JTextField(15);
//		server.setText("localhost");
//		JTextField port = new JTextField(15);
//		port.setText("1433");
//		JTextField db_name = new JTextField(15);
//		db_name.setText("studentManagement");
//		JTextField user = new JTextField(15);
//		JPasswordField pass = new JPasswordField(15);
//
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.insets = new Insets(3,5,3,15);
//		gbc.gridx=0;
//		gbc.gridy=0;
//		dialogPanel.add(new JLabel("Server: "), gbc);
//		gbc.gridx=1;
//		dialogPanel.add(server,gbc);
//		gbc.gridx=0;
//		gbc.gridy=1;
//		dialogPanel.add(new JLabel("Port: "), gbc);
//		gbc.gridx=1;
//		gbc.gridy=1;
//		dialogPanel.add(port, gbc);
//		gbc.gridx=0;
//		gbc.gridy=2;
//		dialogPanel.add(new JLabel("Database name: "), gbc);
//		gbc.gridx=1;
//		gbc.gridy=2;
//		dialogPanel.add(db_name, gbc);
//		gbc.gridx=0;
//		gbc.gridy=3;
//		dialogPanel.add(new JLabel("Username: "), gbc);
//		gbc.gridx=1;
//		gbc.gridy=3;
//		dialogPanel.add(user, gbc);
//		gbc.gridx=0;
//		gbc.gridy=4;
//		dialogPanel.add(new JLabel("Password: "), gbc);
//		gbc.gridx=1;
//		gbc.gridy=4;
//		dialogPanel.add(pass, gbc);
//
//		int result = JOptionPane.showConfirmDialog(frame, dialogPanel, "Config database",
//				JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
//
//		if(result == JOptionPane.OK_OPTION) {
//			connectionUrl =
//					String.format("jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s",
//							server.getText(), port.getText(), db_name.getText(), user.getText(), String.valueOf(pass.getPassword()));
//			Connection connection = null;
//
//			try{
//				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//				connection = DriverManager.getConnection(connectionUrl);
//
//				if(connection != null) {
//					isSuccess = true;
//					JOptionPane.showMessageDialog(frame,
//							"Connected to database " + db_name.getText(), "Success",
//							JOptionPane.INFORMATION_MESSAGE);
//				} else {
//					JOptionPane.showMessageDialog(frame, "Connect to database fail !",
//							"Warning", JOptionPane.WARNING_MESSAGE);
//				}
//			}
//			catch (Exception ex){
//				ex.printStackTrace();
//				JOptionPane.showMessageDialog(frame, "Connect to database fail !",
//						"Warning", JOptionPane.WARNING_MESSAGE);
//			}
//			finally {
//				if(connection != null){
//					try{
//						connection.close();
//					}
//					catch(Exception ex) {
//						ex.printStackTrace();
//					}
//				}
//			}
//		}
//
//		return isSuccess;
//	}

	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == add_btn) {
//			String id = id_field.getText();
//			String name = name_field.getText();
//			String gpa = gpa_field.getText();
//			String address = address_field.getText();
//			String img = img_field.getText();
//			String notes = notes_field.getText();
//
//			boolean isOk = checkValidInput(id, name, gpa, address, img, notes);
//
//			if (Operation.checkID(listStudent, id_field.getText()) == 1) {
//				isOk = false;
//				JOptionPane.showMessageDialog(frame,
//						"ID " + id_field.getText() + " is existing!");
//			}
//
//			if(isOk) {
//				Student std = new Student(id, name, Float.parseFloat(gpa), address, img, notes);
//				Operation.addStudent(listStudent, std);
//				fillDataToTable(listStudent);
//
//				id_field.setText("");
//				name_field.setText("");
//				gpa_field.setText("");
//				address_field.setText("");
//				img_field.setText("");
//				notes_field.setText("");
//			}
//		}
//		else if(e.getSource() == update_btn) {
//			String id = id_field.getText();
//			String name = name_field.getText();
//			String gpa = gpa_field.getText();
//			String address = address_field.getText();
//			String img = img_field.getText();
//			String notes = notes_field.getText();
//
//			boolean isOk = checkValidInput(id, name, gpa, address, img, notes);
//
//			if (Operation.checkID(listStudent, id_field.getText()) == 1 &&
//					!model.getValueAt(selectedRow,0).toString().equals(id_field.getText())) {
//				isOk = false;
//				JOptionPane.showMessageDialog(frame,
//						"ID " + id_field.getText() + " is existing!");
//			}
//
//			if(isOk) {
//				Student std = new Student(id, name, Float.parseFloat(gpa), address, img, notes);
//				Operation.updateStudent(listStudent,
//						String.valueOf(model.getValueAt(selectedRow, 0)), std);
//
//				fillDataToTable(listStudent);
//
//				id_field.setText("");
//				name_field.setText("");
//				gpa_field.setText("");
//				address_field.setText("");
//				img_field.setText("");
//				notes_field.setText("");
//			}
//		}
//		else if(e.getSource() == delete_btn) {
//			if(listStudent.size() == 0){
//				JOptionPane.showMessageDialog(frame, "Table empty !!!", "Invalid Deletion",
//						JOptionPane.ERROR_MESSAGE);
//			} else if(table.getSelectedRow() == -1){
//				JOptionPane.showMessageDialog(frame, "Please choose a student to delete !!!",
//						"Invalid Deletion", JOptionPane.ERROR_MESSAGE);
//			} else{
//				Operation.deleteStudent(listStudent,
//						String.valueOf(model.getValueAt(selectedRow, 0)));
//				fillDataToTable(listStudent);
//
//				id_field.setText("");
//				name_field.setText("");
//				gpa_field.setText("");
//				address_field.setText("");
//				img_field.setText("");
//				notes_field.setText("");
//			}
//		}
//		else if(e.getSource() == config_db){
//			connectedDB = configDatabase();
//		}
//		else if(e.getSource() == new_file_btn) {
//			listStudent = new ArrayList<Student>();
//			fillDataToTable(listStudent);
//		}
//		else if(e.getSource() == load_db_btn) {
//			if(!connectedDB){
//				connectedDB = configDatabase();
//			}
//
//			if(connectedDB) {
//				Connection connection = null;
//
//				try {
//					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//					connection = DriverManager.getConnection(connectionUrl);
//
//					if (connection != null) {
//						listStudent = readFromDatabase(connection);
//						fillDataToTable(listStudent);
//						JOptionPane.showMessageDialog(frame, "Load from database done", "Success",
//								JOptionPane.INFORMATION_MESSAGE);
//					} else {
//						System.out.println("Connection fail!");
//						JOptionPane.showMessageDialog(frame, "Connect to database fail !",
//								"Warning", JOptionPane.WARNING_MESSAGE);
//					}
//				} catch (Exception ex) {
//					JOptionPane.showMessageDialog(frame, "Connect to database fail !",
//							"Warning", JOptionPane.WARNING_MESSAGE);
//				} finally {
//					if (connection != null) {
//						try {
//							connection.close();
//						} catch (Exception ex) {
//							ex.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//		else if(e.getSource() == save_to_db_btn) {
//			if(!connectedDB){
//				connectedDB = configDatabase();
//			}
//			if(connectedDB) {
//				int ok;
//				if(listStudent.isEmpty()) {
//					ok = JOptionPane.showConfirmDialog(frame,
//							"File is empty, this action will overwrite data in your database, you want to continue ?",
//							"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//				}
//				else{
//					ok = JOptionPane.showConfirmDialog(frame,
//							"This action will overwrite data in your database, you want to continue ?",
//							"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//				}
//
//				if(ok == JOptionPane.YES_OPTION) {
//					saveToDatabase(connectionUrl);
//					JOptionPane.showMessageDialog(frame, "Save to database done!", "Success",
//							JOptionPane.INFORMATION_MESSAGE);
//				}
//			}
//		}
//		else if(e.getSource() == load_csv_btn) {
//			String path = null;
//
//			String userDirLocation = System.getProperty("user.dir");
//			File userDir = new File(userDirLocation);
//			JFileChooser jFileChooser = new JFileChooser(userDir);
//			jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
//					"File .csv",
//					"csv"
//			));
//			int n = jFileChooser.showOpenDialog(frame);
//
//			if(n == JFileChooser.APPROVE_OPTION) {
//				listStudent = new ArrayList<Student>();
//				path = jFileChooser.getSelectedFile().getAbsolutePath();
//
//				try {
//					List<List<String>> temp = Operation.readFileCSV(path);
//					for (List<String> s : temp) {
//						String id = s.get(0);
//						String name = s.get(1);
//						String gpa = s.get(2);
//						String address = s.get(3);
//						String img = s.get(4);
//						String notes = s.get(5);
//
//						boolean isOk = checkValidInput(id, name, String.valueOf(gpa), address, img, notes);
//
//						if (Operation.checkID(listStudent, id_field.getText()) == 1) {
//							isOk = false;
//							JOptionPane.showMessageDialog(frame,
//									"ID " + id_field.getText() + " is existing!");
//						}
//
//						if (isOk)
//							listStudent.add(new Student(id, name, Float.parseFloat(gpa), address, img, notes));
//						else
//							JOptionPane.showMessageDialog(frame, "Some error when add student " +
//									id + ", please check again!", "Warning", JOptionPane.WARNING_MESSAGE);
//					}
//					fillDataToTable(listStudent);
//					JOptionPane.showMessageDialog(frame,
//							"Import file " + jFileChooser.getSelectedFile().getName()+" done!",
//							"Success", JOptionPane.INFORMATION_MESSAGE);
//				} catch(Exception ex){
//					JOptionPane.showMessageDialog(frame,"Some error while import file .csv, please try again !",
//							"Warning", JOptionPane.WARNING_MESSAGE);
//				}
//			}
//		}
//		else if(e.getSource() == export_to_csv_btn) {
//			if(listStudent.size() == 0){
//				JOptionPane.showMessageDialog(frame, "Table is empty !!!", "Warning",
//						JOptionPane.WARNING_MESSAGE);
//			} else{
//				String path = "";
//				String userDirLocation = System.getProperty("user.dir");
//				File userDir = new File(userDirLocation);
//				JFileChooser jFileChooser = new JFileChooser(userDir);
//				jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
//						"CSV file", "csv"
//				));
//
//				int n = jFileChooser.showSaveDialog(frame);
//
//
//				if(n == JFileChooser.APPROVE_OPTION) {
//					File file = jFileChooser.getSelectedFile();
//					if (file == null) {
//						return;
//					}
//					if (!file.getName().toLowerCase().endsWith(".csv")) {
//						file = new File(file.getParentFile(), file.getName() + ".csv");
//					}
//
//					path = file.toString();
//
//					Operation.exportCSV(listStudent, path);
//					JOptionPane.showMessageDialog(frame, "Export done!", "Success",
//							JOptionPane.INFORMATION_MESSAGE);
//				}
//			}
//		}
//		else if(e.getSource() == id_sort_btn){
//			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
//			table.setRowSorter(sorter);
//
//			List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>(50);
//			sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
//			sorter.setSortKeys(sortKeys);
//		}
//		else if(e.getSource() == gpa_sort_btn){
//			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
//			table.setRowSorter(sorter);
//
//			List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
//			sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
//			sorter.setSortKeys(sortKeys);
//		}
	}

	private JPanel inputStudentInfo(){
		int col = 20;
		int size_text = 15;
		//-------------- id --------------------------------------
		JLabel id_label = new JLabel("ID Student");
		id_label.setFont(new Font(null, Font.PLAIN, size_text));
		id_field = new JTextField(col);
		id_field.setFont(new Font(null, Font.PLAIN, size_text));
		id_field.setEditable(false);
		//-------------- id --------------------------------------

		//-------------- name --------------------------------------
		JLabel name_label = new JLabel("Full name");
		name_label.setFont(new Font(null, Font.PLAIN, size_text));
		name_field = new JTextField(col);
		name_field.setFont(new Font(null, Font.PLAIN, size_text));
		name_field.setEditable(false);
		//-------------- name --------------------------------------

		//-------------- GPA --------------------------------------
		JLabel test15MinutesLabel = new JLabel("15 Minutes");
		test15MinutesLabel.setFont(new Font(null, Font.PLAIN, size_text));
		test15MinutesField = new JTextField(col);
		test15MinutesField.setFont(new Font(null, Font.PLAIN, size_text));
		//-------------- GPA --------------------------------------

		//-------------- address --------------------------------------
		JLabel onePeriodLabel = new JLabel("1 Period");
		onePeriodLabel.setFont(new Font(null, Font.PLAIN, size_text));
		testOnePeriodField = new JTextField(col);
		testOnePeriodField.setFont(new Font(null, Font.PLAIN, size_text));
		//-------------- address --------------------------------------

		//-------------- img --------------------------------------
		JLabel middleSemesterLabel = new JLabel("Middle Semester");
		middleSemesterLabel.setFont(new Font(null, Font.PLAIN, size_text));
		middleSemesterField = new JTextField(col);
		middleSemesterField.setFont(new Font(null, Font.PLAIN, size_text));
		//-------------- img --------------------------------------

		//-------------- img --------------------------------------
		JLabel finalSemesterLabel = new JLabel("Final Semester");
		finalSemesterLabel.setFont(new Font(null, Font.PLAIN, size_text));
		finalSemesterField = new JTextField(col);
		finalSemesterField.setFont(new Font(null, Font.PLAIN, size_text));
		//-------------- img --------------------------------------

		//-------------- notes --------------------------------------
		JLabel notes_label = new JLabel("Notes");
		notes_label.setFont(new Font(null, Font.PLAIN, size_text));
		notes_field = new JTextField();
		notes_field.setFont(new Font(null, Font.PLAIN, size_text));
		//-------------- notes --------------------------------------

		JPanel info = new JPanel();
		info.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,5,5,10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 10;
		info.add(id_label, gbc);
		gbc.gridy = 1;
		info.add(name_label, gbc);
		gbc.gridy = 2;
		info.add(test15MinutesLabel, gbc);
		gbc.gridy = 3;
		info.add(onePeriodLabel, gbc);
		gbc.gridy = 4;
		info.add(middleSemesterLabel, gbc);
		gbc.gridy = 5;
		info.add(finalSemesterLabel, gbc);
		gbc.gridy = 6;
		info.add(notes_label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		info.add(id_field, gbc);
		gbc.gridy = 1;
		info.add(name_field, gbc);
		gbc.gridy = 2;
		info.add(test15MinutesField, gbc);
		gbc.gridy = 3;
		info.add(testOnePeriodField, gbc);
		gbc.gridy = 4;
		info.add(middleSemesterField, gbc);
		gbc.gridy = 5;
		info.add(finalSemesterField, gbc);
		gbc.gridy = 6;
		info.add(notes_field, gbc);

		save_btn = new JButton("SAVE");
		save_btn.setFont(new Font("", Font.BOLD, size_text));
		save_btn.setBackground(Color.green);
		save_btn.setPreferredSize(new Dimension(100,30));
		save_btn.addActionListener(this);

		clear_btn = new JButton("CLEAR");
		clear_btn.setFont(new Font("", Font.BOLD, size_text));
		clear_btn.setBackground(Color.red);
		clear_btn.setPreferredSize(new Dimension(100,30));
		clear_btn.addActionListener(this);

		JPanel btn = new JPanel();
		btn.setLayout(new FlowLayout());
		btn.add(save_btn);
		btn.add(clear_btn);

		JPanel result = new JPanel();
		result.setLayout(new GridBagLayout());
		gbc.insets = new Insets(5,5,5,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		result.add(info, gbc);
		gbc.gridy = 1;
		result.add(btn, gbc);

		return result;
	}

	private JPanel rateFromStudent() {
		JPanel ratePanel = new JPanel(new BorderLayout());

		String[] column_rate = {"STT", "Ho va ten", "Danh Gia"};
		model_rate=new DefaultTableModel();
		model_rate.setColumnIdentifiers(column_rate);

		scrollPane_rate = new JScrollPane();
		//scrollPane_rate.setBounds(10, 10, 729, 395);

		TableRate = new JTable();
		TableRate.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		Object data_rate[]= {"1","Tran Hoang Phuc","Co day chan, hoc thi it tiep thu con khong thi cha co gi de quan tam"};
		model_rate.addRow(data_rate);
		TableRate.setModel(model_rate);

		TableColumnModel clmnModelRate = TableRate.getColumnModel();
		clmnModelRate.getColumn(0).setPreferredWidth(30);
		clmnModelRate.getColumn(1).setMinWidth(200);
		clmnModelRate.getColumn(2).setMinWidth(500);

		scrollPane_rate.setViewportView(TableRate);

		ratePanel.add(scrollPane_rate, BorderLayout.CENTER);

		return ratePanel;
	}

	private JPanel reviewFromStudent(){
		JPanel ReviewPanel = new JPanel(new BorderLayout());

		String[] column_review = {"MaHS","Ho ten", "Lop","Noi dung phuc khao"};
		model_review=new DefaultTableModel();
		model_review.setColumnIdentifiers(column_review);

		scrollPane_review = new JScrollPane();
		scrollPane_review.setBounds(20, 21, 717, 269);

		TableReview = new JTable();
		TableReview.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		Object data_review[]= {"1","Nguyen Van A", "10A5", "Diem 15' mon Sinh bi sai"};
		model_review.addRow(data_review);

		TableReview.setModel(model_review);
		TableColumnModel clmnModelReview = TableReview.getColumnModel();
		clmnModelReview.getColumn(0).setPreferredWidth(20);
		clmnModelReview.getColumn(1).setPreferredWidth(100);
		clmnModelReview.getColumn(2).setPreferredWidth(20);
		clmnModelReview.getColumn(3).setMinWidth(465);
		scrollPane_review.setViewportView(TableReview);

		successReviewBtn = new JButton("Ch\u1EA5p nh\u1EADn");
		successReviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		successReviewBtn.setBounds(228, 350, 85, 21);

		declineReviewBtn = new JButton("T\u1EEB ch\u1ED1i");
		declineReviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		declineReviewBtn.setBounds(437, 350, 85, 21);

		JPanel btn = new JPanel();
		btn.add(successReviewBtn);
		btn.add(declineReviewBtn);

		ReviewPanel.add(scrollPane_review,BorderLayout.CENTER);
		ReviewPanel.add(btn, BorderLayout.SOUTH);

		return ReviewPanel;
	}
	
	
	private JPanel ManageAccount() {
		JPanel ManagePanel = new JPanel();
		idCPText = new JTextField();
		idCPText.setBounds(363, 118, 123, 19);
		idCPText.setEditable(false);
		idCPText.setColumns(10);
		ManagePanel.add(idCPText);
		
		JLabel changePassLabel = new JLabel("\u0110\u1ED4I M\u1EACT KH\u1EA8U");
		changePassLabel.setBounds(300, 37, 96, 45);
		ManagePanel.add(changePassLabel);
		
		passCPText = new JTextField();
		passCPText.setBounds(363, 171, 123, 19);
		ManagePanel.add(passCPText);
		passCPText.setColumns(10);
		
		JLabel idLabelforchange = new JLabel("ID h\u1ECDc sinh");
		idLabelforchange.setBounds(226, 118, 102, 16);
		ManagePanel.add(idLabelforchange);
		
		JLabel oldPassLabel = new JLabel("Password hi\u1EC7n t\u1EA1i");
		oldPassLabel.setBounds(226, 174, 102, 16);
		ManagePanel.add(oldPassLabel);
		
		JLabel newPassLabel = new JLabel("Password m\u1EDBi");
		newPassLabel.setBounds(226, 229, 102, 16);
		ManagePanel.add(newPassLabel);
		
		newPassCPText = new JTextField();
		newPassCPText.setColumns(10);
		newPassCPText.setBounds(363, 228, 123, 19);
		ManagePanel.add(newPassCPText);
		
		JLabel confirmPassLabel = new JLabel("X\u00E1c nh\u1EADn pass m\u1EDBi");
		confirmPassLabel.setBounds(226, 281, 114, 16);
		ManagePanel.add(confirmPassLabel);
		
		confirmPassCPText = new JTextField();
		confirmPassCPText.setColumns(10);
		confirmPassCPText.setBounds(363, 280, 123, 19);
		ManagePanel.add(confirmPassCPText);
		
		JButton confirmCPBtn = new JButton("X\u00E1c nh\u1EADn \u0111\u1ED5i");
		confirmCPBtn.setBounds(294, 350, 102, 21);
		ManagePanel.add(confirmCPBtn);
		
		JPanel btn = new JPanel();
		btn.add(confirmPassLabel);
		btn.add(confirmCPBtn);
		
		ManagePanel.add(btn, BorderLayout.CENTER);
		
		return ManagePanel;
	}
//
//	private void fillDataToTable(List<Student> listStudent){
//		model.setRowCount(0);
//		for (Student student : listStudent) {
//			model.addRow(new Object[]{student.getId(), student.getName(), student.getGPA(),
//					student.getAddress(), student.getImage_src(), student.getNotes()});
//		}
//	}
//
	private void actionWhenInteractTable(){
		int viewRow = table.getSelectedRow();
		selectedRow = table.convertRowIndexToModel(viewRow);

		id_field.setText(model.getValueAt(selectedRow, 0).toString());
		name_field.setText(model.getValueAt(selectedRow, 1).toString());
		test15MinutesField.setText(model.getValueAt(selectedRow, 2).toString());
		testOnePeriodField.setText(model.getValueAt(selectedRow, 3).toString());
		middleSemesterField.setText(model.getValueAt(selectedRow, 4).toString());
		notes_field.setText(model.getValueAt(selectedRow, 5).toString());
	}

	private JScrollPane showListStudent(){
		String[][] data = {};
		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actionWhenInteractTable();
			}
		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				actionWhenInteractTable();
			}
		});

		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(600,300));

		return sp;
	}

	private JPanel sortButton() {
		JPanel button_panel = new JPanel();

		id_sort_btn = new JButton("Sort asc by ID");
		gpa_sort_btn = new JButton("Sort asc by GPA");

		id_sort_btn.addActionListener(this);
		gpa_sort_btn.addActionListener(this);

		button_panel.add(id_sort_btn);
		button_panel.add(gpa_sort_btn);

		return button_panel;
	}

	private JPanel controlFunction(){
		JPanel function = new JPanel();

		load_db_btn = new JButton("LOAD DATABASE");
		save_to_db_btn = new JButton("SAVE TO DATABASE");
		export_to_csv_btn = new JButton("EXPORT .CSV");

		load_db_btn.addActionListener(this);
		save_to_db_btn.addActionListener(this);
		export_to_csv_btn.addActionListener(this);

		function.setLayout(new FlowLayout());
		function.add(load_db_btn);
		function.add(save_to_db_btn);
		function.add(export_to_csv_btn);

		return function;
	}
//	private String maGV;
//	private JFrame frame;
//	private JPanel contentPane;
//	private JTabbedPane tabbedPane;
//	private JPanel ClassPanel, ReviewPanel, RatePanel;
//	private JTable TableClass, TableReview, TableRate;
//	private JScrollPane scrollPane_class, scrollPane_review, scrollPane_rate;
//	private DefaultTableModel model_class, model_review, model_rate;
//	private JButton successReviewBtn;
//	private JButton declineReviewBtn;
//
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GiaoVienFrame window = new GiaoVienFrame();
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the application.
//	 */
//	public GiaoVienFrame() {
//		initialize();
//	}
//	public GiaoVienFrame(String maGV) {
//		this.maGV = maGV;
//		initialize();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 801, 511);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//
//		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		tabbedPane.setBounds(10, 10, 767, 454);
//		contentPane.add(tabbedPane);
//
//
//		//--------------------------Panel 1-------------------------------
//		ClassPanel = new JPanel();
//		tabbedPane.addTab("Quan li diem so", null, ClassPanel, null);
//		ClassPanel.setLayout(null);
//
//        String[] className = {"10A5", "11A3", "12CL", "11A5"};
//
//		JComboBox classesComboBox = new JComboBox();
//
//		classesComboBox.addItem("Chon Lop");
//		classesComboBox.addItem("10A5");
//		classesComboBox.addItem("11A5");
//		classesComboBox.addItem("12A5");
//
//
//		classesComboBox.setBounds(132, 10, 135, 21);
//		ClassPanel.add(classesComboBox);
//
//		JLabel chooseClassLabel = new JLabel("Ch\u1ECDn l\u1EDBp");
//		chooseClassLabel.setBounds(39, 14, 58, 13);
//		ClassPanel.add(chooseClassLabel);
//
//		String[] column_class = {"MaHS","Ho va Ten", "Lop","15'", "1 tiet", "GiuaHK", "CuoiHK", "Ghi chu"};
//		model_class=new DefaultTableModel();
//		model_class.setColumnIdentifiers(column_class);
//
//		scrollPane_class = new JScrollPane();
//		scrollPane_class.setBounds(20, 54, 717, 339);
//		ClassPanel.add(scrollPane_class);
//		TableClass = new JTable();
//		TableClass.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//		Object data[]= {"1","Nguyen Van A", "10A5", 10,10,10,10,"Tot"};
//		model_class.addRow(data);
//
//
//		TableClass.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//
//			}
//		});
//		TableClass.setModel(model_class);
//		TableColumnModel clmnModel = TableClass.getColumnModel();
//		clmnModel.getColumn(0).setPreferredWidth(30);
//		clmnModel.getColumn(1).setPreferredWidth(100);
//		clmnModel.getColumn(2).setPreferredWidth(40);
//		clmnModel.getColumn(3).setPreferredWidth(10);
//		clmnModel.getColumn(4).setPreferredWidth(10);
//		clmnModel.getColumn(5).setPreferredWidth(10);
//		clmnModel.getColumn(6).setPreferredWidth(10);
//		clmnModel.getColumn(7).setPreferredWidth(200);
//
//
//		scrollPane_class.setViewportView(TableClass);
//
//		JButton RefreshClassBtn = new JButton("L\u00E0m m\u1EDBi");
//		RefreshClassBtn.setBounds(569, 10, 85, 21);
//		ClassPanel.add(RefreshClassBtn);
//
//		JButton updateListBtn = new JButton("C\u1EADp nh\u1EADt");
//		updateListBtn.setBounds(347, 396, 85, 21);
//		ClassPanel.add(updateListBtn);
//
//
//
//
//		//--------------------------Panel 2-------------------------------
//
//		ReviewPanel = new JPanel();
//		tabbedPane.addTab("Phuc khao", null, ReviewPanel, null);
//		ReviewPanel.setLayout(null);
//
//		String[] column_review = {"MaHS","Ho va Ten", "Lop","Phuc Khao"};
//		model_review=new DefaultTableModel();
//		model_review.setColumnIdentifiers(column_review);
//
//		scrollPane_review = new JScrollPane();
//		scrollPane_review.setBounds(20, 21, 717, 269);
//		ReviewPanel.add(scrollPane_review);
//		TableReview = new JTable();
//		TableReview.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//
//		Object data_review[]= {"1","Nguyen Van A", "10A5", "Diem 15' mon Sinh bi sai"};
//		model_review.addRow(data_review);
//
//		TableReview.setModel(model_review);
//		TableColumnModel clmnModelReview = TableReview.getColumnModel();
//		clmnModelReview.getColumn(0).setPreferredWidth(50);
//		clmnModelReview.getColumn(1).setPreferredWidth(150);
//		clmnModelReview.getColumn(2).setPreferredWidth(50);
//		clmnModelReview.getColumn(3).setMinWidth(465);
//		scrollPane_review.setViewportView(TableReview);
//
//		successReviewBtn = new JButton("Ch\u1EA5p nh\u1EADn");
//		successReviewBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		successReviewBtn.setBounds(228, 350, 85, 21);
//		ReviewPanel.add(successReviewBtn);
//
//		declineReviewBtn = new JButton("T\u1EEB ch\u1ED1i");
//		declineReviewBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		});
//		declineReviewBtn.setBounds(437, 350, 85, 21);
//		ReviewPanel.add(declineReviewBtn);
//
//
//		//--------------------------Panel 3 -------------------------------
//
//		RatePanel = new JPanel();
//		tabbedPane.addTab("Danh gia cua hoc sinh", null, RatePanel, null);
//		RatePanel.setLayout(null);
//
//		String[] column_rate = {"STT", "Danh gia"};
//		model_rate=new DefaultTableModel();
//		model_rate.setColumnIdentifiers(column_rate);
//
//		scrollPane_rate = new JScrollPane();
//		scrollPane_rate.setBounds(10, 10, 729, 395);
//		RatePanel.add(scrollPane_rate);
//		TableRate = new JTable();
//		TableRate.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//
//		Object data_rate[]= {"1","Co day chan, hoc thi it tiep thu con khong thi cha co gi de quan tam"};
//		model_rate.addRow(data_rate);
//		TableRate.setModel(model_rate);
//
//
//
//		TableColumnModel clmnModelRate = TableRate.getColumnModel();
//		clmnModelRate.getColumn(0).setPreferredWidth(30);
//		clmnModelRate.getColumn(1).setMinWidth(500);
//
//
//		scrollPane_rate.setViewportView(TableRate);
//	}
}
