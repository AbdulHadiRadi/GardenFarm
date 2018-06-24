package sample;

import javafx.event.ActionEvent;

import java.awt.*;
import java.lang.String;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller implements Initializable{

    @FXML
    private AnchorPane addPane;

    @FXML
    private VBox box;

    @FXML
    private ComboBox<String> car;

    @FXML
    private ComboBox<String> garden;

    @FXML
    private ComboBox<String> element;

    @FXML
    private ComboBox<String> payment;

    @FXML
    private TextField amounts;

    @FXML
    private TextField loanAmount;

    @FXML
    private TextField prices;

    @FXML
    private TextField buyer;

    @FXML
    private TextField address;

    @FXML
    private TextField mobile;

    @FXML
    private ComboBox<String> loanees;

    @FXML
    private ComboBox<String> loaneeList;

    @FXML
    private TextField loanPay;

    @FXML
    private ComboBox<String> costSource;

    @FXML
    private Button submitButton;

    @FXML
    private Button loanPaidButton;

    @FXML
    private TextField reason;

    @FXML
    private TextField costAmount;

    @FXML
    private Button costSumbit;

    @FXML
    private ComboBox<String> carNumber;

    private boolean pay = true;

    @FXML
    private ComboBox<?> rentOrFees;

    @FXML
    private ComboBox<?> rentlist;

    @FXML
    private ComboBox<?> feeslist;

    @FXML
    private TextField amountFR;

    @FXML
    private ComboBox<String> month;

    @FXML
    private Button submitFR;


    @FXML
    void clearButtonAction(ActionEvent event) throws Exception{

        refresh(event);

    }



    @FXML
    void costSubmitAction(ActionEvent event) {

        addingSpendingToDB();
        addingCostToDB();

        if(costSource.getValue()!=null && costSource.getValue().equals("CAR / গাড়ী")) {

            if(carNumber.getValue().equals("NUHA")){
              Nuha nuha = new Nuha(reason.getText(), Double.parseDouble(costAmount.getText()));
              nuha.costOfNuha();

            }

            else {

                costFromCar(Integer.parseInt(carNumber.getValue()), Double.parseDouble(costAmount.getText()));

            }
        }

        costClearAction(event);

    }



    @FXML
    void addingSpendingToDB(){

        String [] sources = costSource.getValue().split(" ");
        String getSource = sources[0];
        Spend spend = new Spend(getSource,reason.getText().toString(),Double.parseDouble(costAmount.getText().toString()));
        spend.spendingToDB();

    }



    @FXML
    void addingCostToDB(){

        String [] sources = costSource.getValue().split(" ");
        String getSource = sources[0];
        Spend spend = new Spend(getSource,reason.getText().toString(),Double.parseDouble(costAmount.getText()));
        spend.addCostToDB();

    }



    @FXML
    void costClearAction(ActionEvent event) {
        costSource.getSelectionModel().clearSelection();
        reason.setText("");
        costAmount.setText("");
        carNumber.getSelectionModel().clearSelection();
        carNumber.setDisable(true);
    }

    @FXML
    void carNumberAction(){
        if(costSource.getValue()!=null && costSource.getValue().equals("CAR / গাড়ী")) {
            carNumber.setDisable(false);
        }
    }


    @FXML
    void submitButtonAction(ActionEvent event) throws Exception{

        String bagan = garden.getValue();
        int gari = Integer.parseInt(car.getValue());
        String upadan = element.getValue();
        double mal = Double.parseDouble( amounts.getText().trim());
        double income = Double.parseDouble( prices.getText().trim());

        String [] names = bagan.split(" ");
        String gardenName = names[0];

        if(payment.getValue().equals("UNPAID / বাকী")) {

            if (loanees.getValue().equals("NEW BUYER / নতুন ক্রেতা")) {
                String newBuyer = buyer.getText().trim();
                String residence = address.getText().trim();
                int phone = Integer.parseInt(mobile.getText().trim());
                Loanee loan = new Loanee(newBuyer, income, residence, phone);
                AddToDB add = new AddToDB(loan);
                add.getLoaneeAdded();
            }
            else{
                String buy;
                String [] buyNames = loanees.getValue().trim().split(" ");
                if (buyNames.length==3){
                 buy = buyNames[0]+" "+buyNames[1];
                }
                else buy = buyNames[0];
                addingExistedLoans(buy, income);
            }
            unpaidLoan(gardenName, income);
            pay = false;
        }


        Tib tib;
        if(pay) {

             tib = new Tib(gardenName, LocalDate.now(), gari, upadan, mal, income, "Paid");

        }

        else tib = new Tib(gardenName, LocalDate.now(), gari, upadan, mal, income, "Unpaid");


        AddToDB add = new AddToDB(tib);
        add.getTibAdded();


        incomeFromCar(gari);
        addingIncome(gardenName,income);
        refresh(event);
        getLoanListLoaded();
        carLoaded();

    }

    @FXML
    void unPaidAction(ActionEvent event) throws Exception{
        if(payment.getValue().equals("UNPAID / বাকী")){
            loanees.setVisible(true);
        }
        else{
            loanees.getSelectionModel().select("BUYER / ক্রেতা");
            loanees.setVisible(false);
            buyer.setVisible(false);
            address.setVisible(false);
            mobile.setVisible(false);
        }
    }




    private  void costFromCar(int numb, double amount){

        Car car = new Car (numb,amount);
        car.costOfCar();

    }





    private  void incomeFromCar(int numb){

        Car car = new Car(numb);
        car.incomeFromCar();

    }


    private void addingIncome(String bagan, double price) throws Exception {

        AddToDB income = new AddToDB(bagan, price);
        income.gardenIncomeAdded();

    }


    private void  unpaidLoan(String bagan, double income){

       Unpaid unpaid = new Unpaid(bagan, income);
       unpaid.unpaidloan();

    }


    private void addingExistedLoans(String buyer, double income){

      Loanee loanee= new Loanee(buyer, income);
      loanee.addingExistingLoanee();

    }


    @FXML
    void newBuyerAction(ActionEvent event) {
        if (loanees.getValue()!=null && loanees.getValue().equals("NEW BUYER / নতুন ক্রেতা")){
            buyer.setVisible(true);
            mobile.setVisible(true);
            address.setVisible(true);
        }
        else{
            buyer.setText("");
            mobile.setText("");
            address.setText("");
            buyer.setVisible(false);
            mobile.setVisible(false);
            address.setVisible(false);
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         buyer.setVisible(false);
         loanees.setVisible(false);
         mobile.setVisible(false);
         address.setVisible(false);
         carLoaded();
         getLoanListLoaded();
         loanAmount.setEditable(false);
         submitButton.setDisable(true);
         loanPaidButton.setDisable(true);
         costSumbit.setDisable(true);
         carNumber.setDisable(true);
         rentlist.setDisable(true);
         feeslist.setDisable(true);
         amountFR.setDisable(true);
         month.setDisable(true);
         submitFR.setDisable(true);

        rentOrFees.getSelectionModel().selectedIndexProperty().addListener(
                (ob,oldVal,newVal) -> {
                    if (rentOrFees.getValue()!=null && rentOrFees.getValue().equals("FEES / বেতন")) {
                        submitFR.disableProperty().unbind();
                        submitFR.disableProperty().bind(feeslist.valueProperty().isNull().or(amountFR.textProperty().isEmpty().or(month.valueProperty().isNull())));
                    }
                    else{
                        submitFR.disableProperty().unbind();
                        submitFR.disableProperty().bind(rentlist.valueProperty().isNull().or(amountFR.textProperty().isEmpty().or(month.valueProperty().isNull())));
                    }
                });


         loanPaidButton.disableProperty().bind(loaneeList.valueProperty().isNull().or(loanPay.textProperty().isEmpty()));

         costSumbit.disableProperty().bind(costSource.valueProperty().isNull().or(reason.textProperty().isEmpty().or(costAmount.textProperty().isEmpty())));

        if(payment.getValue()==null){
            submitButton.disableProperty().unbind();
            submitButton.disableProperty().bind(garden.valueProperty().isNull().or(car.valueProperty().isNull()).or(element.valueProperty().isNull()).or(amounts.textProperty().isEmpty()).or(prices.textProperty().isEmpty()).or(payment.valueProperty().isNull()));
        }
         payment.getSelectionModel().selectedIndexProperty().addListener(
                (ob,oldVal,newVal) -> {
                    if (payment.getValue().equals("UNPAID / বাকী")) {
                        if(loanees.getValue()==null){
                            submitButton.disableProperty().unbind();
                            submitButton.disableProperty().bind(garden.valueProperty().isNull().or(car.valueProperty().isNull()).or(element.valueProperty().isNull()).or(amounts.textProperty().isEmpty()).or(prices.textProperty().isEmpty()).or(payment.valueProperty().isNull()).or(loanees.valueProperty().isNull()));
                        }
                        loanees.getSelectionModel().selectedIndexProperty().addListener(
                                (ob1,oldVal1,newVal1) -> {
                                 if(loanees.getValue()!=null && loanees.getValue().equals("NEW BUYER / নতুন ক্রেতা")){
                                     submitButton.disableProperty().bind(garden.valueProperty().isNull().or(car.valueProperty().isNull()).or(element.valueProperty().isNull()).or(amounts.textProperty().isEmpty()).or(prices.textProperty().isEmpty()).or(payment.valueProperty().isNull()).or(loanees.valueProperty().isNull().or(buyer.textProperty().isEmpty()).or(address.textProperty().isEmpty().or(mobile.textProperty().isEmpty()))));
                                 }
                                 else {
                                     submitButton.disableProperty().bind(garden.valueProperty().isNull().or(car.valueProperty().isNull()).or(element.valueProperty().isNull()).or(amounts.textProperty().isEmpty()).or(prices.textProperty().isEmpty()).or(payment.valueProperty().isNull()).or(loanees.valueProperty().isNull()));
                                 }
                                }
                        );
                    }
                    else{
                        submitButton.disableProperty().bind(garden.valueProperty().isNull().or(car.valueProperty().isNull()).or(element.valueProperty().isNull()).or(amounts.textProperty().isEmpty()).or(prices.textProperty().isEmpty()).or(payment.valueProperty().isEqualTo("UNPAID / বাকী")));
                    }
                }
         );
    }


    public void getLoanListLoaded(){
            loaneeList.getItems().clear();
            if (loanees.getValue()!=null) {

                loanees.getItems().clear();

            }

            DBConnector db = DBConnector.getInstance();
            Connection conn = db.getConnection();
            Statement statement;
            try {
                statement = conn.createStatement();
                statement.setQueryTimeout(10);
                ResultSet rs = statement.executeQuery("SELECT *From 'Loanee'");
                loanees.getItems().add("NEW BUYER / নতুন ক্রেতা");
                while(rs.next()){
                        loaneeList.getItems().add(rs.getString("name")+" "+ rs.getString("mobile"));
                        loanees.getItems().add(rs.getString("name")+" "+ rs.getString("mobile"));
                }
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



    @FXML
    public void carLoaded(){

        car.getItems().clear();

        carNumber.getItems().clear();

        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.setQueryTimeout(10);
            ResultSet rs = statement.executeQuery("SELECT *From 'car'");

            while(rs.next()){
                car.getItems().add(rs.getString("number"));
                carNumber.getItems().add(rs.getString("number"));
            }
                carNumber.getItems().add("NUHA");

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


    @FXML
    void clearLoaneeAction(ActionEvent event) {
        if(loaneeList.getValue()!=null){
            loaneeList.setValue(null);
            loanAmount.setText("");
        }
        if(!loanPay.getText().equals("")){
            loanPay.setText("");
            loanAmount.setText("");
        }

    }



    @FXML
    void loanlistAction(ActionEvent event) {
        if(!loaneeList.getSelectionModel().isEmpty()) {
            String[] buyNames = loaneeList.getValue().trim().split(" ");
            String loaneeName;
            if (buyNames.length == 3) {
                loaneeName = buyNames[0] + " " + buyNames[1];
            } else loaneeName = buyNames[0];
            DBConnector db = DBConnector.getInstance();
            Connection conn = db.getConnection();
            Statement st;
            try {
                st = conn.createStatement();
                st.setQueryTimeout(10);
                ResultSet rs = st.executeQuery("SELECT * FROM Loanee WHERE name = '" + loaneeName + "'");
                String amount = Double.toString(rs.getDouble("amount")) + " tk";
                loanAmount.setText(amount);
                rs.close();
                st.close();
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
    }




    @FXML
    void loanPayingAction(ActionEvent event) throws Exception{
        String [] buyNames = loaneeList.getValue().trim().split(" ");
        String loaneeName;
        if (buyNames.length==3){
            loaneeName = buyNames[0]+" "+buyNames[1];
        }
        else loaneeName = buyNames[0];
        double payement = Double.parseDouble(loanPay.getText().trim());
        DBConnector db = DBConnector.getInstance();
        Connection conn = db.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.setQueryTimeout(10);
            ResultSet rs = st.executeQuery("SELECT * FROM Loanee WHERE name = '" + loaneeName+ "'");
            double amount = rs.getDouble("amount");
            double total = amount-payement;
            if(total<0){
                loanPay.setText("");
            }
            else if(total==0){

                st.executeUpdate("DELETE FROM  'Loanee'  WHERE name = '"+loaneeName+"'");
                loanees.getItems().remove(loaneeList.getValue());
                loaneeList.getItems().remove(loaneeList.getValue());

            }


            else {
                st.executeUpdate("UPDATE 'Loanee' SET amount = '" + total + "' WHERE name = '" + loaneeName + "'");
            }
            rs.close();
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
            getUnpaidLoanUpdated(payement);
            refresh(event);
      }



    public void getUnpaidLoanUpdated(double payement){

        Unpaid loanee = new Unpaid(payement);
        loanee.unpaidLoanUpdate();

    }



    private void refresh(ActionEvent event) throws Exception{
         garden.getSelectionModel().clearSelection();
         car.getSelectionModel().clearSelection();
         element.getSelectionModel().clearSelection();
         amounts.setText("");
         prices.setText("");
         buyer.setText("");
         loaneeList.setValue(null);
         loanAmount.setText("");
         loanPay.setText("");
      }


    @FXML
    void rentFeesSubmit(ActionEvent event) {

    }

    @FXML
    void rentOrFeesAction(ActionEvent event) {

        if(rentOrFees.getValue()!=null && rentOrFees.getValue().equals("FEES / বেতন")){

            rentlist.setDisable(true);
            feeslist.setDisable(false);
            amountFR.setDisable(false);
            month.setDisable(false);
            rentlist.getSelectionModel().clearSelection();

        }

        if(rentOrFees.getValue()!=null && rentOrFees.getValue().equals("RENT / ভাড়া")){

            feeslist.setDisable(true);
            rentlist.setDisable(false);
            amountFR.setDisable(false);
            month.setDisable(false);
            feeslist.getSelectionModel().clearSelection();

        }
    }

    public void rentFeesClear(ActionEvent event) {
          if(rentOrFees.getValue()!=null){
              rentOrFees.getSelectionModel().clearSelection();
          }
          if(rentlist.getValue()!=null) {
              rentlist.getSelectionModel().clearSelection();
          }
        if(feeslist.getValue()!=null) {
            feeslist.getSelectionModel().clearSelection();
        }
          amountFR.setText("");
          if(month.getValue()!=null) {
              month.getSelectionModel().clearSelection();
          }
          rentlist.setDisable(true);
          feeslist.setDisable(true);
    }



}

