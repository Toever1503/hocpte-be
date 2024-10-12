package hocpte.models;

import hocpte.entities.CategoryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryModel {
    @ApiModelProperty(notes = "Category ID", dataType = "Long", example = "1")
    private Long id;
    @ApiModelProperty(notes = "Category category-name", dataType = "String", example = "Khóa học TOEIC")
    @NotNull
    private String category_name;
    public static CategoryEntity toEntity(CategoryModel model){
        if(model == null) throw new RuntimeException("CategoryModel is null");
        return CategoryEntity.builder()
                .id(model.getId())
                .categoryName(model.getCategory_name())
                .build();
    }

}
