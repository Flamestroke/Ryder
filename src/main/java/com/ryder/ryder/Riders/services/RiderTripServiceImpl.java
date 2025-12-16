package com.ryder.ryder.Riders.services;

import java.util.List;
import java.util.Random;

import org.springframework.data.domain.Pageable;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ryder.ryder.Location.model.utils.GeometryUtil;
import com.ryder.ryder.Trips.model.dtos.TripHistoryDto;
import com.ryder.ryder.Trips.model.dtos.TripRequestDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Trips.model.mappers.TripMapper;
import com.ryder.ryder.Trips.repositories.TripsRepo;
import com.ryder.ryder.Trips.services.TripServiceImpl;
import com.ryder.ryder.Users.model.entity.Users;
import com.ryder.ryder.Users.repositories.UsersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderTripServiceImpl implements RiderTripService {

    private final TripsRepo tripsRepo;
    private final UsersRepo usersRepo;
    private final TripMapper tripMapper;

    public static final List<TripStatus> FINISHED_STATUSES = List.of(TripStatus.COMPLETED, TripStatus.CANCELLED);

    // Request Ride
    @Override
    public TripResponseDto requestRide(String email, TripRequestDto request) {
        Users rider = getRider(email);
        var activeTrips = tripsRepo.findByRiderAndStatusNotIn(rider, FINISHED_STATUSES);
        if (activeTrips.isPresent()) {
            throw new RuntimeException("Cannot request a new ride. You have an active trip!");
        }

        Trips newTrip = tripMapper.toEntity(request);
        double distanceKm = calculateDistanceKm(newTrip.getSource(), newTrip.getDestination());
        Double fare = 50.0 + (10.0 * distanceKm);

        newTrip.setRider(rider);
        newTrip.setStatus(TripStatus.REQUESTED);
        newTrip.setFare(fare);
        newTrip.setOtp(generateRandomOtp());
        Trips savedTrip = tripsRepo.save(newTrip);
        return tripMapper.toResponseDto(savedTrip);
    }

    // Current Trip
    @Override
    public TripResponseDto getCurentTrip(String email) {
        Users rider = getRider(email);

        return tripsRepo.findByRiderAndStatusNotIn(rider, FINISHED_STATUSES)
                .map(tripMapper::toResponseDto)
                .orElse(null);

    }

    // Past Trips/ Trip history
    @Override
    public Page<TripHistoryDto> getTripHistory(String email, int page, int size) {
        Users rider = getRider(email);

        Pageable pageable = PageRequest.of(page, size, Sort.by("requestedAt").descending());

        Page<Trips> tripPage = tripsRepo.findAllByRiderAndStatusIn(rider, FINISHED_STATUSES, pageable);

        return tripPage.map(tripMapper::toHistoryDto);

    }

    private Users getRider(String email) {
        return usersRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Rider not found!!"));
    }

    private double calculateDistanceKm(Point p1, Point p2) {
        return GeometryUtil.calculateHaversineDistance(
                p1.getY(), p1.getX(), // Y is Lat, X is Long
                p2.getY(), p2.getX());
    }

    private String generateRandomOtp() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
