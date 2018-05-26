package pl.mytrip.trip.dto;

import lombok.Data;

@Data
public class PhotoDTO {

    private Long waypointId;
    private String thumbnailStorageUrl;
    private String fullImageStorageUrl;
}
