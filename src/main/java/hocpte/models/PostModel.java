package hocpte.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hocpte.entities.PostEntity;
import hocpte.enums.EPostStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostModel {
    @ApiModelProperty(notes = "Post's ID", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "Post's Title", dataType = "String", example = "thần hình câu diệt")
    @NotBlank
    @NotNull
    private String title;

    @ApiModelProperty(notes = "Post's Content", dataType = "String", example = "This page is not found on bấy bì")
    @NotNull
    private String content;

    @ApiModelProperty(notes = "Post's Content-Excerpt", dataType = "String", example = "Hán Li, Lâm Động")
    @NotNull
    private String contentExcerpt;

    @ApiModelProperty(notes = "Post's Featured-Image", dataType = "String", example = "https://i.imgur.com/1Z1Z1Z1.jpg")
    private String featuredImage;

    @ApiModelProperty(notes = "Post's Status",dataType = "String",example = "cũ")
    @NotNull
    private EPostStatus status;

    @ApiModelProperty(notes = "Post's Created-Date ",dataType = "Data",example = "2022-09-19")
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date createdDate;

    @ApiModelProperty(notes = "Post's Modified-Date ",dataType = "Data",example = "2022-09-19")
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date modifiedDate;

    @ApiModelProperty(notes = "Category ID",dataType = "Long",example = "2")
    @NotNull
    private Long category;

    private List<String> tags;

    public static PostEntity toEntity(PostModel model){
        if(model == null) throw new RuntimeException("PostsModel is null!");
        return PostEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .content(model.getContent())
                .contentExcerpt(model.getContentExcerpt())
                .status(model.getStatus().name())
                .createdDate(model.getCreatedDate())
                .modifiedDate(model.getModifiedDate())
                .featuredImage(model.getFeaturedImage())
                .build();
    }
}
