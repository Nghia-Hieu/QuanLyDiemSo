package com.example.qldiemso.model;

import java.util.ArrayList;
import java.util.List;

public class GiaoVien {
    private int _id;
    private String _fullName;
    private int _subjectTeaching;
    private int _accountId;
    private List<Integer> _listClass;

    public GiaoVien(){
        this._id = 0;
        this._fullName = null;
        this._subjectTeaching = 0;
        this._accountId = 0;
        this._listClass = new ArrayList<>();
    }

    public GiaoVien(int id, String fullName, int subjectTeaching, int accountId, List<Integer> listClass) {
        this._id = id;
        this._fullName = fullName;
        this._subjectTeaching = subjectTeaching;
        this._accountId = accountId;
        this._listClass = listClass;
    }
    
    public GiaoVien(int id, String fullName, int subjectTeaching, int accountId) {
        this._id = id;
        this._fullName = fullName;
        this._subjectTeaching = subjectTeaching;
        this._accountId = accountId;
        this._listClass = null;
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

    public int get_subjectTeaching() {
        return _subjectTeaching;
    }

    public void set_subjectTeaching(int _subjectTeaching) {
        this._subjectTeaching = _subjectTeaching;
    }

    public int get_accountId() {
        return _accountId;
    }

    public void set_accountId(int _accountId) {
        this._accountId = _accountId;
    }

    public List<Integer> get_listClass() {
        return _listClass;
    }

    public void set_listClass(List<Integer> _listClass) {
        this._listClass = _listClass;
    }
}
