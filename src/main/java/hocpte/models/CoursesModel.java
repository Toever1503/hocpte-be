package hocpte.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoursesModel {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String lectures;
    private String imgBanner;
    private String totalSession;
    
    private List<String> tags;

}
