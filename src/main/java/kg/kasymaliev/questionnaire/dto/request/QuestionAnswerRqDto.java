package kg.kasymaliev.questionnaire.dto.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerRqDto {
    @ApiModelProperty("Id вопроса")
    private Long questionId;
    @ApiModelProperty("Id ответа")
    private Long answerId;
    @ApiModelProperty("Текстовый ответ")
    private String textAnswer;
}
