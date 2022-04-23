package com.example.qldiemso.string;

import java.security.AlgorithmConstraints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.qldiemso.model.HocSinh;
import com.example.qldiemso.model.PhucKhao;
import com.example.qldiemso.model.BangDiem;
import com.example.qldiemso.model.DanhGia;
import com.example.qldiemso.model.GiaoVien;

public class HocSinhDtb {
	private String connectionUrl;
	

	static public void main(String[] args) throws ClassNotFoundException, SQLException {
		HocSinhDtb h = new HocSinhDtb();
	}
	
	public HocSinhDtb() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			this.connectionUrl = ConfigUserSetting.getConnectionUrl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public HocSinh getStudentInfor(int maHS) {
		String query = String.format("SELECT * FROM HocSinh WHERE maHS = %s",maHS);
		System.out.println(query);
		Connection conn;
		try {
			conn = DriverManager.getConnection(connectionUrl);
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
	
	public HocSinh getHocSinh(int id) throws SQLException {
		String query = String.format("SELECT * FROM HocSinh WHERE MaHS = %s",id);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		HocSinh hSinh = new HocSinh(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
		return hSinh;
	}
	
	public GiaoVien getGiaoVien(int id) throws SQLException {
		String query = String.format("SELECT * FROM GiaoVien WHERE MaGV = %s",id);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		GiaoVien gv = new GiaoVien(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
		return gv;
	}
	
	public ArrayList<Integer> getSubjectList() throws SQLException{
		ArrayList<Integer> subjectStrings= new ArrayList<>();
		String query = "SELECT * FROM MonHoc";

		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next())
			subjectStrings.add(rs.getInt(1));
		return subjectStrings;
		
	}
	
	public ResultSet listSubject() throws SQLException {
		String query = "SELECT * FROM MonHoc";
		
				Connection conn = DriverManager.getConnection(connectionUrl);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				return rs;
	}
	
	public String getSubjectName(int id) throws SQLException{
		String sname = "";
		String query = String.format("SELECT * FROM MonHoc WHERE MaMH = %s",id);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		sname = Normalizer.normalize(rs.getString(2),Form.NFD);
		return sname;
	}
	
	public int getSubjectID(String name) throws SQLException{
		int sid;
		String query = String.format("SELECT * FROM MonHoc WHERE TenMH = %s",name);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		sid = rs.getInt(1);
		return sid;
	}
	
	public ResultSet getPointInfor(int id) throws SQLException {
		String query = String.format("SELECT * FROM BangDiemMonHoc bd JOIN HocSinh hs ON bd.HocSinh=hs.MaHS and bd.HocSinh = %s"
				+ " JOIN MonHoc mh ON mh.MaMH = bd.MonHoc",id);

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


	public void sendReview(int maHS , int subject, String text) throws SQLException { 
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		String query_check = String.format("SELECT * FROM HocSinh hs \n"
				+ "	LEFT JOIN LopHoc lh ON hs.LopHoc = lh.MaLop AND hs.MaHS=%s \n"
				+ "	LEFT JOIN GiaoVien_LopHoc gl ON gl.MaLop = lh.MaLop \n"
				+ "	JOIN GiaoVien gv ON gv.MaGV = gl.MaGV \n"
				+ "	JOIN MonHoc mh ON gv.MonGiangDay = mh.MaMH \n"
				+ "	AND mh.MaMH= %s", maHS, subject) ;
		System.out.println(query_check);
		ResultSet rs = st.executeQuery(query_check);
		rs.next();
		int maGV = rs.getInt("MaGV");
		String query = String.format("INSERT INTO PhucKhao VALUES (%s, %s, N'%s', 0, NULL)",maHS, maGV, text);
		System.out.println(query);

		st.executeUpdate(query);
	}
	
	public ResultSet getReview(int id) throws SQLException {
		String query = String.format("SELECT * FROM PhucKhao pk JOIN GiaoVien gv ON pk.GiaoVien = gv.MaGV"
				+ "  JOIN MonHoc mh ON mh.MaMH = gv.MonGiangDay and pk.HocSinh= %s ",id);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	} 
	
	

	
	public ArrayList<DanhGia> getRate(int id) throws SQLException{
		ArrayList<DanhGia> dgList = new ArrayList<>();
		String query = String.format("SELECT * FROM DanhGiaGiaoVien WHERE HocSinh= %s",id);
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			int idDG = rs.getInt(1);
			int idHS = rs.getInt(2);
			int idGV = rs.getInt(3);
			String note = rs.getString(4);
			dgList.add(new DanhGia(idDG, idHS, idGV, note));
		}
		return dgList;
	}
	
	 public List<PhucKhao> getReviewOf(int id) {
	        List<PhucKhao> listReviews = new ArrayList<PhucKhao>();

	        Connection conn = null;
	        Statement statement = null;
	        ResultSet resultSet = null;

	        try{
	            conn = DriverManager.getConnection(connectionUrl);
	            String SQL = String.format("SELECT * FROM PhucKhao WHERE HocSinh = %s", id);
	            statement = conn.createStatement();
	            resultSet = statement.executeQuery(SQL);

	            while (resultSet.next()) {
	            	
	                int rid = Integer.parseInt(resultSet.getString(1));
	                int teacherId = Integer.parseInt(resultSet.getString(3));
	                String content = resultSet.getString(4);
	                int status = Integer.parseInt(resultSet.getString(5));
	                String reason = resultSet.getString(6);

	                listReviews.add(new PhucKhao(rid, id, teacherId, content, status, reason));
	            }

	        } catch (Exception ignored){

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

	        return listReviews;
	    }
	
	public void sendRate(int maHS , int subject, String text) throws SQLException {

		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		String query_check = String.format("SELECT * FROM HocSinh hs JOIN LopHoc lh ON hs.LopHoc = lh.MaLop JOIN GiaoVien_LopHoc gl "
				+ "ON gl.MaLop = lh.MaLop JOIN GiaoVien gv ON gv.MaGV = gl.MaGV AND gv.MonGiangDay = %s AND hs.MaHS = %s",subject,maHS);
		ResultSet rs = st.executeQuery(query_check);
		rs.next();
		int maGV = rs.getInt("MaGV");
		String query = String.format("INSERT INTO DanhGiaGiaoVien VALUES (%s, %s, N'%s')",maHS, maGV, text);
		st.executeUpdate(query);
	}
	
	public boolean checkRate(int maHS, int subject) throws SQLException{
		Connection conn = DriverManager.getConnection(connectionUrl);
		Statement st = conn.createStatement();
		String query_check = String.format("SELECT * FROM HocSinh hs JOIN LopHoc lh ON hs.LopHoc = lh.MaLop JOIN GiaoVien_LopHoc gl "
				+ "ON gl.MaLop = lh.MaLop JOIN GiaoVien gv ON gv.MaGV = gl.MaGV AND gv.MonGiangDay = %s AND hs.MaHS = %s",subject,maHS);
		ResultSet rs = st.executeQuery(query_check);
		rs.next();
		int maGV = rs.getInt("MaGV");
		String query_main = String.format("SELECT * FROM DanhGiaGiaoVien WHERE HocSinh = %s AND GiaoVien = %s", maHS, maGV);
		System.out.println(query_main);
		rs = st.executeQuery(query_main);
		if(rs.next())
			return true;
		else 
			return false;
		
	}
	
	public boolean checkAccount(int id, String pass) {
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("SELECT * FROM TaiKhoan WHERE MaSoTK = %s AND MatKhau = '%s'", id, pass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL);
			if (resultSet.next()) 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateAccount(int id, String pass) {
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = DriverManager.getConnection(connectionUrl);
			String SQL = String.format("UPDATE TaiKhoan SET MatKhau = '%s' WHERE MaSoTK = %s;",pass, id);
            statement = conn.createStatement();
            int s = statement.executeUpdate(SQL);
            if (s > 0) {
                System.out.println("Successfully update");
            } else {
                System.out.println("Faild to update");
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
        };
	}
}
