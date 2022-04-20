/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.qldiemso.string;

/**
 *
 * @author nhonnhon
 */
public class ConfigUserSetting {
    static public String databaseName = "ManageScore";
    static public String username = "sa";
    static public String password = "123";
    static public String getConnectionUrl(){
        return String.format("jdbc:sqlserver://localhost:1433;databaseName=%s;username=%s;password=%s;MultipleActiveResultSets=True;",
                databaseName, username, password);
    }
}

