package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WaypointDTO {

    private String tripId;
    private Long waypointId;
    private Float longitude;
    private Float latitude;
    private Date date;
    private List<PhotoInfoDTO> photos;
    private List<VideoInfoDTO> videos;
}
