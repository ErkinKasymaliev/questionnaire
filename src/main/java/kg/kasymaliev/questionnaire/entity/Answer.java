package kg.kasymaliev.questionnaire.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_generator")
    @SequenceGenerator(name="answers_generator", sequenceName = "answers_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String text;

    @Column(name = "order_num")
    private Integer orderNum;

    @JsonIgnore
    @OneToMany(mappedBy = "answer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Result> results;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "question_id")
    private Question question;
}
