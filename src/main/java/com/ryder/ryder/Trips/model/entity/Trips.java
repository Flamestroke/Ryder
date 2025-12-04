package com.ryder.ryder.Trips.model.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    // Get Latitude and Longitude from Coordinates.java for source of trip
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "source_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "source_longitude"))
    })
    private Coordinates source;

    // Get Latitude and Longitude from Coordinates.java for destination of trip
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude"))
    })
    private Coordinates destination;

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
