package com.driver.services.impl;

import com.driver.Entity.ParkingLot;
import com.driver.Entity.Reservation;
import com.driver.Entity.Spot;
import com.driver.Entity.User;
import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.assertj.core.util.diff.Delta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        }catch (Exception e){
            throw new RuntimeException("reservation cannot be made");
        }

        User user;
        try{
            user = userRepository3.findById(userId).get();
        }catch (Exception e){
            throw new RuntimeException("reservation cannot be made");
        }
        List<Spot> spots = parkingLot.getSpots();
        boolean found = false;
        String type;
        if(numberOfWheels == 2){
            type = "TWO_WHEELER";
        }else if(numberOfWheels == 4){
            type = "FOUR_WHEELER";
        }else{
            type = "OTHERS";
        }
        int bill = Integer.MAX_VALUE;
        Spot spot = null;
        for(Spot s:spots){
            String curt = s.getSpotType().toString();
            if(curt == type){
                int currbil = s.getPricePerHour() * timeInHours;
                if(currbil < bill){
                    spot = s;
                    bill = currbil;
                    found = true;
                }
            }
        }

        if(found == false){
            throw new RuntimeException("reservation cannot be made");
        }

        Reservation reservation = new Reservation(timeInHours);
        List<Reservation> userRes = user.getReservationList();
        List<Reservation> spotRes = spot.getReservationList();
        userRes.add(reservation);
        spotRes.add(reservation);
        user.setReservationList(userRes);
        spot.setReservationList(spotRes);
        spot.setOccupied(true);
        spot.setParkingLot(parkingLot);
        // update spot in parking lot
        List<Spot> spotsInPl = parkingLot.getSpots();
        for(Spot s:spotsInPl){
            if(s.getId() == spot.getId()){
                s.setPricePerHour(spot.getPricePerHour());
                s.setOccupied(spot.isOccupied());
                s.setReservationList(spot.getReservationList());
                break;
            }
        }
        userRepository3.save(user);
        spotRepository3.save(spot);
        parkingLot.setSpots(spotsInPl);
        parkingLotRepository3.save(parkingLot);
        reservationRepository3.save(reservation);
        return reservation;
    }
}
