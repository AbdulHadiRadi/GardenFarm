package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Unpaid {
    private String garden;
    private double amount;

    public Unpaid(String garden, double amount){

        this.garden = garden;
        this.amount = amount;

    }

    public Unpaid(double amount){

        this.amount = amount;

    }


    public String getGarden() {
        return garden;
    }

    public double getAmount() {
        return amount;
    }


    public void unpaidloan(){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM Unpaid WHERE garden = '" + garden+ "'");
            double total = rs.getDouble("amount")+amount;
            rs.close();
            st.executeUpdate("UPDATE 'Unpaid' SET amount = '"+total+"' WHERE garden = '"+garden+"'");
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


    public void unpaidLoanUpdate(){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{

            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM Income WHERE garden = 'LOANPAID'");
            double total = rs.getDouble("income")+amount;
            rs.close();
            st.executeUpdate("UPDATE 'Income' SET income = '"+total+"' WHERE garden = 'LOANPAID'");
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
