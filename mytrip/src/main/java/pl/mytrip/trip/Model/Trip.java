package pl.mytrip.trip.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Entity(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    @Getter
    private Long tripId;

    @Getter
    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Getter
    private String description;

    @Column(nullable = false)
    @Getter
    private Date start;

    @Column(nullable = false)
    @Getter
    private Date end;

    @Column(length = 1000)
    @Getter
    private String poster;

    @Column(length = 1000)
    @Getter
    private String presentation;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @Getter
    private Set<Waypoint> points;

    @Column(length = 1000)
    @Getter
    private String cachedMap;

    @Column(nullable = false)
    @Getter
    private String owner;
}
