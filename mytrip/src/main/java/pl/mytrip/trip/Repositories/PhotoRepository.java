package pl.mytrip.trip.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mytrip.trip.Model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
