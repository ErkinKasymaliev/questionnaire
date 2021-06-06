package kg.kasymaliev.questionnaire.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRqDto {
    @ApiModelProperty("Текст вопроса")
    private String text;
    @ApiModelProperty("Является ли вопрос текстовым")
    private boolean isTextAnswer;
    @ApiModelProperty("Список возможных ответов")
    private Set<AnswerRqDto> answers;
}
