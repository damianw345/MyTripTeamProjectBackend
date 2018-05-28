package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class WaypointDTO {

    private Float longitude;
    private Float latitude;
    private Date date;
}
