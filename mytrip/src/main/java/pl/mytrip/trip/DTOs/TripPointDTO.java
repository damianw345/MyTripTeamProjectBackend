package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripPointDTO {

    private Long waypointId;
    private Float latitude;
    private Float longitude;
    private Date date;
    private List<PhotoInfoDTO> photos;
    private List<VideoInfoDTO> videos;
}
