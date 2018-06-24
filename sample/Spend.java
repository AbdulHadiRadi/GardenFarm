package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Spend{

    private String source;
    private String reason;
    private double amount;


    public Spend(String source, String reason, double amount) {
        this.source = source;
        this.reason = reason;
        this.amount = amount;
    }


    public void spendingToDB (){

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            st.executeUpdate("INSERT INTO 'Spending' (date, source, reason, amount) VALUES ('"+ LocalDate.now()+"','" + source + "','"+reason+"','"+amount+"')");
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

    public void addCostToDB(){
        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM Cost WHERE source = '" + source + "'");
            double total = rs.getDouble("amount") + amount;
            rs.close();
            st.executeUpdate("UPDATE 'Cost' SET  amount ='"+total+"' WHERE  source = '" + source + "'");
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
