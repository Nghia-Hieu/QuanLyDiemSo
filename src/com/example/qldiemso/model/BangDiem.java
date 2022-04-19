package com.example.qldiemso.model;

public class BangDiem {
    private int _id;
    private float _test15minutes;
    private float _test1period;
    private float _middleSemester;
    private float _finalSemester;
    private String notes;
    private int _studentId;
    private int _subjectId;

    public BangDiem(){
        this._id = 0;
        this._studentId = 0;
        this._subjectId = 0;
        this._test15minutes = 0.0F;
        this._test1period = 0.0F;
        this._middleSemester = 0.0F;
        this._finalSemester = 0.0F;
        this.notes = null;
    }

    public BangDiem(int markTableId, int studentId, int subjectId, float _test15minutes, float _test1period, float _middleSemester, float _finalSemester, String notes) {
        this._id = markTableId;
        this._studentId = studentId;
        this._subjectId = subjectId;
        this._test15minutes = _test15minutes;
        this._test1period = _test1period;
        this._middleSemester = _middleSemester;
        this._finalSemester = _finalSemester;
        this.notes = notes;
    }
//
//    public MarkTable(MarkTable hs_obj){
//        this._id = hs_obj.get_id();
//        this._fullName = hs_obj.get_fullName();
//        this._test15minutes = hs_obj.get_test15minutes();
//        this._test1period = hs_obj.get_test1period();
//        this._middleSemester = hs_obj.get_middleSemester();
//        this._finalSemester = hs_obj.get_finalSemester();
//        this.notes = null;
//    }


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

    public int get_subjectId() {
        return _subjectId;
    }

    public void set_subjectId(int _subjectId) {
        this._subjectId = _subjectId;
    }

    public float get_test15minutes() {
        return _test15minutes;
    }

    public void set_test15minutes(float _test15minutes) {
        this._test15minutes = _test15minutes;
    }

    public float get_test1period() {
        return _test1period;
    }

    public void set_test1period(float _test1period) {
        this._test1period = _test1period;
    }

    public float get_middleSemester() {
        return _middleSemester;
    }

    public void set_middleSemester(float _middleSemester) {
        this._middleSemester = _middleSemester;
    }

    public float get_finalSemester() {
        return _finalSemester;
    }

    public void set_finalSemester(float _finalSemester) {
        this._finalSemester = _finalSemester;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
