package pl.mytrip.trip.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BasicTripDTO {

    private Long tripId;
    private String name;
    private Date start;
    private Date end;
}
