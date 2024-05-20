package wepik.backend.module.template.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wepik.backend.module.template.application.QuestionService;
import wepik.backend.module.template.dao.Question;
import wepik.backend.module.template.dto.QuestionRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("questions")
public class QuestionController {

    private final QuestionService questionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createQuestion(@RequestBody QuestionRequest questionRequest) {
        questionService.save(questionRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Question findQuestion(@PathVariable Long id) {
        return questionService.findQuestion(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
    }
}
