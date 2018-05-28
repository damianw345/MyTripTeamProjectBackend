package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class VideoInfoDTO {

    private Long videoId;
    private Date date;
    private String url;
}