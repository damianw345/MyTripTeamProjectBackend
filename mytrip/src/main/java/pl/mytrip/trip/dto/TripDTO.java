package pl.mytrip.trip.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripDTO {

    private Long tripId;
    private String name;
    private String description;
    private List<TripPointDTO> points;
    private Date start;
    private Date end;
}
