package kg.kasymaliev.questionnaire.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessRqDto {
    @ApiModelProperty("Дата начала прохождения опроса в формате \"dd.MM.yyyy HH:mm:ss\"")
    @JsonSerialize
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private String createDt;
    @ApiModelProperty("Дата окончания прохождения опроса в формате \"dd.MM.yyyy HH:mm:ss\"")
    @JsonSerialize
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private String endDt;
    @ApiModelProperty("Список ответов-вопросов пользователя")
    @NotNull
    private List<QuestionAnswerRqDto> questionAnswerRqDto;
}
