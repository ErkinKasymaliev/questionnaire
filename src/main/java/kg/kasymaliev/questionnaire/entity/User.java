package kg.kasymaliev.questionnaire.entity;


import com.sun.istack.NotNull;
import kg.kasymaliev.questionnaire.model.UserType;
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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_seq")
    private Long id;

    @NotNull
    @Column
    private String login;
    
    @Column
    private String firstname;

    @Column
    private String surname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "created_date")
    private LocalDateTime createdDt;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TakingSurvey> takingSurveys;
}

