package sample;

import java.time.LocalDate;

public class Tib {


    private String garden;
    private LocalDate date;
    private int car;
    private String element;
    private double amount;
    private double price;
    private String payment;


    public Tib(String garden, LocalDate date, int car, String element, double amount, double price, String payment) {

        this.garden = garden;
        this.date = date;
        this.car = car;
        this.element = element;
        this.amount = amount;
        this.price = price;
        this.payment = payment;

    }

    public String getGarden() {
        return garden;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCar() {
        return car;
    }

    public String getElement() {
        return element;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getPayment() {
        return payment;
    }

}
