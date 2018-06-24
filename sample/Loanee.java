package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by abdul on 6/16/2017.
 */
public class Loanee {
    private String name;
    private String address;
    private double amount;
    private int phone;


    public Loanee(String name, double amount, String address, int phone){
        this.name = name;
        this.amount = amount;
        this.address = address;
        this.phone = phone;
    }


    public Loanee(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }



    public Loanee(double amount) {
        this.amount = amount;
    }


    public String getName() {

        return name;

    }

    public double getAmount() {

        return amount;

    }

    public String getAddress() {

        return address;

    }

    public int getPhone() {

        return phone;

    }


    public void addingExistingLoanee(){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM Loanee WHERE name = '" + name+ "'");
            double total = rs.getDouble("amount")+amount;
            rs.close();
            st.executeUpdate("UPDATE 'Loanee' SET amount = '"+total+"' WHERE name = '"+name+"'");
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
