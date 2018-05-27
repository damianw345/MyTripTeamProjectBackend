package pl.mytrip.trip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TripRepository extends JpaRepository<Trip, Long> {

    Page<Trip> findByOwner(String owner, Pageable pageable);
}
