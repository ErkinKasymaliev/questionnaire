package kg.kasymaliev.questionnaire.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ApiModel
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireDtoRq {
    @ApiModelProperty("Название опроса")
    private String name;
    @ApiModelProperty("Описание опроса")
    private String description;
    @ApiModelProperty("Активный/не активный")
    private boolean isActive;
    @ApiModelProperty("Дата начала опроса в формате \"dd.MM.yyyy HH:mm:ss\"")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private String startDt;
    @ApiModelProperty("Дата конца опроса в формате \"dd.MM.yyyy HH:mm:ss\"")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private String endDt;
}
