package sample;

/**
 * Created by abdul hadi on 2/20/2017.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;



public class DBConnector {

    private static final DBConnector INSTANCE = new DBConnector();

    private Connection connection = null;

    private DBConnector () {

        try {
            Driver driver1 = new org.sqlite.JDBC();
            DriverManager.registerDriver(driver1);
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\abdul\\Garden\\src\\sample\\baghan.db");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
    }



    public static DBConnector getInstance() {

        return INSTANCE;
    }

    public Connection getConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\abdul\\Garden\\src\\sample\\baghan.db");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Database connection failed!", ex);
        }
        return this.connection;
    }
}



