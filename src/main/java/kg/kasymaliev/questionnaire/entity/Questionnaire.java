package kg.kasymaliev.questionnaire.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@Entity
@Table(name="questionnaires")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionnaire_generator")
    @SequenceGenerator(name="questionnaire_generator", sequenceName = "questionnaire_seq")
    private Long id;

    private String name;

    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "update_dt")
    private LocalDateTime updateDt;

    @Column(name = "delete_dt")
    private LocalDateTime deleteDt;

    @Column(name = "start_dt")
    private LocalDateTime startDt;

    @Column(name = "end_dt")
    private LocalDateTime endDt;

    @JsonIgnore
    @OneToMany(mappedBy = "questionnaire", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "questionnaire")
    private Set<TakingSurvey> takingSurveys;
}
