package pl.mytrip.trip.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "waypoint_id")
    private Waypoint waypoint;

}
