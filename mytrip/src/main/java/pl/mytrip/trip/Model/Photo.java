package pl.mytrip.trip.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String url;

    @Column
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name = "waypoint_id")
    private Waypoint waypoint;

}
