package com.driver.Entity;

import com.driver.Entity.ParkingLot;
import com.driver.model.SpotType;

//import com.sun.tools.javac.jvm.Gen;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private SpotType spotType;
    private int pricePerHour;
    private boolean occupied;

    public Spot() {
    }


    public int getId() {
        return id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    @ManyToOne
    @JoinColumn
    ParkingLot parkingLot;
    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    List<Reservation> reservationList = new ArrayList<>();
}
