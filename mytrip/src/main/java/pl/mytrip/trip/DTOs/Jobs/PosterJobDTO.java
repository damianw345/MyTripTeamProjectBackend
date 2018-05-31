package pl.mytrip.trip.DTOs.Jobs;

import lombok.Data;
import pl.mytrip.trip.DTOs.TripDTO;

@Data
public class PosterJobDTO {

    private TripDTO tripDTO;
    private String callbackUrl;
    private String resultStorageUrl;

}
