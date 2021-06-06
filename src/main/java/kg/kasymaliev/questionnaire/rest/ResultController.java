package kg.kasymaliev.questionnaire.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kg.kasymaliev.questionnaire.dto.response.ResultRsDto;
import kg.kasymaliev.questionnaire.entity.Result;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.mapper.ResultMapper;
import kg.kasymaliev.questionnaire.service.ResultService;
import kg.kasymaliev.questionnaire.service.TakingSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.HttpURLConnection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/result")
@Api(tags = "Результаты опросов")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;
    private final TakingSurveyService takingSurveyService;

    private final ResultMapper resultMapper;

    @GetMapping("/takingSurvey/{takingSurveyId}")
    @ApiOperation("Запрос результатов по id прохождения опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResultRsDto> getByTakingSurvey(@PathVariable Long takingSurveyId) {
        TakingSurvey takingSurvey = takingSurveyService.getById(takingSurveyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Taking survey id " + takingSurveyId + "is not found"));
        List<Result> list = resultService.getResultsByTakingSurvey(takingSurvey);

        return new ResponseEntity<>(resultMapper.resultsToDto(list), HttpStatus.OK);
    }

}
