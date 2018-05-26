package pl.mytrip.trip.Services;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.PhotoRepository;
import pl.mytrip.trip.StorageConnector;
import pl.mytrip.trip.dto.PhotoDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

@Data
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PhotoService {

    private final StorageConnector storageConnector;
    private final PhotoRepository photoRepository;
    private final QueueJobService queueJobService;

    public String addPhoto(Long tripId, PhotoDTO dto, byte[] photoBytes) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer();
        CloudBlob blob;
        try {


            Photo photo = new Photo();
            photo.setPhotoId((long) 1);
            photo.setDate(new Date());
            photo.setUrl("url");
            photo.setThumbnailUrl("thumbnail");
            photo.setWaypointId((long) 1);
            photoRepository.save(photo);

            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId.toString() + "_"
                    + photo.getPhotoId());

            blob.uploadFromByteArray(photoBytes, 0, photoBytes.length);
            String thumbnailUrl = queueJobService.addThumbnailJob(blob.getUri().toString());

//            for (ListBlobItem blobItem : cloudBlobContainer.listBlobs()) {
//
//            }

            return blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String updatePhoto(PhotoDTO dto, Long tripId, Long photoId) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer();
        CloudBlob blob;

        try {
            blob = cloudBlobContainer.getBlockBlobReference("photo"
                    + tripId.toString() + "_"
                    + photoId);
            return blob.getUri().toString();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePhoto(Long tripId, Long photoId) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer();
        CloudBlob blob;

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
