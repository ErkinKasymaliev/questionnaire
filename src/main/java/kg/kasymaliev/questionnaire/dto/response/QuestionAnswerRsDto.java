package kg.kasymaliev.questionnaire.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@ApiModel
@Builder
@Data
public class QuestionAnswerRsDto {
    @ApiModelProperty("Вопрос")
    private String questionName;
    @ApiModelProperty("Ответы")
    private List<String> answers;
}
