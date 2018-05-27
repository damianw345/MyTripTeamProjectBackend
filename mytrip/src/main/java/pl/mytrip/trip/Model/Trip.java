package pl.mytrip.trip.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mytrip.trip.Model.Waypoint;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Date start;

    @Column(nullable = false)
    private Date end;

    @Column(length = 1000)
    private String poster;

    @Column(length = 1000)
    private String presentation;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private Set<Waypoint> points;

    @Column(length = 1000)
    private String cachedMap;

    @Column(nullable = false)
    private String owner;
}
