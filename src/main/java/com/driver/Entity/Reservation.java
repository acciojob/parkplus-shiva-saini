package com.driver.Entity;

import javax.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numOfHours;
    public Reservation(int numOfHours){
        this.numOfHours = numOfHours;
    }
    public Reservation(){

    }

    public int getId() {
        return id;
    }

    public int getNumOfHours() {
        return numOfHours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumOfHours(int numOfHours) {
        this.numOfHours = numOfHours;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public Spot getSpot() {
        return spot;
    }

    public Payment getPayment() {
        return payment;
    }

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Spot spot;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    Payment payment;
}
