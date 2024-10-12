package hocpte.services;


import hocpte.dtos.TagDto;
import hocpte.dtos.UserProfileDto;
import hocpte.entities.TagEntity;
import hocpte.entities.UserEntity;
import hocpte.models.TagModel;
import hocpte.models.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ITagService  {
    public TagEntity create(TagEntity tagEntity);
    public TagEntity update(TagEntity tagEntity);
    public TagEntity delete(TagEntity tagEntity);
    public TagEntity findById(Long id);
    List<TagEntity> findAll();

    List<String> searchTag(String q);
}
