package hocpte.dtos;

import hocpte.entities.CategoryEntity;
import hocpte.entities.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    private String title;

    private String content;

    private String contentExcerpt;


    private Integer commentCount;

    private String status;

    private UserMetaDto createdBy;

    private UserMetaDto modifiedBy;

    private Date createdDate;

    private Date modifiedDate;

    private String featuredImage;

    private CategoryEntity category;

    public static PostDto toDto(PostEntity postEntity){
        if(postEntity == null) return null;
        return PostDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .contentExcerpt(postEntity.getContentExcerpt())
                .commentCount(postEntity.getCommentCount())
                .status(postEntity.getStatus())
                .createdBy(UserMetaDto.toDto(postEntity.getCreatedBy()))
                .modifiedBy(UserMetaDto.toDto(postEntity.getModifiedBy()))
                .createdDate(postEntity.getCreatedDate())
                .modifiedDate(postEntity.getModifiedDate())
                .featuredImage(postEntity.getFeaturedImage())
                .category(postEntity.getCategory())
                .build();
    }
}
