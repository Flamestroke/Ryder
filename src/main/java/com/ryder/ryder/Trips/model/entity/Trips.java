package com.ryder.ryder.Trips.model.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import com.ryder.ryder.Location.model.entity.Coordinates;
import com.ryder.ryder.Trips.model.enums.TripStatus;
import com.ryder.ryder.Users.model.entity.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trips")
public class Trips {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // Get Rider From Users.java
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "rider_id", nullable = false)
        private Users rider;

        // Get Driver From Users.java
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "driver_id")
        private Users driver;

        // PostGIS definition
        @Column(columnDefinition = "geometry(Point, 4326)")
        private Point source;

        @Column(columnDefinition = "geometry(Point, 4326)")
        private Point destination;

        @Enumerated(EnumType.STRING)
        private TripStatus status;

        private Double fare;

        private String otp;

        @CreationTimestamp
        private Date requestedAt;

        private Date startedAt;

        private Date completedAt;

        @UpdateTimestamp
        private Date updatedAt;

}
