package com.example.qldiemso.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.interfaces.RSAKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.NoInitialContextException;
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

import com.example.qldiemso.model.BangDiem;
import com.example.qldiemso.model.HocSinh;
import com.example.qldiemso.string.HocSinhDtb;

import javax.swing.JTextPane;
import javax.swing.JTextField;

public class HocSinhScreen extends JFrame{

	private JFrame frame;
	private int maHS = 1;
	private HocSinh hs;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel GradePanel, ReviewStatePanel, DoRatePanel, ManagePanel;
	private JTable TableGrade, TableReview, TableRate;
	private JScrollPane scrollPane_class, scrollPane_review, scrollPane_rate;
	private DefaultTableModel model_class, model_review, model_rate;
	private JLabel rateTextLabel;
	private JTextField idCPText;
	private JTextField passCPText;
	private JTextField newPassCPText;
	private JTextField confirmPassCPText;
	
	private HocSinhDtb dtb;

	
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
		super("HocSinh");
		initialize();
	}
	
	public HocSinhScreen(int maHS) {
		super("HocSinh " + maHS);
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
		
		dtb = new HocSinhDtb();
		hs = dtb.getStudentInfor(maHS);
				
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
		TableGrade = new JTable();
		TableGrade.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		List <BangDiem> scoreList = dtb.getAllMarksOf(maHS);
		
		
		refreshPointTable(model_class);
		
		
		TableGrade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		TableGrade.setModel(model_class);
		TableColumnModel clmnModel = TableGrade.getColumnModel();
		clmnModel.getColumn(0).setPreferredWidth(30);
		clmnModel.getColumn(1).setPreferredWidth(100);
		clmnModel.getColumn(2).setPreferredWidth(40);
		clmnModel.getColumn(3).setPreferredWidth(10);
		clmnModel.getColumn(4).setPreferredWidth(10);
		clmnModel.getColumn(5).setPreferredWidth(10);
		clmnModel.getColumn(6).setPreferredWidth(10);


		scrollPane_class.setViewportView(TableGrade);
		
		JButton refreshListBtn = new JButton("L\u00E0m m\u1EDBi");
		refreshListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshPointTable(model_class);
			}
		});
		refreshListBtn.setBounds(569, 10, 85, 21);
		GradePanel.add(refreshListBtn);
		
		JButton reviewBtn = new JButton("G\u1EEDi ph\u00FAc kh\u1EA3o");
		reviewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int grade_col = TableGrade.getSelectedColumn();
				int grade_row = TableGrade.getSelectedRow();
				
				String grade_review = gradeCol(grade_col);
				String subject_review = (String) TableGrade.getValueAt(2, grade_row);
				String grade_review_text = "Xem xet lai diem "+grade_review;
				try {
					dtb.sendReview(maHS, subject_review, grade_review_text);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		scrollPane_review.setBounds(20, 21, 717, 359);
		ReviewStatePanel.add(scrollPane_review);
		TableReview = new JTable();
		TableReview.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		
		Object data_review[]= {"Sinh","Nguyen Van A", "10A5", "Dang cho", "15' mon Sinh bi sai"};
		model_review.addRow(data_review);
		
//		try {
//		ResultSet rs;
//			rs = dtb.getPoint(maHS);
//			model_review.setRowCount(0);
//			while(rs.next()) {
//				int idHS = rs.getInt("maHS");
//				int idGV = rs.getInt("maGV");
//				String note = rs.getString("NoiDung");
//
//				Object editData[] = {idHS,idGV, note};
//				model_review.addRow(editData);
//			}
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Can't search Treatment Place");
//		}
		
		TableReview.setModel(model_review);
		TableColumnModel clmnModelReview = TableReview.getColumnModel();
		clmnModelReview.getColumn(0).setPreferredWidth(50);
		clmnModelReview.getColumn(1).setPreferredWidth(150);
		clmnModelReview.getColumn(2).setPreferredWidth(50);
		clmnModelReview.getColumn(3).setPreferredWidth(50);
		clmnModelReview.getColumn(4).setMinWidth(350);
		scrollPane_review.setViewportView(TableReview);
		
		
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
		
//		try {
//			ResultSet rs;
//			rs = dtb.getRate(maHS);
//			model_rate.setRowCount(0);
//			while(rs.next()) {
//				int idHS = rs.getInt("maHS");
//				int idGV = rs.getInt("maGV");
//				String note = rs.getString("NoiDung");
//
//				Object editData[] = {idHS,idGV, note};
//				model_rate.addRow(editData);
//			}
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Can't search Treatment Place");
//		}
//		
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
		
		JTextPane rateTextPane = new JTextPane();
		rateTextPane.setBounds(136, 340, 351, 77);
		DoRatePanel.add(rateTextPane);
		
		JButton sendBtn = new JButton("G\u1EEDi");
//		sendBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String rateText = rateTextPane.getText();
//				String subject = subjectsComboBox.getSelectedItem().toString();
//				try {
//					dtb.sendRate(maHS, subject, rateText);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
		sendBtn.setBounds(595, 289, 85, 77);
		DoRatePanel.add(sendBtn);
		
		
		//--------------------------Panel 3 -------------------------------

		ManagePanel = new JPanel();
		tabbedPane.addTab("Quan li tai khoan", null, ManagePanel, null);
		ManagePanel.setLayout(null);
		
		idCPText = new JTextField();
		idCPText.setBounds(363, 118, 123, 19);
		idCPText.setColumns(10);
		idCPText.setEditable(false);
		ManagePanel.add(idCPText);
		
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
	
	private void refreshPointTable( DefaultTableModel model) {
		ResultSet rs;
		try {
			rs = dtb.getPointInfor(maHS);
			model.setRowCount(0);
			while(rs.next()) {
				String subject = rs.getString("TenMH");
				String name = rs.getString("HoTen");
				float kt15 = rs.getFloat("KiemTra15Phut");
				float kt1 = rs.getFloat("KiemTra1Tiet");
				float ktg = rs.getFloat("ThiGiuaKi");
				float ktc = rs.getFloat("ThiCuoiKi");

				Object editData[] = {maHS, name, subject, kt15, kt1, ktg, ktc};
				model.addRow(editData);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Can't search Treatment Place");
		}
	}
	
	private String gradeCol(int col) {
		switch (col) {
		case 3: 
			return "KiemTra15Phut";
		case 4: 
			return "KiemTra1Tiet";
			
		case 5: 
			return "ThiGiuaKi";
			
		case 6: 
			return "ThiCuoiKi";
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + col);
		}
	}
}
