package com.driver.Entity;

import com.driver.Entity.Spot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    public ParkingLot(){

    }

    public ParkingLot(String name,String address){
        this.name = name;
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @OneToMany(mappedBy = "parkingLot",cascade = CascadeType.ALL)
    List<Spot> spots = new ArrayList<>();


}

