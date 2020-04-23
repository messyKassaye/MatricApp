package com.students.preparation.matric.students.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class StudentsModel {

    public String _device_id;
    public String _activeStatus;
    public String _studentId;
    public String _fullName;
    public String _mobileNumber;
    public String _password;
    public String _txRefNum;
    public String _stream;
    public String _school;
    public String _bank;
    public String _role;

    public StudentsModel() {

    }


    public StudentsModel(String _studentId , String _fullName, String _mobileNumber, String _password, String _txRefNum, String _stream, String _school, String _bank , String _device_id , String _activeStatus,String role) {

        this._studentId = _studentId;
        this._fullName = _fullName;
        this._mobileNumber=_mobileNumber;
        this._password = _password;
        this._txRefNum = _txRefNum;
        this._stream = _stream;
        this._school = _school;
        this._bank = _bank;
        this._device_id = _device_id;
        this._activeStatus = _activeStatus;
        this._role = role;

    }


    public String get_deviceId() {
        return _device_id;
    }

    public void set_deviceId(String _device_id) {
        this._device_id = _device_id;
    }

    public String get_activeStatus() {
        return _activeStatus;
    }

    public void set_activeStatus(String _activeStatus) {
        this._activeStatus = _activeStatus;
    }

    public String get_studentId() {
        return _studentId;
    }

    public void set_studentId(String _studentId) {
        this._studentId = _studentId;
    }

    public String get_fullName() {
        return _fullName;
    }

    public void set_fullName(String _fullName) {
        this._fullName = _fullName;
    }

    public String get_mobileNumber() {
        return _mobileNumber;
    }

    public void set_mobileNumber(String _mobileNumber) {
        this._mobileNumber = _mobileNumber;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_txRefNum() {
        return _txRefNum;
    }

    public void set_txRefNum(String _txRefNum) {
        this._txRefNum = _txRefNum;
    }

    public String get_stream() {
        return _stream;
    }

    public void set_stream(String _stream) {
        this._stream = _stream;
    }

    public String get_school() {
        return _school;
    }

    public void set_school(String _school) {
        this._school = _school;
    }

    public String get_bank() {
        return _bank;
    }

    public void set_bank(String _bank) {
        this._bank = _bank;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("_studentId", _studentId);
        result.put("_fullName", _fullName);
        result.put("_mobileNumber", _mobileNumber);
        result.put("_password", _password);
        result.put("_txRefNum", _txRefNum);
        result.put("_stream", _stream);
        result.put("_school", _school);
        result.put("_bank", _bank);
        result.put("_device_id", _device_id);
        result.put("_activeStatus", _activeStatus);

        return result;
    }

    public String get_role() {
        return _role;
    }

    public void set_role(String _role) {
        this._role = _role;
    }
}
