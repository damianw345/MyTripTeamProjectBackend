package pl.mytrip.trip.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mytrip.trip.Model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
