package pl.mytrip.trip.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class WaypointDTO {

    private Long tripId;
    private Float longitude;
    private Float latitude;
    private Date date;
}
