package hr.tvz.java.web.susac.joke.model;

import hr.tvz.java.web.susac.joke.enums.RatingTypeEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating_table")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RatingTypeEnum type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "joke_id", nullable = false)
    private Joke joke;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "date_time_created", nullable = false)
    private LocalDateTime dateTimeCreated;

    @UpdateTimestamp
    @Column(name = "date_time_updated")
    private LocalDateTime dateTimeUpdated;
}
