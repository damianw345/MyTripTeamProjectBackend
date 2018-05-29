package pl.mytrip.trip.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mytrip.trip.Model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, String>, TripRepositoryCustom {

    Page<Trip> findByOwner(String owner, Pageable pageable);
}
