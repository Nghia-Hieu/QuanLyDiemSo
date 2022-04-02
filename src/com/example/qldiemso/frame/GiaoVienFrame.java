package com.example.qldiemso.frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JComboBox;

public class GiaoVienFrame extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel ClassPanel, ReviewPanel, RatePanel;
	private JTable TableClass, TableReview, TableRate;
	private JScrollPane scrollPane_class, scrollPane_review, scrollPane_rate;
	private DefaultTableModel model_class, model_review, model_rate;
	private JButton successReviewBtn;
	private JButton declineReviewBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GiaoVienFrame window = new GiaoVienFrame();
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
	public GiaoVienFrame() {
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
		ClassPanel = new JPanel();
		tabbedPane.addTab("Quan li diem so", null, ClassPanel, null);
		ClassPanel.setLayout(null);
		
        String[] className = {"10A5", "11A3", "12CL", "11A5"};

		JComboBox classesComboBox = new JComboBox();
		
		classesComboBox.addItem("Chon Lop");
		classesComboBox.addItem("10A5");
		classesComboBox.addItem("11A5");
		classesComboBox.addItem("12A5");


		classesComboBox.setBounds(132, 10, 135, 21);
		ClassPanel.add(classesComboBox);
		
		JLabel chooseClassLabel = new JLabel("Ch\u1ECDn l\u1EDBp");
		chooseClassLabel.setBounds(39, 14, 58, 13);
		ClassPanel.add(chooseClassLabel);
		
		String[] column_class = {"MaHS","Ho va Ten", "Lop","15'", "1 tiet", "GiuaHK", "CuoiHK", "Ghi chu"};
		model_class=new DefaultTableModel();
		model_class.setColumnIdentifiers(column_class);
		
		scrollPane_class = new JScrollPane();
		scrollPane_class.setBounds(20, 54, 717, 339);
		ClassPanel.add(scrollPane_class);
		TableClass = new JTable();
		TableClass.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		Object data[]= {"1","Nguyen Van A", "10A5", 10,10,10,10,"Tot"};
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
		clmnModel.getColumn(7).setPreferredWidth(200);


		scrollPane_class.setViewportView(TableClass);
		
		JButton RefreshClassBtn = new JButton("L\u00E0m m\u1EDBi");
		RefreshClassBtn.setBounds(569, 10, 85, 21);
		ClassPanel.add(RefreshClassBtn);
		
		JButton updateListBtn = new JButton("C\u1EADp nh\u1EADt");
		updateListBtn.setBounds(347, 396, 85, 21);
		ClassPanel.add(updateListBtn);
		
		
		
		
		//--------------------------Panel 2-------------------------------

		ReviewPanel = new JPanel();
		tabbedPane.addTab("Phuc khao", null, ReviewPanel, null);
		ReviewPanel.setLayout(null);
		
		String[] column_review = {"MaHS","Ho va Ten", "Lop","Phuc Khao"};
		model_review=new DefaultTableModel();
		model_review.setColumnIdentifiers(column_review);
		
		scrollPane_review = new JScrollPane();
		scrollPane_review.setBounds(20, 21, 717, 269);
		ReviewPanel.add(scrollPane_review);
		TableReview = new JTable();
		TableReview.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		
		Object data_review[]= {"1","Nguyen Van A", "10A5", "Diem 15' mon Sinh bi sai"};
		model_review.addRow(data_review);
		
		TableReview.setModel(model_review);
		TableColumnModel clmnModelReview = TableReview.getColumnModel();
		clmnModelReview.getColumn(0).setPreferredWidth(50);
		clmnModelReview.getColumn(1).setPreferredWidth(150);
		clmnModelReview.getColumn(2).setPreferredWidth(50);
		clmnModelReview.getColumn(3).setMinWidth(465);
		scrollPane_review.setViewportView(TableReview);
		
		successReviewBtn = new JButton("Ch\u1EA5p nh\u1EADn");
		successReviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		successReviewBtn.setBounds(228, 350, 85, 21);
		ReviewPanel.add(successReviewBtn);
		
		declineReviewBtn = new JButton("T\u1EEB ch\u1ED1i");
		declineReviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		declineReviewBtn.setBounds(437, 350, 85, 21);
		ReviewPanel.add(declineReviewBtn);
		
		
		//--------------------------Panel 3 -------------------------------

		RatePanel = new JPanel();
		tabbedPane.addTab("Danh gia cua hoc sinh", null, RatePanel, null);
		RatePanel.setLayout(null);
		
		String[] column_rate = {"STT", "Danh gia"};
		model_rate=new DefaultTableModel();
		model_rate.setColumnIdentifiers(column_rate);
		
		scrollPane_rate = new JScrollPane();
		scrollPane_rate.setBounds(10, 10, 729, 395);
		RatePanel.add(scrollPane_rate);
		TableRate = new JTable();
		TableRate.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		Object data_rate[]= {"1","Co day chan, hoc thi it tiep thu con khong thi cha co gi de quan tam"};
		model_rate.addRow(data_rate);
		TableRate.setModel(model_rate);


		
		TableColumnModel clmnModelRate = TableRate.getColumnModel();
		clmnModelRate.getColumn(0).setPreferredWidth(30);
		clmnModelRate.getColumn(1).setMinWidth(500);

		
		scrollPane_rate.setViewportView(TableRate);
	}
}
