package pl.mytrip.trip.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
