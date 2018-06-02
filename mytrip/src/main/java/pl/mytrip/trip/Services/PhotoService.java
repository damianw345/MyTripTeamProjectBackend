package pl.mytrip.trip.Services;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.DTOs.PhotoInfoDTO;
import pl.mytrip.trip.Mappers.PhotoMapper;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.Repositories.PhotoRepository;
import pl.mytrip.trip.Repositories.TripRepository;
import pl.mytrip.trip.Repositories.WaypointRepository;
import pl.mytrip.trip.StorageConnector;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Slf4j
@Data
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PhotoService {

    private final StorageConnector storageConnector;
    private final PhotoRepository photoRepository;
    private final QueueJobService queueJobService;
    private final TripRepository tripRepository;
    private final WaypointRepository waypointRepository;
    private final PhotoMapper photoMapper;

    public String addPhoto(String tripId, byte[] photoBytes, PhotoInfoDTO photoInfoDTO, String extension) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("photos");
        CloudBlob blob;
        try {
            Waypoint waypoint = Optional.ofNullable(waypointRepository.findOne(photoInfoDTO.getWaypointId()))
                    .orElseThrow(NotFoundException::new);
            Photo photo = Optional.ofNullable(photoInfoDTO)
                    .map(photoMapper::toEntity)
                    .map(entity -> { entity.setWaypoint(waypoint); return entity; })
                    .map(photoRepository::save).orElseThrow(NotFoundException::new);

            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId + "_"
                    + photo.getPhotoId() + "."
                    + extension);
            blob.uploadFromByteArray(photoBytes, 0, photoBytes.length);

            photo.setUrl(blob.getUri().toString());
            photo.setThumbnailUrl(queueJobService.addThumbnailJob(blob.getUri().toString(), tripId, photo.getPhotoId()));
            photoRepository.save(photo);

            return blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public String updatePhoto(PhotoDTO dto, String tripId, Long photoId) {
//        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("photos");
//        CloudBlob blob;
//
//        try {
//            blob = cloudBlobContainer.getBlockBlobReference("photo"
//                    + tripId + "_"
//                    + photoId);
//            return blob.getUri().toString();
//        } catch (URISyntaxException | StorageException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void deletePhoto(String tripId, Long photoId) {
        log.info("PhotoService::deletePhoto");
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("photos");
        CloudBlob blob;

        String[] url = photoRepository.findOne(photoId).getUrl().split("[.]");
        String extension = url[url.length - 1];

        photoRepository.delete(photoId);

        try {
            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId + "_"
                    + photoId + "."
                    + extension);

            blob.delete();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
    }


}
