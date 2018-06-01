package pl.mytrip.trip.Model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @NonNull
    private String tripId;

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

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private Set<Waypoint> points;

    @Column(length = 1000)
    private String cachedMap;

    @Column(nullable = false)
    private String owner;
}
