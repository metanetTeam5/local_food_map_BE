package com.metanet.amatmu.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationUpdateDto {
    private String resvDate;
    private String resvHour;
    private String resvRequirement;
}