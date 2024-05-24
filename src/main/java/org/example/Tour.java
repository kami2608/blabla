package org.example;

import java.io.Serializable;

public class Tour extends Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String destination;
    private int duration;
    private String tourGuide;

    public Tour() {}

    public Tour(int productId, String productName, double productPrice, int productTotal, String destination, int duration, String tourGuide) {
        super(productId, productName, productPrice, productTotal);
        this.destination = destination;
        this.duration = duration;
        this.tourGuide = tourGuide;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTourGuide() {
        return tourGuide;
    }

    public void setTourGuide(String tourGuide) {
        this.tourGuide = tourGuide;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "productId=" + getProductId() +
                ", productName='" + getProductName() + '\'' +
                ", productPrice=" + getProductPrice() +
                ", productTotal=" + getProductTotal() +
                ", destination='" + destination + '\'' +
                ", duration=" + duration +
                ", tourGuide='" + tourGuide + '\'' +
                '}';
    }
}
