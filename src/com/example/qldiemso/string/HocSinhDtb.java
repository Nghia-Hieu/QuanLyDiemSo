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
	private String connectionUrl;

	static public void main(String[] args) throws ClassNotFoundException, SQLException {
		HocSinhDtb h = new HocSinhDtb();
		//h.updateMarkOfSubject(1,1,9,9,9,9, "hello world");
	}
	
	public HocSinhDtb() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			this.connectionUrl = ConfigUserSetting.getConnectionUrl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public ResultSet getPoint(int id) throws SQLException {
		String query = "SELECT * FROM BangDiemMonHoc WHERE HocSinh='"+id+"'";
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	public HocSinh getStudent(int studentId) {
		HocSinh hs = new HocSinh();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try{
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("select * from HocSinh WHERE MaHS = %s", studentId);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				int id = Integer.parseInt(resultSet.getString(1));
				String fullName = resultSet.getString(2);
				int sex = Integer.parseInt(resultSet.getString(3));
				int age = Integer.parseInt(resultSet.getString(4));
				int classId = Integer.parseInt(resultSet.getString(5));
				int accountId = Integer.parseInt(resultSet.getString(6));
				List<BangDiem> listMark = getAllMarksOf(id);

				hs = new HocSinh(id, fullName, sex, age, classId, accountId, listMark);
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

		return hs;
	}

	public String getClassName(int studentId) {
		String className = "";

		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try{
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("SELECT * FROM LopHoc JOIN HocSinh ON" +
					"(LopHoc.MaLop = HocSinh.LopHoc) WHERE MaHS = '%s'", studentId);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				className = resultSet.getString(2);
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}

		return className;
	}

	public List<HocSinh> getAllStudentOfClass(int classId) {
		List<HocSinh> listStudent= new ArrayList<HocSinh>();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try{
			conn = DriverManager.getConnection(connectionUrl);
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
			conn = DriverManager.getConnection(connectionUrl);
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

	public void updateMarkOfSubject(int studentId, int subjectId, BangDiem bd) {
		Connection conn = null;
		Statement statement = null;
		try{
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("UPDATE BangDiemMonHoc SET KiemTra15Phut = %s, KiemTra1Tiet = %s, " +
					"ThiGiuaKi = %s, ThiCuoiKi = %s, GhiChu = '%s' WHERE HocSinh = %s AND MonHoc = %s",bd.get_test15minutes(),
					bd.get_test1period(), bd.get_middleSemester(), bd.get_finalSemester(), bd.getNotes(), studentId, subjectId);
			statement = conn.createStatement();
			statement.executeUpdate(SQL);
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
		}
	}

	public void sendReview(int maHS , String subject, String text) throws SQLException { 
		
		Connection conn = DriverManager.getConnection(connectionUrl);
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
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	} 
	
	public ResultSet getRate(int id) throws SQLException {
		String query = "SELECT * FROM DanhGiaGiaoVien WHERE HocSinh='"+id+"'";
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	} 
	
	public void sendRate(int maHS , String subject, String text) throws SQLException {

		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		String query_check = "SELECT * FROM HocSinh LEFT JOIN LopHoc WHERE LopHoc = MaLop LEFT JOIN GIAOVIEN_LOPHOC "
				+ "WHERE GiaoVien_LopHoc.MaLop = LopHoc.MaLop LEFT JOIN GiaoVien WHERE GiaoVien.MaGV = GiaoVien_LopHoc.MaGV AND MonGiangDay = " + subject;
		ResultSet rs = st.executeQuery(query_check);
		int maGV = rs.getInt("MaGV");
		String query = "INSERT INTO DanhGiaGiaoVien VALUES (" + maHS + "','" + maGV + "','" + text + "')";
		st.executeUpdate(query);
	}
}
