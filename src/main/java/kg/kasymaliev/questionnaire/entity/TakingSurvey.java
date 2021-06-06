package kg.kasymaliev.questionnaire.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="taking_survey")
public class TakingSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taking_survey_generator")
    @SequenceGenerator(name="taking_survey_generator", sequenceName = "taking_survey_seq")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "end_dt")
    private LocalDateTime endDt;

    @OneToMany(mappedBy = "takingSurvey", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Result> results;
}
