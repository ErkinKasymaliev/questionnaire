package kg.kasymaliev.questionnaire.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@ApiModel
@Builder
@Data
public class ResultRsDto {
    @ApiModelProperty("Логин пользователя")
    private String userName;
    @ApiModelProperty("Название опроса")
    private String questionnaireName;
    @ApiModelProperty("Дата начала прохождения опроса в формате \"yyyy-MM-dd\"")
    private String createDt;
    @ApiModelProperty("Дата окончания прохождения опроса в формате \"yyyy-MM-dd\"")
    private String endDt;
    @ApiModelProperty("Список вопрос-ответов пользователя")
    private List<QuestionAnswerRsDto> answers;
}
