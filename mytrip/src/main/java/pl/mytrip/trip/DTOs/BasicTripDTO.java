package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class BasicTripDTO {

    private String tripId;
    private String name;
    private Date start;
    private Date end;
}
