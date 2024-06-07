package wepik.backend.module.template.dao;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_tag_id", nullable = false)
    private Long templateTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void setTemplate(Template template) {
        this.template = template;
    }
}
