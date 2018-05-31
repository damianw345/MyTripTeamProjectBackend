package pl.mytrip.trip.DTOs;

import lombok.Data;

@Data
public class ThumbnailJobDTO {

    private String fullImageStorageUrl;
    private String callbackUrl;
    private String resultStorageUrl;

}
