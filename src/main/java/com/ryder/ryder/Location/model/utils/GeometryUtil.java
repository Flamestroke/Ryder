package com.ryder.ryder.Location.model.utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.GeometricShapeFactory;

import com.ryder.ryder.Location.model.entity.Coordinates;

public class GeometryUtil {
    public static final int SRID = 4326;
    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), SRID);

    // DTO to PostGIS
    public static Point createPoint(Coordinates source) {
        return factory.createPoint(new Coordinate(source.getLongitude(), source.getLatitude()));

    }

    // PostGIS to DTO
    public static Coordinates createCoordinates(Point point) {
        return new Coordinates(point.getY(), point.getX());
    }

    public static double calculateDistancekm(Point src, Point des) {
        return calculateHaversineDistance(src.getX(), src.getY(), des.getX(), des.getY());
    }

    public static double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double sinDlat = Math.sin(dlat / 2);
        double sinDlon = Math.sin(dlon / 2);

        double a = (sinDlat * sinDlat) + (sinDlon * sinDlon) * Math.cos(lat1) * Math.cos(lat2);

        double rad = 6371; // earth radius

        double c = 2 * Math.asin(Math.sqrt(a));

        return rad * c;

    }

}
