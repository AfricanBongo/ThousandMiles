package com.thousandmiles.models.steps;

public class Steps {
    public double distance;
    public int numberOfSteps;
    public Long activeTimeInMillis;

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;

    }

    public Long getActiveTimeInMillis() {
        return activeTimeInMillis;
    }

    public void setActiveTimeInMillis(Long activeTimeInMillis) {
        this.activeTimeInMillis = activeTimeInMillis;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String formattedActiveTime() {

      return  activeTimeInMillis.toString();
    }
}
