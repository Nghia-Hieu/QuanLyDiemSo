package com.example.qldiemso.string;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.qldiemso.model.HocSinh;
import com.example.qldiemso.model.BangDiem;

public class HocSinhDtb {
	private String dbURL;
	private String username;
	private String password;


	static public void main(String[] args) throws ClassNotFoundException, SQLException {
		HocSinhDtb h = new HocSinhDtb();
		List<BangDiem>  hs = h.getAllMarksOf(1);
		System.out.println(hs.size());
	}
	
	public HocSinhDtb() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.dbURL = ConfigUserSetting.connectionUrl;
			this.username = ConfigUserSetting.username;
			this.password = ConfigUserSetting.password;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void setConfigDatabase(String username, String pass){
		username = username;
		password = pass;
	}
	
	public HocSinh getStudentInfor(int maHS) {
		String query = String.format("SELECT * FROM HocSinh WHERE maHS = %s",maHS);
		System.out.println(query);
		Connection conn;
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				//rs.next();
				return new HocSinh(maHS, rs.getString("HoTen"), rs.getInt("GioiTinh"), rs.getInt("Tuoi"), rs.getInt("LopHoc"), rs.getInt("MaSoTK"));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet getPointInfor(int id) throws SQLException {
		String query = String.format("SELECT * FROM BangDiemMonHoc bd JOIN HocSinh hs ON bd.HocSinh=hs.MaHS and bd.HocSinh = %s"
				+ " JOIN MonHoc mh ON mh.MaMH = bd.MonHoc",id);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	public List<HocSinh> getAllStudentOfClass(int classId) {
		List<HocSinh> listStudent= new ArrayList<HocSinh>();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try{
			conn = DriverManager.getConnection(dbURL, username, password);
			String SQL = String.format("select * from HocSinh WHERE LopHoc = %s", classId);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString(1));
				String fullName = resultSet.getString(2);
				int sex = Integer.parseInt(resultSet.getString(3));
				int age = Integer.parseInt(resultSet.getString(4));
				int accountId = Integer.parseInt(resultSet.getString(6));
				List<BangDiem> listMark = getAllMarksOf(id);

				listStudent.add(new HocSinh(id, fullName, sex, age, classId, accountId, listMark));
			}

		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			if(statement != null){
				try{
					conn.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(resultSet != null){
				try{
					conn.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return listStudent;
	}

	public List<BangDiem> getAllMarksOf(int studentId) {
		List<BangDiem> listMarks = new ArrayList<BangDiem>();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try{
			conn = DriverManager.getConnection(dbURL, username, password);
			String SQL = String.format("select * from BangDiemMonHoc WHERE HocSinh = %s", studentId);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				int markTableId = Integer.parseInt(resultSet.getString(1));
				float test15minutes = Float.parseFloat(resultSet.getString(2));
				float test1period = Float.parseFloat(resultSet.getString(3));
				float middleSeme = Float.parseFloat(resultSet.getString(4));
				float finalSeme = Float.parseFloat(resultSet.getString(5));
				String notes = resultSet.getString(6);
				int subjectId = Integer.parseInt(resultSet.getString(8));

				listMarks.add(new BangDiem(markTableId, studentId, subjectId, test15minutes, test1period, middleSeme, finalSeme, notes));
			}

		} catch (Exception ex){
			ex.printStackTrace();
		} finally {
			if(statement != null){
				try{
					conn.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(resultSet != null){
				try{
					conn.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return listMarks;
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
				+ "WHERE GiaoVien_LopHoc.MaLop = LopHoc.MaLop LEFT JOIN GiaoVien WHERE GiaoVien.MaGV = GiaoVien_LopHoc.MaGV AND MonGiangDay = " + subject;
		ResultSet rs = st.executeQuery(query_check);
		int maGV = rs.getInt("MaGV");
		String query = "INSERT INTO DanhGiaGiaoVien VALUES (" + maHS + "','" + maGV + "','" + text + "')";
		st.executeUpdate(query);
	}
}
