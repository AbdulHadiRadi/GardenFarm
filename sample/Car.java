package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by abdul on 6/17/2017.
 */
public class Car {


    private int number;
    private double income;
    private double cost;


    public Car(int number, double cost) {
        this.number = number;
        this.cost = cost;
    }

    public Car(int number) {

        this.number = number;
        this.income = 600;

    }


    public void costOfCar(){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM car WHERE number = '" + number + "'");
            double total = rs.getDouble("cost")+cost;
            rs.close();
            st.executeUpdate("UPDATE 'car' SET cost = '"+total+"' WHERE number = '" + number + "'");
            st.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (db.getConnection() != null) {
                try {
                    db.getConnection().close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }


    public void incomeFromCar(){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM car WHERE number = '" + number + "'");
            double total = rs.getDouble("income")+600;
            rs.close();
            st.executeUpdate("UPDATE 'car' SET income = '"+total+"' WHERE number = '" + number + "'");
            st.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (db.getConnection() != null) {
                try {
                    db.getConnection().close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

    }
}
