package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.question.dto.QuestionRequest;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateQuestion;
import wepik.backend.module.template.dao.TemplateTag;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class TemplateRequest {

    @Schema(description = "템플릿 제목", example = "연인이랑 가고싶은 장소")
    private String title;

    @Schema(description = "DB에 저장되는 이미지 이름", example = "4df23447-2355-45h2-8783-7f6gd2ceb848_고양이.jpg")
    private String storedName;

    private List<String> tags;

    private List<QuestionRequest> questions;

    public Template toEntity(File file, List<File> files) {
        List<Question> questionEntities = getQuestions(files);
        List<TemplateQuestion> templateQuestions = questionEntities.stream()
                .map(question -> TemplateQuestion.builder()
                        .template(null) // Template 객체를 나중에 설정합니다.
                        .question(question)
                        .build())
                .collect(Collectors.toList());

        Template template = Template.builder()
                .title(title)
                .file(file)
                .templateTags(getTemplateTags())
                .templateQuestions(templateQuestions)
                .build();

        // Template 객체 설정
        templateQuestions.forEach(tq -> tq.setTemplate(template));

        return template;
    }

    private List<TemplateTag> getTemplateTags() {
        return tags.stream()
                .map(TemplateTagDto::toEntity)
                .collect(Collectors.toList());
    }

    private List<Question> getQuestions(List<File> files) {
        Map<String, File> fileMap = files.stream()
                .collect(Collectors.toMap(File::getStoredName, Function.identity()));
        return questions.stream()
                .map(question -> {
                    File questionFile = fileMap.get(question.getStoredName());
                    return question.toEntity(questionFile);
                })
                .collect(Collectors.toList());
    }
}
