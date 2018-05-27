package pl.mytrip.trip.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class TripPointDTO {

    private Date date;
    private Float longitude;
    private Float latitude;
    // TODO
//    private List<PhotoDTO> photos;
//    private List<VideoDTO> videos;
}
