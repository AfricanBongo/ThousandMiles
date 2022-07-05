package com.thousandmiles.models.discount;

import java.util.List;

public class DiscountOffer {
    public String serviceProvider ;
    public Double discountPrice;
    public List <ServiceStationLocation> serviceStationLocations;


        // Constructor of Discount offer class
    DiscountOffer(String serviceProvider, double discountPrice,List <ServiceStationLocation> serviceStationLocations)
    {
        // This keyword refers to current instance itself
        this.serviceProvider = serviceProvider;
        this.discountPrice = discountPrice;
        this.serviceStationLocations =serviceStationLocations;


    }
    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String id) {
        this.serviceProvider = serviceProvider;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String icon) {
        this.discountPrice = discountPrice;
    }

    public List<ServiceStationLocation> getServiceStationLocations() {
        return serviceStationLocations;
    }

    public void setServiceStationLocations(List<ServiceStationLocation> serviceStationLocations) {
        this.serviceStationLocations = serviceStationLocations;
    }

    // return price as formatted string
    public String discountPriceAsString() {
        String price;
        price = "USD$" + discountPrice.toString();
        return price;
    }


}
