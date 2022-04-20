package com.example.qldiemso.model;

/**
 * com.example.qldiemso.model
 * Create by pmtoan
 * Date 4/21/2022 - 12:55 AM
 * Description: ...
 */
public class DanhGia {
    private int _id;
    private int _studentId;
    private int _teacherId;
    private String _content;

    public DanhGia(){
        this._id = 0;
        this._studentId = 0;
        this._teacherId = 0;
        this._content = "";
    }

    public DanhGia(int reviewId, int studentId, int teacherId, String content) {
        this._id = reviewId;
        this._studentId = studentId;
        this._teacherId = teacherId;
        this._content = content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_studentId() {
        return _studentId;
    }

    public void set_studentId(int _studentId) {
        this._studentId = _studentId;
    }

    public int get_teacherId() {
        return _teacherId;
    }

    public void set_teacherId(int _teacherId) {
        this._teacherId = _teacherId;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }
}
