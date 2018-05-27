package pl.mytrip.trip.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "waypoint")
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waypointId;

    @Column(nullable = false, columnDefinition="Decimal(11,8)")
    private Float longitude;

    @Column(nullable = false, columnDefinition="Decimal(10,8)")
    private Float latitude;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}
