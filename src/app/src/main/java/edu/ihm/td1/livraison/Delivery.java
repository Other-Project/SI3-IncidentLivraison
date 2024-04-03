package edu.ihm.td1.livraison;

import androidx.annotation.NonNull;

import java.util.Random;

public class Delivery {
    private String address;

    public Delivery(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public double getDistance(double currentLat, double currentLong) {
        return new Random().nextDouble() * 10;
    }

    @NonNull
    @Override
    public String toString() {
        return address;
    }
}
