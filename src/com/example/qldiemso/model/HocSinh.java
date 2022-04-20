package com.example.qldiemso.model;

import java.util.ArrayList;
import java.util.List;

public class HocSinh {
    private int _id;
    private String _fullName;
    private int _sex;
    private int _age;
    private int _classId;
    private int _accountId;
    private List<BangDiem> _markTable;

    public HocSinh(){
        this._id = 0;
        this._fullName = null;
        this._sex = 0;
        this._age = 0;
        this._classId = 0;
        this._accountId = 0;
        _markTable = new ArrayList<>();
    }

    public HocSinh(int id, String fullName, int sex, int age, int classId, int accountId, List<BangDiem> listMark) {
        this._id = id;
        this._fullName = fullName;
        this._sex = sex;
        this._age = age;
        this._classId = classId;
        this._accountId = accountId;
        this._markTable = listMark;
    }

//    public HocSinh(HocSinh hs_obj){
//        this._id = hs_obj.get_id();
//        this._fullName = hs_obj.get_fullName();
//        this._test15minutes = hs_obj.get_test15minutes();
//        this._test1period = hs_obj.get_test1period();
//        this._middleSemester = hs_obj.get_middleSemester();
//        this._finalSemester = hs_obj.get_finalSemester();
//        this.notes = null;
//    }
    
    public HocSinh(int id, String fullName, int sex, int age, int classId, int accountId) {
        this._id = id;
        this._fullName = fullName;
        this._sex = sex;
        this._age = age;
        this._classId = classId;
        this._accountId = accountId;
    }

    public List<BangDiem> get_markTable() {
        return _markTable;
    }

    public void set_markTable(List<BangDiem> _markTable) {
        this._markTable = _markTable;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_fullName() {
        return _fullName;
    }

    public void set_fullName(String _fullName) {
        this._fullName = _fullName;
    }

    public int get_sex() {
        return _sex;
    }

    public void set_sex(int _sex) {
        this._sex = _sex;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public int get_classId() {
        return _classId;
    }

    public void set_classId(int _classId) {
        this._classId = _classId;
    }

    public int get_accountId() {
        return _accountId;
    }

    public void set_accountId(int _accountId) {
        this._accountId = _accountId;
    }
}
