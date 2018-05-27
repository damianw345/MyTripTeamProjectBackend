package pl.mytrip.trip.DTOs;

import lombok.Data;

@Data
public class PhotoDTO {

    private Long waypointId;
    private String thumbnailStorageUrl;
    private String fullImageStorageUrl;
}
