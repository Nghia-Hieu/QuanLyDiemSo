package com.example.qldiemso.string;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.qldiemso.string.UtilsString;

public class HocSinhDtb {
	public static String dbURL; 
	public static String username; 
	public static String password; 
	
	public HocSinhDtb() {
		UtilsString dtb = new UtilsString();
		//dbURL = dtb.dbURL+"; databaseName="+dtb.DATABASENAME;
		dbURL = dtb.dbURL;
		username = dtb.username;
		password = dtb.password;
	}
	
	public ResultSet getPoint(int id) throws SQLException {
		String query = "SELECT * FROM BangDiemMonHoc WHERE HocSinh='"+id+"'";
		System.out.println(query);
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	} 
	 
	
	public void sendReview(int maHS , String subject, String text) throws SQLException { 
		
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement st = conn.createStatement();
		String query_check = "SELECT * FROM HocSinh LEFT JOIN LopHoc WHERE LopHoc = MaLop LEFT JOIN GIAOVIEN_LOPHOC "
				+ "WHERE GiaoVien_LopHoc.MaLop = LopHoc.MaLop LEFT JOIN GiaoVien WHERE GiaoVien.MaGV = GiaoVien_LopHoc.MaGV AND MonGiangDay = "+subject;
		ResultSet rs = st.executeQuery(query_check);
		int maGV = rs.getInt("MaGV");
		String query = "INSERT INTO PhucKhao VALUES ("+maHS+ "','"+maGV+"','"+text+"')";
		st.executeUpdate(query);
	}
	
	public ResultSet getReview(int id) throws SQLException {
		String query = "SELECT * FROM PhucKhao WHERE HocSinh='"+id+"'";
		System.out.println(query);
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	} 
	
	public ResultSet getRate(int id) throws SQLException {
		String query = "SELECT * FROM DanhGiaGiaoVien WHERE HocSinh='"+id+"'";
		System.out.println(query);
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	} 
	
	public void sendRate(int maHS , String subject, String text) throws SQLException { 
		
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement st = conn.createStatement();
		String query_check = "SELECT * FROM HocSinh LEFT JOIN LopHoc WHERE LopHoc = MaLop LEFT JOIN GIAOVIEN_LOPHOC "
				+ "WHERE GiaoVien_LopHoc.MaLop = LopHoc.MaLop LEFT JOIN GiaoVien WHERE GiaoVien.MaGV = GiaoVien_LopHoc.MaGV AND MonGiangDay = "+subject;
		ResultSet rs = st.executeQuery(query_check);
		int maGV = rs.getInt("MaGV");
		String query = "INSERT INTO DanhGiaGiaoVien VALUES ("+maHS+ "','"+maGV+"','"+text+"')";
		st.executeUpdate(query);
		
}
