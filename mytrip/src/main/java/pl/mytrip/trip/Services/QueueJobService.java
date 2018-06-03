package pl.mytrip.trip.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.DTOs.Jobs.PosterJobDTO;
import pl.mytrip.trip.DTOs.Jobs.ThumbnailJobDTO;
import pl.mytrip.trip.DTOs.Jobs.VideoPresentationDTO;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Repositories.PhotoRepository;
import pl.mytrip.trip.Repositories.TripRepository;

import javax.ws.rs.NotFoundException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueueJobService {

    private final TripRepository tripRepository;
    private final PhotoRepository photoRepository;
    private static final String JOB_API = "https://tripsbackendprocessingapi.azurewebsites.net";
    private static final String CALLBACK_URL = "http://104.41.220.226:8080/api/trips";

    public String addThumbnailJob(String photoUrl, String tripId, Long photoId) throws JsonProcessingException {
        log.info("QueueJobService::addThumbnailJob");
        ThumbnailJobDTO dto = new ThumbnailJobDTO();
        dto.setCallbackUrl(CALLBACK_URL + "/" + tripId + "/photos/" + photoId + "/thumbnail");
        dto.setFullImageStorageUrl(photoUrl);

        return executePost(JOB_API + "/mediaService/addThumbnailJob",
                new ObjectMapper().writeValueAsString(dto));
    }

    public String addPosterJob(String tripId) throws JsonProcessingException {
        Set<String> photoUrls = new HashSet<>();

        tripRepository.findOne(tripId).getPoints().forEach(waypoint -> waypoint.getPhotos().forEach(photo -> {
            photoUrls.add(photo.getUrl());
        }));

        PosterJobDTO dto = new PosterJobDTO();
        dto.setPhotos(photoUrls);
        dto.setCallbackUrl(CALLBACK_URL + "/" + tripId + "/posterCreated");

        return executePost(JOB_API + "/mediaService/addPosterJob",
                new ObjectMapper().writeValueAsString(dto));
    }

    public String addVideoPresentationJob(String tripId) throws JsonProcessingException {
        Set<String> photoUrls = new HashSet<>();

        tripRepository.findOne(tripId).getPoints().forEach(waypoint -> waypoint.getPhotos().forEach(photo -> {
            photoUrls.add(photo.getUrl());
        }));

        VideoPresentationDTO dto = new VideoPresentationDTO();
        dto.setPhotos(photoUrls);
        dto.setCallbackUrl(CALLBACK_URL + "/" + tripId + "/presentationCreated");

        return executePost(JOB_API + "/mediaService/addVideoPresentationJob",
                new ObjectMapper().writeValueAsString(dto));
    }

    private static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection;

        try {
            String encoding = Base64.getEncoder().encodeToString(("serviceapp:e&GbE]5as-k~hspC").getBytes());
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPosterUrl(String tripId, String posterUrl) {
        Trip trip = Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new);
        trip.setPoster(posterUrl);
        tripRepository.save(trip);
    }

    public void addVideoPresentationUrl(String tripId, String videoPresentationUrl) {
        Trip trip = Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new);
        trip.setPresentation(videoPresentationUrl);
        tripRepository.save(trip);
    }

    public void addThumbnailUrl(Long photoId, String thumbnailUrl) {
        Photo photo = Optional.ofNullable(photoRepository.findOne(photoId))
                .orElseThrow(NotFoundException::new);
        photo.setThumbnailUrl(thumbnailUrl);
        photoRepository.save(photo);
    }

    public String getPoster(String tripId) {
        return Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new).getPoster();
    }

    public String getVideoPresentation(String tripId) {
        return Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new).getPresentation();
    }
}
