package sample;

/**
 * Created by abdul on 6/17/2017.
 */
public class Garden{

    private String name;
    private double income;
    private double cost;
    private double unpaid;

    public Garden(String name, double income, double cost, double unpaid) {
        this.name = name;
        this.income = income;
        this.cost = cost;
        this.unpaid = unpaid;
    }

    public String getName() {
        return name;
    }

    public double getIncome() {
        return income;
    }

    public double getCost() {
        return cost;
    }

    public double getUnpaid() {
        return unpaid;
    }
}
