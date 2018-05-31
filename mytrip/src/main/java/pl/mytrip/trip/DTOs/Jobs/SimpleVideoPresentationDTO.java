package pl.mytrip.trip.DTOs.Jobs;

import lombok.Data;

import java.util.List;

@Data
public class SimpleVideoPresentationDTO {

    private List<String> photos;
    private String callbackUrl;
    private String resultStorageUrl;

}
