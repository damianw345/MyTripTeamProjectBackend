package pl.mytrip.trip.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.Trip;
import pl.mytrip.trip.TripMapperImpl;
import pl.mytrip.trip.TripRepository;
import pl.mytrip.trip.dto.TripDTO;

import javax.ws.rs.NotFoundException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueueJobService {

    private final TripRepository tripRepository;
    private final TripMapperImpl tripMapper;

    public String addThumbnailJob() {
        return executePost("https://mediaservice-trzye.azurewebsites.net/mediaService/addThumbnailJob",
                "{\"fullImageStorageUrl\": \"\"}");
    }

    public TripDTO addPosterJob(Long tripId) {
        System.out.println("Reguch to koks");
        Trip trip = tripRepository.findOne(tripId);
//        TripDTO tripDTO = Optional.ofNullable(tripRepository.findOne(tripId))
//                .map(tripMapper::toDto)
//                .orElseThrow(NotFoundException::new);

        System.out.println(trip.toString());

        return null;
//        return executePost("https://mediaservice-trzye.azurewebsites.net/mediaService/addPosterJob",
//                "{\"fullImageStorageUrl\": \"\"}");
    }

    public String addVideoPresentationJob() {
//        return executePost("https://mediaservice-trzye.azurewebsites.net/mediaService/addThumbnailJob",
//                "{\"fullImageStorageUrl\": \"\"}");
        return null;
    }

    private static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection;

        try {
            String encoding = Base64.getEncoder().encodeToString(("serviceapp:serviceapp@123").getBytes());
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Authorization", "Basic " + encoding);

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
//            connection.setRequestProperty("Content-Language", "en-US");

//            connection.setUseCaches(false);
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
}
