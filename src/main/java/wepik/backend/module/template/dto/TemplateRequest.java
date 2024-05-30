package wepik.backend.module.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wepik.backend.module.file.dao.File;
import wepik.backend.module.question.dao.Question;
import wepik.backend.module.template.dao.Template;
import wepik.backend.module.template.dao.TemplateQuestion;
import wepik.backend.module.template.dao.TemplateTag;
import java.util.List;
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

    private List<Long> questionIds;

    public Template toEntity(File file, List<Question> questions) {
        Template template = Template.builder()
                .title(title)
                .file(file)
                .build();

        List<TemplateTag> tags = getTemplateTags();
        for (TemplateTag tag : tags) {
            template.addTemplateTag(tag);
        }

        for (Question question : questions) {
            template.addTemplateQuestion(TemplateQuestion.builder()
                    .question(question)
                    .template(template)
                    .build()
            );
        }
        return template;
    }
    private List<TemplateTag> getTemplateTags() {
        return tags.stream()
                .map(TemplateTagDto::toEntity)
                .collect(Collectors.toList());
    }
}
