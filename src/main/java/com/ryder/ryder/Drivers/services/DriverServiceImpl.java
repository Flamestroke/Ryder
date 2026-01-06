package com.ryder.ryder.Drivers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.ryder.ryder.Common.Exceptions.InvalidCredentialsException;
import com.ryder.ryder.Common.Exceptions.NotFoundException;
import com.ryder.ryder.Drivers.model.dtos.DriverLocationDto;
import com.ryder.ryder.Drivers.model.dtos.StartRideDto;
import com.ryder.ryder.Drivers.model.dtos.VehicleDataDto;
import com.ryder.ryder.Drivers.model.entity.Vehicle;
import com.ryder.ryder.Drivers.model.mappers.VehicleMapper;
import com.ryder.ryder.Drivers.repositories.VehicleRepo;
import com.ryder.ryder.Location.model.utils.GeometryUtil;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Trips.model.mappers.TripMapper;
import com.ryder.ryder.Trips.repositories.TripsRepo;
import com.ryder.ryder.Trips.services.TripService;
import com.ryder.ryder.Users.model.dtos.UserProfileDto;
import com.ryder.ryder.Users.model.dtos.UserUpdateRequestDto;
import com.ryder.ryder.Users.model.entity.Users;
import com.ryder.ryder.Users.repositories.UsersRepo;
import com.ryder.ryder.Users.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final UsersRepo usersRepo;
    private final UserService userService;

    private final TripsRepo tripsRepo;
    private final TripMapper tripMapper;

    private final VehicleRepo VehicleRepo;
    private final VehicleMapper vehicleMapper;

    // Show Rider Profile
    @Override
    public UserProfileDto getMyProfileDto(String driverEmail) {
        return userService.getMyProfile(driverEmail);
    }

    // Update Rider Profile
    @Override
    public UserProfileDto updateMyProfile(String driverEmail, UserUpdateRequestDto request) {
        return userService.updateMyProfile(driverEmail, request);
    }

    // Add Vehicle Details of the Driver
    @Override
    public VehicleDataDto addVehicle(String driverEmail, VehicleDataDto vehicleDataDto) {
        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new NotFoundException("Driver not Found!"));

        Vehicle vehicle = vehicleMapper.toEntity(vehicleDataDto);

        vehicle.setDriver(driver);

        VehicleRepo.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    // Driver location update
    // to be done by websocket
    @Override
    public DriverLocationDto updateCurrentLocation(String driverEmail, DriverLocationDto driverLocationDto) {
        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new NotFoundException("Driver not Found!"));

        Point point = GeometryUtil.createPoint(
                GeometryUtil.createCoordinates(driverLocationDto.getLongitude(), driverLocationDto.getLatitude()));

        driver.setCurr_location(point);
        usersRepo.save(driver);

        return driverLocationDto;
    }

    // Search for rides(customer) near current location
    @Override
    public List<TripResponseDto> getNearbyRides(String driverEmail) {
        Users driver = usersRepo.findByEmail(driverEmail).orElseThrow(() -> new NotFoundException("Driver Not Found!"));

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

    // Accept REQUESTED ride
    @Override
    // for concurrency
    @Transactional
    public TripResponseDto acceptRide(Long tripId, String driverEmail) {
        Users driver = usersRepo.findByEmail(driverEmail).orElseThrow(() -> new NotFoundException("Driver not Found!"));

        Trips trip = tripsRepo.findById(tripId).orElseThrow(() -> new NotFoundException("Trip  not found!"));

        // if Ride is accepted
        if (!trip.getStatus().equals(TripStatus.REQUESTED)) {
            throw new RuntimeException("Ride is no longer requested. Status is: " + trip.getStatus());
        }

        trip.setStatus(TripStatus.ACCEPTED);
        trip.setDriver(driver);
        tripsRepo.save(trip);

        return tripMapper.toResponseDto(trip);

    }

    // Start ACCEPTED ride
    @Override
    public TripResponseDto startRide(String driverEmail, StartRideDto startRideDto) {

        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new NotFoundException("Driver not Found!"));

        Trips trip = tripsRepo.findById(startRideDto.getTripId())
                .orElseThrow(() -> new NotFoundException("Trip not found!"));

        if (!trip.getDriver().equals(driver)) {
            throw new RuntimeException("Trip not assigned to you");
        }
        if (!trip.getStatus().equals(TripStatus.ACCEPTED)) {
            throw new RuntimeException("Ride cannot be accepted. Status is: " + trip.getStatus());
        }
        if (!trip.getOtp().equals(startRideDto.getOtp())) {
            throw new InvalidCredentialsException("OTP does not match!");
        }

        trip.setStatus(TripStatus.STARTED);
        trip.setStartedAt(LocalDateTime.now());
        tripsRepo.save(trip);

        return tripMapper.toResponseDto(trip);
    }

    // Complete STARTED ride
    @Override
    public TripResponseDto completeRide(Long tripId, String driverEmail) {

        Users driver = usersRepo.findByEmail(driverEmail)
                .orElseThrow(() -> new NotFoundException("Driver not Found!"));

        Trips trip = tripsRepo.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip not found!"));

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
