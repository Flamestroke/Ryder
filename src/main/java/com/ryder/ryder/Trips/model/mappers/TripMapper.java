package com.ryder.ryder.Trips.model.mappers;

import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ryder.ryder.Location.model.entity.Coordinates;
import com.ryder.ryder.Location.model.utils.GeometryUtil;
import com.ryder.ryder.Trips.model.dtos.TripHistoryDto;
import com.ryder.ryder.Trips.model.dtos.TripRequestDto;
import com.ryder.ryder.Trips.model.dtos.TripResponseDto;
import com.ryder.ryder.Trips.model.entity.Trips;
import com.ryder.ryder.Users.model.mappers.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TripMapper {

    TripResponseDto toResponseDto(Trips trips);

    @Mapping(target = "date", source = "requestedAt")   
    TripHistoryDto toHistoryDto(Trips trips);

    Trips toEntity(TripRequestDto request);

    // for toResponseDto (to Coordinates)
    default Coordinates mapPointToCoordinates(Point point) {

        if (point == null) {
            return null;
        }

        return GeometryUtil.createCoordinates(point);

    }

    // for Request
    default Point mapCoordinatesToPoint(Coordinates coordinates) {
        if (coordinates == null)
            return null;
        return GeometryUtil.createPoint(coordinates);
    }

    // for toHistoryDto (to String)
    default String mapPointToString(Point point) {

        if (point == null) {
            return null;
        }

        return point.getY() + "," + point.getX();

    }

}
