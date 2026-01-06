package com.ryder.ryder.Drivers.model.mappers;

import org.mapstruct.Mapper;

import com.ryder.ryder.Drivers.model.dtos.VehicleDataDto;
import com.ryder.ryder.Drivers.model.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleDataDto vehicleDataDto);

    VehicleDataDto toDto(Vehicle Vehicle);
}
