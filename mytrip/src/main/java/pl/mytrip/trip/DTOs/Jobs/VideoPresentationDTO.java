package pl.mytrip.trip.DTOs.Jobs;

import lombok.Data;

import java.util.Set;

@Data
public class VideoPresentationDTO {

    private Set<String> photos;
    private String callbackUrl;

}
