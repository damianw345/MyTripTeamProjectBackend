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
import pl.mytrip.trip.DTOs.VideoDTO;
import pl.mytrip.trip.Mappers.PhotoMapper;
import pl.mytrip.trip.Mappers.VideoMapper;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Video;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.Repositories.PhotoRepository;
import pl.mytrip.trip.Repositories.TripRepository;
import pl.mytrip.trip.Repositories.VideoRepository;
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
public class VideoService {

    private final StorageConnector storageConnector;
    private final VideoRepository videoRepository;
    private final QueueJobService queueJobService;
    private final TripRepository tripRepository;
    private final WaypointRepository waypointRepository;
    private final VideoMapper videoMapper;

    public String addVideo(String tripId, byte[] videoBytes, VideoDTO videoDTO, String extension) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("videos");
        CloudBlob blob;
        try {
            Waypoint waypoint = Optional.ofNullable(waypointRepository.findOne(videoDTO.getWaypointId()))
                    .orElseThrow(NotFoundException::new);
            Video video = Optional.ofNullable(videoDTO)
                    .map(videoMapper::toEntity)
                    .map(entity -> { entity.setWaypoint(waypoint); return entity; })
                    .map(videoRepository::save).orElseThrow(NotFoundException::new);

            blob = cloudBlobContainer.getBlockBlobReference("video"
                    + tripId + "_"
                    + video.getVideoId() + "."
                    + extension);
            blob.uploadFromByteArray(videoBytes, 0, videoBytes.length);

            video.setUrl(blob.getUri().toString());
            videoRepository.save(video);

            return blob.getUri().toString();
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteVideo(String tripId, Long videoId) {
        CloudBlobContainer cloudBlobContainer = storageConnector.getStorageContainer("videos");
        CloudBlob blob;
        String[] url = videoRepository.findOne(videoId).getUrl().split("[.]");
        String extension = url[url.length - 1];
        videoRepository.delete(videoId);

        try {
            blob = cloudBlobContainer.getBlockBlobReference("video"
                    + tripId + "_"
                    + videoId + "."
                    + extension);

            blob.delete();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
    }


}
