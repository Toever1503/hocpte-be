package hocpte.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String category_name;
    public static CategoryDto toDto(CategoryDto categoryDto){
        if(categoryDto == null) return null;
        return CategoryDto.builder()
                .id(categoryDto.getId())
                .category_name(categoryDto.getCategory_name())
                .build();
    }
}
