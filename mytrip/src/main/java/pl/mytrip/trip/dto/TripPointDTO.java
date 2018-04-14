package pl.mytrip.trip.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TripPointDTO {

    private Date date;
    private Float longitude;
    private Float latitude;
    // TODO
//    private List<PhotoDTO> photos;
//    private List<VideoDTO> videos;
}
