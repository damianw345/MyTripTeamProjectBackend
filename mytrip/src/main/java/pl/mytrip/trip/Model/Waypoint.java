package pl.mytrip.trip.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "waypoint")
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long waypointId;

    @Column(nullable = false)
    private Float longitude;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @OneToMany(mappedBy = "waypoint", cascade = CascadeType.ALL)
    private Set<Photo> photos;

    @OneToMany(mappedBy = "waypoint", cascade = CascadeType.ALL)
    private Set<Video> videos;
}
