package com.example.qldiemso.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTextPane;

public class HocSinhScreen extends JFrame{

	private JFrame frame;
	private String maHS;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel GradePanel, ReviewStatePanel, DoRatePanel;
	private JTable TableClass, TableReview, TableRate;
	private JScrollPane scrollPane_class, scrollPane_review, scrollPane_rate;
	private DefaultTableModel model_class, model_review, model_rate;
	private JButton successReviewBtn;
	private JButton declineReviewBtn;
	private JLabel rateTextLabel;
	
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
	}
}
