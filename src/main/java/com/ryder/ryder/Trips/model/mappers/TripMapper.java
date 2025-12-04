package com.ryder.ryder.Trips.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ryder.ryder.Location.model.entity.Coordinates;
import com.ryder.ryder.Trips.model.dtos.TripHistoryDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Users.model.mappers.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TripMapper {

    TripResponseDto toResponseDto(Trips trips);

    @Mapping(target = "date", source = "requestedAt")
    TripHistoryDto toHistoryDto(Trips trips);

    default String mapCoords(Coordinates coordinates) {

        if (coordinates == null) {
            return null;
        }

        return coordinates.getLatitude() + "," + coordinates.getLongitude();

    }

}
