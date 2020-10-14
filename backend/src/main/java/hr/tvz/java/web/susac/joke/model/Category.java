package hr.tvz.java.web.susac.joke.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_table")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Joke> jokeList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "date_time_created", nullable = false)
    private LocalDateTime dateTimeCreated;

    @UpdateTimestamp
    @Column(name = "date_time_updated")
    private LocalDateTime dateTimeUpdated;
}
