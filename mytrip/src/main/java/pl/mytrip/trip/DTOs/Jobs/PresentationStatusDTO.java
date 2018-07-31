package pl.mytrip.trip.DTOs.Jobs;

import lombok.Data;

@Data
public class PresentationStatusDTO {
    String errorMessage;
    String resultStorageUrl;
    String status;
}
