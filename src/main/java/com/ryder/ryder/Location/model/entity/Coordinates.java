package com.ryder.ryder.Location.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    private Double longitude;
    private Double latitude;

}
