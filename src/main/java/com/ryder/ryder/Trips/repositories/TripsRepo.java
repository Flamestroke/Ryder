package com.ryder.ryder.Trips.repositories;

import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Users.model.entity.Users;

import java.util.List;

@Repository
public interface TripsRepo extends JpaRepository<Trips, Long> {

    // rider who is on active trip
    Optional<Trips> findByRiderAndStatusNotIn(Users rider, List<TripStatus> finishedStatuses);
    
    // rider who is not on active trip
    Page<Trips> findAllByRiderAndStatusIn(Users rider, List<TripStatus> finishedStatuses, Pageable pageable);

    // to locate nearby rides
    @Query(value = "SELECT * FROM trips t " +
            "WHERE t.status = 'REQUESTED' " +
            "AND ST_DistanceSphere(t.source, :driverLocation) < :radiusKm * 1000", nativeQuery = true)
    List<Trips> nearByRides(Point driverLocation, Double radiusKm);
}
