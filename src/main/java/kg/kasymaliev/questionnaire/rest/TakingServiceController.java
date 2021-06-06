package kg.kasymaliev.questionnaire.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kg.kasymaliev.questionnaire.entity.Questionnaire;
import kg.kasymaliev.questionnaire.entity.TakingSurvey;
import kg.kasymaliev.questionnaire.entity.User;
import kg.kasymaliev.questionnaire.service.QuestionnaireService;
import kg.kasymaliev.questionnaire.service.TakingSurveyService;
import kg.kasymaliev.questionnaire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.HttpURLConnection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/taking-survey")
@Api(tags = "Прохождение опросов")
@RequiredArgsConstructor
public class TakingServiceController {
    private final TakingSurveyService takingSurveyService;
    private final UserService userService;
    private final QuestionnaireService questionnaireService;

    @GetMapping("/{id}")
    @ApiOperation("Запрос результатов по id прохождения опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(takingSurveyService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Taking survey id = " +
                        id + " is not found")),
                HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Запрос всех прохождений опросов")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getByAll() {
        List<TakingSurvey> list = takingSurveyService.getAllTakingSurveys();
        if(list==null||list.isEmpty())
            return new ResponseEntity<>("There is not any taking surveys", HttpStatus.NOT_FOUND);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("Запрос прохождений опросов по id пользователя")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User id = " + userId + " is not found"));
        List<TakingSurvey> list = takingSurveyService.getByUser(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/questionnaire/{questionnaireId}")
    @ApiOperation("Запрос прохождений по id опроса")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "NOT FOUND"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "ACCESS DENIED")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getByQuestionnaire(@PathVariable Long questionnaireId) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireById(questionnaireId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questionnaire id = " +
                        questionnaireId + " is not found"));
        List<TakingSurvey> list = takingSurveyService.getByQuestionnaire(questionnaire);

        if(list.isEmpty()||list==null) {
            return new ResponseEntity<>("There is not any taking surveys", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
