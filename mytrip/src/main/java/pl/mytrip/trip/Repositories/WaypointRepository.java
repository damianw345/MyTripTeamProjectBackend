package pl.mytrip.trip.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mytrip.trip.Model.Waypoint;

@Repository
public interface WaypointRepository extends JpaRepository<Waypoint, Long> {

}
