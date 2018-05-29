package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class PhotoInfoDTO {

    private Long waypointId;
    private Date date;
    private String url;
    private String thumbnailUrl;
}