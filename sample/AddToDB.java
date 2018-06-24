package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by abdul on 7/2/2017.
 */
public class AddToDB {

    private Tib tib;
    private Garden garden;
    private Loanee loanee;
    private String name;
    private double amount;



    public AddToDB(String name, double amount) {

        this.name = name;
        this.amount = amount;

    }


    public AddToDB(Tib tib){

        this.tib = tib;
    }


    public AddToDB(Garden garden) {

        this.garden = garden;

    }


    public AddToDB(Loanee loanee){

        this.loanee = loanee;

    }



    public void getTibAdded(){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.setQueryTimeout(10);
            statement.executeUpdate("insert into `Tib` (garden, date, car, element, amount, price, payment) values('"+tib.getDate()+"','"+tib.getGarden()+"','"+tib.getCar()+"','"+tib.getElement()+"','"+tib.getAmount()+"','"+tib.getPrice()+"','"+tib.getPayment()+"')");//Insert Querry
            statement.close();
            conn.close();
        } catch (SQLException ex) {
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

    public void gardenIncomeAdded() throws Exception {
        //Getting Connection
        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM Income WHERE garden = '" + name+ "'");
            double total = rs.getDouble("income")+amount;
            rs.close();
            st.executeUpdate("UPDATE 'Income' SET income = '"+total+"' WHERE garden = '"+garden+"'");
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

    public void getLoaneeAdded() throws Exception{
        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            st.executeUpdate("insert into 'Loanee' (name, amount, address, mobile) values('"+loanee.getName()+"','"+loanee.getAmount()+"','"+loanee.getAddress()+"','"+loanee.getPhone()+"')");
            st.close();
            conn.close();
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

