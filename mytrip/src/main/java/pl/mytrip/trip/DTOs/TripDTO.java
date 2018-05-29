package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripDTO {

    private String tripId;
    private String name;
    private String description;
    private Date start;
    private Date end;
    private List<WaypointDTO> waypoints;
}
