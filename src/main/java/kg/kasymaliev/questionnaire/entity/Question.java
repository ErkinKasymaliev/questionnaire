package kg.kasymaliev.questionnaire.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@Entity
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name="question_generator", sequenceName = "question_seq")
    private Long id;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Result> results;

    private String text;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Answer> answers;

    @Column(name = "is_text_answer")
    private Boolean isTextAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

}
