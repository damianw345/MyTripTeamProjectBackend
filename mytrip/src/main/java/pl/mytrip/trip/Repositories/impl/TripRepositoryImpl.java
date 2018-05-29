package pl.mytrip.trip.Repositories.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.Repositories.TripRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Transactional
public class TripRepositoryImpl implements TripRepositoryCustom {

//    @Autowired
//    TripRepository tripRepository;  /* Optional - if you need it */

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Trip saveTrip(Trip trip) {

        Trip savedTrip = entityManager.merge(trip);

        if(Objects.nonNull(trip.getPoints())) {
            //set points without generated IDs
            savedTrip.setPoints(trip.getPoints());
            Set<Waypoint> savedWaypoints = new HashSet<>();
            for (Waypoint waypoint : savedTrip.getPoints()) {
                waypoint.setTrip(savedTrip);
                //IDs are generating
                savedWaypoints.add(entityManager.merge(waypoint));
            }
            //set points with generated IDs
            savedTrip.setPoints(savedWaypoints);
        }
        return savedTrip;
    }
}
