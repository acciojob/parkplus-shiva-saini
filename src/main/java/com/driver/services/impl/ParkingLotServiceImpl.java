package com.driver.services.impl;

import com.driver.Entity.ParkingLot;
import com.driver.Entity.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        if(numberOfWheels == 2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        }else if(numberOfWheels == 4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }else{
            spot.setSpotType(SpotType.OTHERS);
        }
        spot.setPricePerHour(pricePerHour);

        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        }catch (Exception e){
            throw new RuntimeException("Invalid Parking lot id");
        }

        parkingLot.getSpots().add(spot);
        spot.setParkingLot(parkingLot);
        parkingLotRepository1.save(parkingLot);
        return spot;


    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot;
        try{
            spot = spotRepository1.findById(spotId).get();
        }catch (Exception e){
            throw new RuntimeException("Invalid spot Id");
        }

        ParkingLot parkingLot = spot.getParkingLot();
        List<Spot> spots = parkingLot.getSpots();
        int idx = -1;
        for(Spot s:spots){
            idx++;
            if(s.getId() == spotId){
                break;
            }
        }
        spots.remove(idx);
        parkingLot.setSpots(spots);
        spotRepository1.deleteById(spotId);
        parkingLotRepository1.save(parkingLot);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
           ParkingLot parkingLot;
           try{
               parkingLot = parkingLotRepository1.findById(parkingLotId).get();
           }catch (Exception e){
               throw new RuntimeException("Invalid Parking lot Id");
           }

           Spot spot;
           try{
               spot = spotRepository1.findById(spotId).get();
           }catch (Exception e){
               throw new RuntimeException("Invalid Spot Id");
           }

           List<Spot> spots = parkingLot.getSpots();
           for(Spot s:spots){
               if(s.getId() == spotId){
                   s.setPricePerHour(pricePerHour);
                   break;
               }
           }
           parkingLot.setSpots(spots);
           spot.setPricePerHour(pricePerHour);
           parkingLotRepository1.save(parkingLot);
           spotRepository1.save(spot);
           return spot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
          ParkingLot parkingLot;
          try{
              parkingLot = parkingLotRepository1.findById(parkingLotId).get();
          }catch (Exception e){
              throw new RuntimeException("Invalid parkingLot Id");
          }

          List<Spot> spots = parkingLot.getSpots();
          for(Spot s:spots){
              spotRepository1.deleteById(s.getId());
          }
          parkingLotRepository1.deleteById(parkingLotId);
    }
}
