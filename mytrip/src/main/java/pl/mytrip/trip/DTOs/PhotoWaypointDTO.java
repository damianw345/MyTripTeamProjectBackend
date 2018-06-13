package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class PhotoWaypointDTO {

    private Long photoId;
    private Long waypointId;
    private Date date;
    private String url;
    private String thumbnailUrl;
}
