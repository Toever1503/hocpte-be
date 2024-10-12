package hocpte.models;

import hocpte.entities.TagEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagModel {
    @ApiModelProperty(notes = "Tag ID", dataType = "Long", example = "1")
    private Long id;
    @ApiModelProperty(notes = "Tag tags_name", dataType = "String", example = "abc")
    @NotNull
    private String tags_name;
    public static TagEntity toEntity(TagModel model){
        if (model == null) throw new RuntimeException("TagModel is null");
        return TagEntity.builder()
                .id(model.getId())
                .tagName(model.getTags_name())
                .build();
    }
}
