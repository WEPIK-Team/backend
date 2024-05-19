package wepik.backend.module.template.dao;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;

@Getter
@Entity
public class TemplateTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_tag_id", nullable = false)
    private Long templateTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Template template;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_tag_id")
    private Set<Tag> tag;
}
