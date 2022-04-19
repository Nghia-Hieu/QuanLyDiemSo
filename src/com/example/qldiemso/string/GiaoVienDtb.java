package com.example.qldiemso.string;

import com.example.qldiemso.model.GiaoVien;
import com.example.qldiemso.model.BangDiem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.example.qldiemso.string
 * Create by pmtoan
 * Date 4/19/2022 - 9:44 PM
 * Description: ...
 */
public class GiaoVienDtb {
    private String dbURL;
    private String username;
    private String password;

    static public void main(String[] args) throws ClassNotFoundException, SQLException {
        GiaoVienDtb db = new GiaoVienDtb();
        GiaoVien t = db.getTeacher("CaoMinhPhuc");
        System.out.println(t.get_subjectTeaching());
        List<BangDiem> m = db.getAllMarksOfClass(2, 2);
        for(int i=0 ;i < m.size();i++){
            System.out.println(m.get(i).get_id());
        }
    }

    public GiaoVienDtb() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            dbURL = ConfigUserSetting.connectionUrl;
            username = ConfigUserSetting.username;
            password = ConfigUserSetting.password;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setConfigDatabase(String username, String pass){
        username = username;
        password = pass;
    }

    public GiaoVien getTeacher(String usernameOfAccount) {
        GiaoVien teacher = new GiaoVien();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection(dbURL, username, password);
            String SQL = String.format("select * from GiaoVien join TaiKhoan on" +
                    "(GiaoVien.MaSoTK = TaiKhoan.MaSoTK) WHERE TenDangNhap = '%s'", usernameOfAccount);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString(1));
                String fullName = resultSet.getString(2);
                int subjectTeaching = Integer.parseInt(resultSet.getString(3));
                int accountId = Integer.parseInt(resultSet.getString(4));
                List<Integer> listClass = getClassOf(id);

                teacher = new GiaoVien(id, fullName, subjectTeaching, accountId, listClass);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return teacher;
    }

    public List<BangDiem> getAllMarksOfClass(int classId, int subjectId) {
        List<BangDiem> listMarks = new ArrayList<BangDiem>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            String SQL = String.format("SELECT * FROM BangDiemMonHoc JOIN HocSinh ON (BangDiemMonHoc.HocSinh = HocSinh.MaHS) " +
                        "WHERE  MonHoc = '%s' AND LopHoc = '%s'", classId, subjectId);

            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int markTableId = Integer.parseInt(resultSet.getString(1));
                float test15minutes = Float.parseFloat(resultSet.getString(2));
                float test1period = Float.parseFloat(resultSet.getString(3));
                float middleSeme = Float.parseFloat(resultSet.getString(4));
                float finalSeme = Float.parseFloat(resultSet.getString(5));
                String notes = resultSet.getString(6);
                int studentId = Integer.parseInt(resultSet.getString(7));

                listMarks.add(new BangDiem(markTableId, studentId, subjectId, test15minutes, test1period, middleSeme, finalSeme, notes));
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return listMarks;
    }

    public List<BangDiem> getAllMarksOf(int studentId) {
        List<BangDiem> listMarks = new ArrayList<BangDiem>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection(dbURL, username, password);
            String SQL = String.format("SELECT * FROM MonHoc ");
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

        return listMarks;
    }

    public List<Integer> getClassOf(int teacherId) {
        List<Integer> listClass = new ArrayList<Integer>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            conn = DriverManager.getConnection(dbURL, username, password);

            String SQL = String.format("SELECT * FROM GiaoVien_LopHoc WHERE MaGV = %s", teacherId);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString(2));

                listClass.add(id);
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

        return listClass;
    }
}
