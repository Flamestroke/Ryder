package com.ryder.ryder.Trips.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Users.model.entity.Users;

import java.util.List;

@Repository
public interface TripsRepo extends JpaRepository<Trips, Long> {

    Optional<Trips> findByRiderAndStatusNotIn(Users rider, List<TripStatus> finishedStatuses);

    Page<Trips> findAllByRiderAndStatusIn(Users rider, List<TripStatus> finishedStatuses, Pageable pageable);

}
