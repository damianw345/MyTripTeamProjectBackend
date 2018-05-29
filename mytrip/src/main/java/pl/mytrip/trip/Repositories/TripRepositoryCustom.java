package pl.mytrip.trip.Repositories;

import org.springframework.stereotype.Repository;
import pl.mytrip.trip.Model.Trip;

@Repository
public interface TripRepositoryCustom {
    Trip saveTrip(Trip trip);
}
