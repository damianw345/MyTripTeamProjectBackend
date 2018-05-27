package pl.mytrip.trip.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mytrip.trip.Model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

}
