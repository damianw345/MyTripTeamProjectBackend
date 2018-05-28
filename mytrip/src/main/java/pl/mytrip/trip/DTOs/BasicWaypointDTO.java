package pl.mytrip.trip.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BasicWaypointDTO {

    private Float longitude;
    private Float latitude;
    private Date date;
}
