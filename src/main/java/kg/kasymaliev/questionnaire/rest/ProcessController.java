package kg.kasymaliev.questionnaire.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kg.kasymaliev.questionnaire.dto.request.ProcessRqDto;
import kg.kasymaliev.questionnaire.entity.Result;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.entity.User;
import kg.kasymaliev.questionnaire.model.UserType;
import kg.kasymaliev.questionnaire.service.*;
import kg.kasymaliev.questionnaire.util.DateUtil;
import kg.kasymaliev.questionnaire.util.SecurityContextHelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/api/v1/process")
@Api(tags = "Процесс прохождения опроса")
@RequiredArgsConstructor
public class ProcessController {
    private final TakingSurveyService takingSurveyService;
    private final UserService userService;
    private final QuestionnaireService questionnaireService;
    private final ResultService resultService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping
    @ApiOperation("Создание процесса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> createProcess(@RequestParam Long questionnaireId,
                                           @RequestBody ProcessRqDto processRqDto) {

        String user = SecurityContextHelperUtil.getUsername();
        TakingSurvey takingSurvey = TakingSurvey.builder()
                .createdDt(DateUtil.stringToLocalDateTime(processRqDto.getCreateDt()))
                .endDt(DateUtil.stringToLocalDateTime(processRqDto.getEndDt()))
                .user(userService.getUserByLogin(user).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + user + " not found")
                ))
                .questionnaire(questionnaireService.getQuestionnaireById(questionnaireId).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questionnaire id = " +
                                questionnaireId + " is not found")
                ))
                .build();
        takingSurvey = takingSurveyService.addTakingSurvey(takingSurvey);
        TakingSurvey finalTakingSurvey = takingSurvey;
        processRqDto.getQuestionAnswerRqDto().forEach(p -> {
            Result result = Result.builder()
                    .answer(answerService.getAnswer(p.getAnswerId()).orElse(null))
                    .question(questionService.getQuestion(p.getQuestionId()).orElse(null))
                    .takingSurvey(finalTakingSurvey)
                    .build();
            resultService.addResult(result);
        });



        return new ResponseEntity<>(HttpStatus.OK);
    }
}
