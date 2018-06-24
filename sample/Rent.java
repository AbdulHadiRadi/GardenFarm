package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Rent {

    private String place;
    private double money;


    public Rent(String place, double money) {
        this.place = place;
        this.money = money;
    }


    public void getRentAdded(){

            DBConnector db = DBConnector.getInstance();
            Connection conn = db.getConnection();
            Statement st;
            try{
                st = conn.createStatement();
                st.setQueryTimeout(10);
                ResultSet rs = st.executeQuery("SELECT * FROM Unpaid WHERE garden = '" + name+ "'");
                double total = rs.getDouble("amount") + amount;
                rs.close();
                st.executeUpdate("UPDATE 'Unpaid' SET amount = '"+total+"' WHERE garden = '"+name+"'");
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
