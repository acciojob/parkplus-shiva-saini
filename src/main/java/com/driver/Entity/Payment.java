package com.driver.Entity;

import com.driver.model.PaymentMode;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean paymentCompleted;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @OneToOne
    @JoinColumn
    private Reservation reservation;

    public void setId(int id) {
        this.id = id;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Payment(){

    }
}
