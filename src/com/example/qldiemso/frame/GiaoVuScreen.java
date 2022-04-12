package com.example.qldiemso.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GiaoVuScreen extends JFrame {

	private JFrame frame;
	private String maGV;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel ClassPanel, AccountPanel, ExportPanel;
	private JTable TableClass, TableAccount, TableExport;
	private JScrollPane scrollPane_class, scrollPane_account, scrollPane_export;
	private DefaultTableModel model_class, model_account, model_export;
	private JButton successReviewBtn;
	private JButton declineReviewBtn;
	private JTextField idText;
	private JTextField nameText;
	private JTextField idGVtext;
	private JTextField passGVtext;
	private JButton exportBtn;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public GiaoVuScreen() {
		initialize();
	}

	public static void main(String[] args) {
		GiaoVuScreen window = new GiaoVuScreen();
		window.setVisible(true);
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

		// --------------------------Panel 1-------------------------------
		ClassPanel = new JPanel();
		tabbedPane.addTab("Quan li diem so", null, ClassPanel, null);
		ClassPanel.setLayout(null);

		String[] className = { "10A5", "11A3", "12CL", "11A5" };

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

		String[] column_class = { "MaHS", "Ho va Ten", "Lop", "15'", "1 tiet", "GiuaHK", "CuoiHK" };
		model_class = new DefaultTableModel();
		model_class.setColumnIdentifiers(column_class);

		scrollPane_class = new JScrollPane();
		scrollPane_class.setBounds(292, 54, 445, 339);
		ClassPanel.add(scrollPane_class);
		TableClass = new JTable();
		TableClass.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		Object data[] = { "1", "Nguyen Van A", "10A5", 10, 10, 10, 10 };
		model_class.addRow(data);

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

		JButton RefreshClassBtn = new JButton("L\u00E0m m\u1EDBi");
		RefreshClassBtn.setBounds(569, 10, 85, 21);
		ClassPanel.add(RefreshClassBtn);

		JButton updateListBtn = new JButton("C\u1EADp nh\u1EADt");
		updateListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		updateListBtn.setBounds(530, 396, 85, 21);
		ClassPanel.add(updateListBtn);

		JLabel idLabel = new JLabel("M\u00E3 h\u1ECDc sinh");
		idLabel.setBounds(28, 109, 69, 13);
		ClassPanel.add(idLabel);

		idText = new JTextField();
		idText.setBounds(132, 106, 135, 19);
		ClassPanel.add(idText);
		idText.setColumns(10);

		JLabel nameLabel = new JLabel("T\u00EAn h\u1ECDc sinh");
		nameLabel.setBounds(28, 166, 69, 13);
		ClassPanel.add(nameLabel);

		nameText = new JTextField();
		nameText.setColumns(10);
		nameText.setBounds(132, 163, 135, 19);
		ClassPanel.add(nameText);

		JButton searchBtn = new JButton("T\u00ECm ki\u1EBFm");
		searchBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		searchBtn.setBounds(28, 229, 85, 21);
		ClassPanel.add(searchBtn);

		JButton checkAccountBtn = new JButton("T\u00E0i kho\u1EA3n");
		checkAccountBtn.setHorizontalAlignment(SwingConstants.LEADING);
		checkAccountBtn.setBounds(161, 229, 85, 21);
		ClassPanel.add(checkAccountBtn);

		

		

		// --------------------------Panel 2-------------------------------

		AccountPanel = new JPanel();
		tabbedPane.addTab("Quan li tai khoan giao vien", null, AccountPanel, null);
		AccountPanel.setLayout(null);

		scrollPane_account = new JScrollPane();
		scrollPane_account.setBounds(20, 21, 358, 376);
		AccountPanel.add(scrollPane_account);
		TableAccount = new JTable();
		TableAccount.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		String[] column_account = { "MaGV", "Ten dang nhap", "Mat khau" };
		model_account = new DefaultTableModel();
		model_account.setColumnIdentifiers(column_account);

		TableAccount.setModel(model_account);
		TableColumnModel clmnModelAccount = TableAccount.getColumnModel();
		scrollPane_account.setViewportView(TableAccount);
		
		Object data_account[] = { "1222", "Nguyen Thi Cam Tu", "abcdef" };
		model_account.addRow(data_account);
		
		clmnModelAccount.getColumn(0).setPreferredWidth(50);
		clmnModelAccount.getColumn(1).setPreferredWidth(150);
		clmnModelAccount.getColumn(2).setPreferredWidth(50);

		idGVtext = new JTextField();
		idGVtext.setBounds(575, 71, 96, 19);
		AccountPanel.add(idGVtext);
		idGVtext.setColumns(10);

		passGVtext = new JTextField();
		passGVtext.setBounds(575, 151, 96, 19);
		AccountPanel.add(passGVtext);
		passGVtext.setColumns(10);

		JButton resetBtn = new JButton("Reset M\u1EADt kh\u1EA9u");
		resetBtn.setBounds(525, 243, 108, 21);
		AccountPanel.add(resetBtn);

		JLabel lblNewLabel = new JLabel("ID gi\u00E1o vi\u00EAn");
		lblNewLabel.setBounds(446, 74, 90, 13);
		AccountPanel.add(lblNewLabel);

		JLabel lblTnTiKhon = new JLabel("T\u00EAn t\u00E0i kho\u1EA3n");
		lblTnTiKhon.setBounds(446, 154, 90, 13);
		AccountPanel.add(lblTnTiKhon);

		// --------------------------Panel 3 -------------------------------

		ExportPanel = new JPanel();
		tabbedPane.addTab("Xuat bao cao", null, ExportPanel, null);
		ExportPanel.setLayout(null);

		String[] column_export = { "MaHS", "Ho va Ten", "Lop", "15'", "1 tiet", "GiuaHK", "CuoiHK" };
		model_export = new DefaultTableModel();
		model_export.setColumnIdentifiers(column_export);

		scrollPane_export = new JScrollPane();
		scrollPane_export.setBounds(10, 45, 729, 293);
		ExportPanel.add(scrollPane_export);
		TableExport = new JTable();
		TableExport.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		Object data_export[] = { "1", "Nguyen Van A", "10A5", 10, 10, 10, 10 };
		model_export.addRow(data_export);
		TableExport.setModel(model_export);

		TableColumnModel clmnModelExport = TableExport.getColumnModel();
		clmnModelExport.getColumn(1).setMinWidth(300);

		scrollPane_export.setViewportView(TableExport);

		exportBtn = new JButton("Xu\u1EA5t b\u00E1o c\u00E1o \u0111i\u1EC3m");
		exportBtn.setBounds(310, 375, 152, 21);
		ExportPanel.add(exportBtn);
	}
}
