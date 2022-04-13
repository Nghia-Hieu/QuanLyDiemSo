package com.example.qldiemso.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class HocSinhScreen extends JFrame{

	private JFrame frame;
	private String maHS;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel GradePanel, ReviewStatePanel, DoRatePanel, ManagePanel;
	private JTable TableClass, TableReview, TableRate;
	private JScrollPane scrollPane_class, scrollPane_review, scrollPane_rate;
	private DefaultTableModel model_class, model_review, model_rate;
	private JButton successReviewBtn;
	private JButton declineReviewBtn;
	private JLabel rateTextLabel;
	private JTextField idCPText;
	private JTextField passCPText;
	private JTextField newPassCPText;
	private JTextField confirmPassCPText;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HocSinhScreen window = new HocSinhScreen();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HocSinhScreen() {
		initialize();
	}
	
	public HocSinhScreen(String maHS) {
		this.maHS = maHS;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(HocSinhScreen.this,
						"Do you want to Exit ?", "Exit Confirmation",
						JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION){
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else if (result == JOptionPane.NO_OPTION){
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});

		setBounds(100, 100, 801, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 767, 454);
		contentPane.add(tabbedPane);
		
		
		//--------------------------Panel 1-------------------------------
		GradePanel = new JPanel();
		tabbedPane.addTab("Xem diem", null, GradePanel, null);
		GradePanel.setLayout(null);
		
        
		
		String[] column_class = {"MaHS","Ho va Ten", "Mon","15'", "1 tiet", "GiuaHK", "CuoiHK"};
		model_class=new DefaultTableModel();
		model_class.setColumnIdentifiers(column_class);
		
		scrollPane_class = new JScrollPane();
		scrollPane_class.setBounds(20, 54, 717, 339);
		GradePanel.add(scrollPane_class);
		TableClass = new JTable();
		TableClass.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		Object data[]= {"1","Nguyen Van A", "Sinh", 10,10,10,10};
		model_class.addRow(data);
		
		
		TableClass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		TableClass.setModel(model_class);
		TableColumnModel clmnModel = TableClass.getColumnModel();
		clmnModel.getColumn(0).setPreferredWidth(30);
		clmnModel.getColumn(1).setPreferredWidth(100);
		clmnModel.getColumn(2).setPreferredWidth(40);
		clmnModel.getColumn(3).setPreferredWidth(10);
		clmnModel.getColumn(4).setPreferredWidth(10);
		clmnModel.getColumn(5).setPreferredWidth(10);
		clmnModel.getColumn(6).setPreferredWidth(10);


		scrollPane_class.setViewportView(TableClass);
		
		JButton refreshListBtn = new JButton("L\u00E0m m\u1EDBi");
		refreshListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		refreshListBtn.setBounds(569, 10, 85, 21);
		GradePanel.add(refreshListBtn);
		
		JButton reviewBtn = new JButton("G\u1EEDi ph\u00FAc kh\u1EA3o");
		reviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		reviewBtn.setBounds(310, 396, 122, 21);
		GradePanel.add(reviewBtn);
		
		
		
		
		//--------------------------Panel 2-------------------------------

		ReviewStatePanel = new JPanel();
		tabbedPane.addTab("Phuc khao", null, ReviewStatePanel, null);
		ReviewStatePanel.setLayout(null);
		
		String[] column_review = {"Mon","Ho va Ten", "Lop","Trang thai","Phuc Khao"};
		model_review=new DefaultTableModel();
		model_review.setColumnIdentifiers(column_review);
		
		scrollPane_review = new JScrollPane();
		scrollPane_review.setBounds(20, 21, 717, 269);
		ReviewStatePanel.add(scrollPane_review);
		TableReview = new JTable();
		TableReview.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		
		Object data_review[]= {"Sinh","Nguyen Van A", "10A5", "Dang cho", "15' mon Sinh bi sai"};
		model_review.addRow(data_review);
		
		TableReview.setModel(model_review);
		TableColumnModel clmnModelReview = TableReview.getColumnModel();
		clmnModelReview.getColumn(0).setPreferredWidth(50);
		clmnModelReview.getColumn(1).setPreferredWidth(150);
		clmnModelReview.getColumn(2).setPreferredWidth(50);
		clmnModelReview.getColumn(3).setPreferredWidth(50);
		clmnModelReview.getColumn(4).setMinWidth(350);
		scrollPane_review.setViewportView(TableReview);
		
		successReviewBtn = new JButton("Ch\u1EA5p nh\u1EADn");
		successReviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		successReviewBtn.setBounds(228, 350, 85, 21);
		ReviewStatePanel.add(successReviewBtn);
		
		declineReviewBtn = new JButton("T\u1EEB ch\u1ED1i");
		declineReviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		declineReviewBtn.setBounds(437, 350, 85, 21);
		ReviewStatePanel.add(declineReviewBtn);
		
		
		//--------------------------Panel 3 -------------------------------

		DoRatePanel = new JPanel();
		tabbedPane.addTab("Danh gia giao vien", null, DoRatePanel, null);
		DoRatePanel.setLayout(null);
		
		String[] className = {"Sinh", "Toan", "Ly", "Hoa"};

		JComboBox subjectsComboBox = new JComboBox();
		
		subjectsComboBox.addItem("Chon mon");
		subjectsComboBox.addItem("Sinh");
		subjectsComboBox.addItem("Toan");
		subjectsComboBox.addItem("Ly");
		subjectsComboBox.addItem("Hoa");



		subjectsComboBox.setBounds(136, 289, 135, 21);
		DoRatePanel.add(subjectsComboBox);
		
		JLabel chooseSubjectLabel = new JLabel("Ch\u1ECDn m\u00F4n");
		chooseSubjectLabel.setBounds(50, 293, 58, 13);
		DoRatePanel.add(chooseSubjectLabel);
		
		String[] column_rate = {"Mon", "GiaoVien","Thoi gian", "Danh gia"};
		model_rate=new DefaultTableModel();
		model_rate.setColumnIdentifiers(column_rate);
		
		scrollPane_rate = new JScrollPane();
		scrollPane_rate.setBounds(33, 10, 687, 254);
		DoRatePanel.add(scrollPane_rate);
		TableRate = new JTable();
		TableRate.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		Object data_rate[]= {"Sinh","Nguyen Thi Linh","21/02/2022", "Co day sieu ghe"};
		model_rate.addRow(data_rate);
		TableRate.setModel(model_rate);


		
		TableColumnModel clmnModelRate = TableRate.getColumnModel();
		clmnModelRate.getColumn(0).setPreferredWidth(70);
		clmnModelRate.getColumn(1).setPreferredWidth(100);
		clmnModelRate.getColumn(2).setPreferredWidth(100);
		clmnModelRate.getColumn(3).setMinWidth(300);

		
		scrollPane_rate.setViewportView(TableRate);
		
		rateTextLabel = new JLabel("\u0110\u00E1nh gi\u00E1");
		rateTextLabel.setBounds(50, 346, 58, 13);
		DoRatePanel.add(rateTextLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(136, 340, 351, 77);
		DoRatePanel.add(textPane);
		
		JButton sendBtn = new JButton("G\u1EEDi");
		sendBtn.setBounds(595, 289, 85, 77);
		DoRatePanel.add(sendBtn);
		
		//--------------------------Panel 3 -------------------------------

		ManagePanel = new JPanel();
		tabbedPane.addTab("Quan li tai khoan", null, ManagePanel, null);
		ManagePanel.setLayout(null);
		
		idCPText = new JTextField();
		idCPText.setBounds(363, 118, 123, 19);
		ManagePanel.add(idCPText);
		idCPText.setColumns(10);
		
		JLabel changePassLabel = new JLabel("\u0110\u1ED4I M\u1EACT KH\u1EA8U");
		changePassLabel.setBounds(311, 40, 96, 45);
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
		
		JButton confirmCPLabel = new JButton("X\u00E1c nh\u1EADn \u0111\u1ED5i");
		confirmCPLabel.setBounds(294, 350, 123, 21);
		ManagePanel.add(confirmCPLabel);
				
	}		
}
