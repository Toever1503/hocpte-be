package hocpte.services;

import hocpte.dtos.PostDto;
import hocpte.entities.PostEntity;
import hocpte.models.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService extends IBaseService<PostEntity, PostDto, PostModel,Long>{
    PostEntity findPostEntityByTitle(String title);

    Page<PostDto> search(String q, Pageable page);

    List<String> getAllPostDate();

    PostDto[] findNextPrevPosts(Long postId, Long categoryId);
}
