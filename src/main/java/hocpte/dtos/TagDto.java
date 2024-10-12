package hocpte.dtos;

import hocpte.entities.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TagDto {
    private Long id;
    private String tags_name;
    public static TagDto toDto(TagEntity tagEntity){
        if (tagEntity == null) return null;
        return TagDto.builder()
                .id(tagEntity.getId())
                .tags_name(tagEntity.getTagName())
                .build();
    }
}
