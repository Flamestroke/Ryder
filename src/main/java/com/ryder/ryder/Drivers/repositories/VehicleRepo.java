package com.ryder.ryder.Drivers.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ryder.ryder.Drivers.model.entity.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findById(Long driverId);

}
