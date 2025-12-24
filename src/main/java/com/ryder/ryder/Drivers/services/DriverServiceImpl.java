package com.ryder.ryder.Drivers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.ryder.ryder.Drivers.model.dtos.DriverLocationDto;
import com.ryder.ryder.Drivers.model.dtos.StartRideDto;
import com.ryder.ryder.Location.model.utils.GeometryUtil;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Trips.model.mappers.TripMapper;
import com.ryder.ryder.Trips.repositories.TripsRepo;
import com.ryder.ryder.Trips.services.TripService;
import com.ryder.ryder.Users.model.entity.Users;
import com.ryder.ryder.Users.repositories.UsersRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final UsersRepo usersRepo;

    private final TripsRepo tripsRepo;
    private final TripMapper tripMapper;

    // Driver location update
    @Override
    public DriverLocationDto updateCurrentLocation(String driverEmail, DriverLocationDto driverLocationDto) {
        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not Found!"));

        Point point = GeometryUtil.createPoint(
                GeometryUtil.createCoordinates(driverLocationDto.getLongitude(), driverLocationDto.getLatitude()));

        driver.setCurr_location(point);
        usersRepo.save(driver);

        return driverLocationDto;
    }

    // Nearby rides search
    @Override
    public List<TripResponseDto> getNearbyRides(String driverEmail) {
        Users driver = usersRepo.findByEmail(driverEmail).orElseThrow(() -> new RuntimeException("Driver Not Found!"));

        if (driver.getCurr_location() == null) {
            throw new RuntimeException("Driver location Unknown. Please go Online!");
        }

        // NearBy Radius limit
        Double radiusKm = 10.0;

        List<Trips> nearbyTrips = tripsRepo.nearByRides(driver.getCurr_location(), radiusKm);

        return nearbyTrips.stream()
                .map(trip -> tripMapper.toResponseDto(trip))
                .collect(Collectors.toList());

    }

    // Ride Acceptance
    @Override
    // for concurrency
    @Transactional
    public TripResponseDto acceptRide(Long tripId, String driverEmail) {
        Users driver = usersRepo.findByEmail(driverEmail).orElseThrow(() -> new RuntimeException("Driver not Found!"));

        Trips trip = tripsRepo.findById(tripId).orElseThrow(() -> new RuntimeException("Trip  not found!"));

        // if Ride is accepted
        if (!trip.getStatus().equals(TripStatus.REQUESTED)) {
            throw new RuntimeException("Ride is no longer requested. Status is: " + trip.getStatus());
        }

        trip.setStatus(TripStatus.ACCEPTED);
        trip.setDriver(driver);
        tripsRepo.save(trip);

        return tripMapper.toResponseDto(trip);

    }

    @Override
    public TripResponseDto startRide(String driverEmail, StartRideDto startRideDto) {

        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not Found!"));

        Trips trip = tripsRepo.findById(startRideDto.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found!"));

        if (!trip.getDriver().equals(driver)) {
            throw new RuntimeException("Trip not assigned to you");
        }
        if (!trip.getStatus().equals(TripStatus.ACCEPTED)) {
            throw new RuntimeException("Ride cannot be accepted. Status is: " + trip.getStatus());
        }
        if (!trip.getOtp().equals(startRideDto.getOtp())) {
            throw new RuntimeException("OTP does not match!");
        }

        trip.setStatus(TripStatus.STARTED);
        trip.setStartedAt(LocalDateTime.now());
        tripsRepo.save(trip);

        return tripMapper.toResponseDto(trip);
    }

    @Override
    public TripResponseDto completeRide(Long tripId, String driverEmail) {

        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new RuntimeException("Driver not Found!"));

        Trips trip = tripsRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found!"));

        if (!trip.getDriver().equals(driver)) {
            throw new RuntimeException("Trip not assigned to you");
        }
        if (!trip.getStatus().equals(TripStatus.STARTED)) {
            throw new RuntimeException("Ride has'nt Started. Status is: " + trip.getStatus());
        }

        trip.setStatus(TripStatus.COMPLETED);
        trip.setCompletedAt(LocalDateTime.now());

        // Future: Fare recalculation based on time

        tripsRepo.save(trip);

        return tripMapper.toResponseDto(trip);
    }

}
