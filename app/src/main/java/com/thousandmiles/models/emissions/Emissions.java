package com.thousandmiles.models.emissions;

public class Emissions {
    public double amountInKg;

    public void setAmountInKg(double amountInKg) {
        this.amountInKg = amountInKg;
    }


    @Override
    public String toString() {
        return "Emissions{" +
                "amountInKg=" + amountInKg +
                '}';
    }
}
