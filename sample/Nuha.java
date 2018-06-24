package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Nuha {

    private String reason;
    private double amount;

    public Nuha(String reason, double amount) {
        this.amount = amount;
        this.reason = reason;
    }
    public void costOfNuha(){
        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            st.executeUpdate("INSERT INTO 'Nuha' (date, reason, amount) VALUES ('"+ LocalDate.now()+"','" +reason+"','"+amount+"')");
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
