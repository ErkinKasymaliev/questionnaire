package kg.kasymaliev.questionnaire.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "results_generator")
    @SequenceGenerator(name="results_generator", sequenceName = "results_seq")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "taking_survey_id")
    private TakingSurvey takingSurvey;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
