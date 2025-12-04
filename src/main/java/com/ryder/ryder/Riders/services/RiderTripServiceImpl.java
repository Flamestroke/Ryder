package com.ryder.ryder.Riders.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ryder.ryder.Trips.model.dtos.TripHistoryDto;
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

}
