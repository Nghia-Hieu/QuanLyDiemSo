package com.example.qldiemso.string;

import com.example.qldiemso.model.DanhGia;
import com.example.qldiemso.model.GiaoVien;
import com.example.qldiemso.model.BangDiem;
import com.example.qldiemso.model.PhucKhao;

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
    private String connectionUrl;

    static public void main(String[] args) throws ClassNotFoundException, SQLException {
        new GiaoVienDtb().updateReview(2, "OK em");
    }

    public GiaoVienDtb() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            connectionUrl = ConfigUserSetting.getConnectionUrl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public GiaoVien getTeacher(String usernameOfAccount) {
        GiaoVien teacher = new GiaoVien();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection(connectionUrl);
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

    public List<String> getClassesNameOf(int teacherId) {
        List<String> listClassName = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection(connectionUrl);
            String SQL = String.format("SELECT * FROM LopHoc JOIN GiaoVien_LopHoc ON" +
                    "(LopHoc.MaLop = GiaoVien_LopHoc.MaLop) WHERE MaGV = '%s'", teacherId);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                String className = resultSet.getString(2);

                listClassName.add(className);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return listClassName;
    }

    public List<BangDiem> getAllMarksOfClass(int classId, int subjectId) {
        List<BangDiem> listMarks = new ArrayList<BangDiem>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection(connectionUrl);
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
            conn = DriverManager.getConnection(connectionUrl);
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
            conn = DriverManager.getConnection(connectionUrl);

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

    public List<PhucKhao> getReviewOf(int teacherId) {
        List<PhucKhao> listReviews = new ArrayList<PhucKhao>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection(connectionUrl);
            String SQL = String.format("SELECT * FROM PhucKhao WHERE GiaoVien = %s", teacherId);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString(1));
                int studentId = Integer.parseInt(resultSet.getString(2));
                String content = resultSet.getString(4);
                int status = Integer.parseInt(resultSet.getString(5));
                String reason = resultSet.getString(6);

                listReviews.add(new PhucKhao(id, studentId, teacherId, content, status, reason));
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

    public void updateReview(int reviewId, String reason) {
        Connection conn = null;
        Statement statement = null;
        try{
            conn = DriverManager.getConnection(connectionUrl);
            String SQL = String.format("UPDATE PhucKhao SET TrangThai = 1, LyDo = N'%s' WHERE MaPK = %s;",
                    reason, reviewId);
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
        }
    }

    public List<DanhGia> getAssessmentOf(int teacherId) {
        List<DanhGia> listAssessment = new ArrayList<DanhGia>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            conn = DriverManager.getConnection(connectionUrl);
            String SQL = String.format("SELECT * FROM DanhGiaGiaoVien WHERE GiaoVien = %s", teacherId);
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString(1));
                int studentId = Integer.parseInt(resultSet.getString(2));
                String content = resultSet.getString(4);

                listAssessment.add(new DanhGia(id, studentId, teacherId, content));
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

        return listAssessment;
    }
}
