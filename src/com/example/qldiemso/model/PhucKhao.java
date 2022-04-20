package com.example.qldiemso.model;

/**
 * com.example.qldiemso.model
 * Create by pmtoan
 * Date 4/20/2022 - 10:52 PM
 * Description: ...
 */
public class PhucKhao {
    private int _id;
    private int _studentId;
    private int _teacherId;
    private String _content;
    private int _status;
    private String _reason;

    public PhucKhao(){
        this._id = 0;
        this._studentId = 0;
        this._teacherId = 0;
        this._content = "";
        this._status = 0;
        this._reason = "";
    }

    public PhucKhao(int reviewId, int studentId, int teacherId, String content, int status, String reason) {
        this._id = reviewId;
        this._studentId = studentId;
        this._teacherId = teacherId;
        this._content = content;
        this._status = status;
        this._reason = reason;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public String get_reason() {
        return _reason;
    }

    public void set_reason(String _reason) {
        this._reason = _reason;
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
