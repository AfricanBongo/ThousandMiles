package com.thousandmiles.models.discount;

public class DiscountOverView {
    public Double averageDiscountPrice;
    public DiscountOffer bestDiscountOffer;

    public Double getAverageDiscountPrice() {
        return averageDiscountPrice;
    }

    public void setAverageDiscountPrice(Double averageDiscountPrice) {
        this.averageDiscountPrice = averageDiscountPrice;
    }

    public DiscountOffer getBestDiscountOffer() {
        return bestDiscountOffer;
    }

    public void setBestDiscountOffer(DiscountOffer bestDiscountOffer) {
        this.bestDiscountOffer = bestDiscountOffer;
    }
}
