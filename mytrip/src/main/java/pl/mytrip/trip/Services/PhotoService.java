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
import pl.mytrip.trip.DTOs.PhotoDTO;
import pl.mytrip.trip.DTOs.PhotoInfoDTO;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Repositories.PhotoRepository;
import pl.mytrip.trip.Repositories.TripRepository;
import pl.mytrip.trip.StorageConnector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

@Slf4j
@Data
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PhotoService {

    private final StorageConnector storageConnector;
    private final PhotoRepository photoRepository;
    private final QueueJobService queueJobService;
    private final TripRepository tripRepository;

    public String addPhoto(String tripId, byte[] photoBytes, PhotoInfoDTO photoInfoDTO) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("photos");
        CloudBlob blob;
        try {
            Photo photo = new Photo();
            photo.setDate(new Date());
            photo.setWaypointId(photoInfoDTO.getWaypointId());
            photo.setUrl("url");
            photoRepository.save(photo);

            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId + "_"
                    + photo.getPhotoId());

            blob.uploadFromByteArray(photoBytes, 0, photoBytes.length);
            String thumbnailUrl = queueJobService.addThumbnailJob(blob.getUri().toString(), tripId, photo.getPhotoId());

            photo.setUrl(blob.getUri().toString());
            photo.setThumbnailUrl(thumbnailUrl);
            photoRepository.save(photo);

            for (ListBlobItem blobItem : cloudBlobContainer.listBlobs()) {
                log.info(blobItem.getUri().toString());
            }

            return blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String updatePhoto(PhotoDTO dto, String tripId, Long photoId) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("photos");
        CloudBlob blob;

        try {
            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId + "_"
                    + photoId);
            return blob.getUri().toString();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePhoto(String tripId, Long photoId) {
        log.info("PhotoService::deletePhoto");
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("photos");
        CloudBlob blob;

        photoRepository.delete(photoId);

        try {
            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId.toString() + "_"
                    + photoId);

            blob.delete();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
    }


}
