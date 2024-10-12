package hocpte.services;

import hocpte.dtos.MediaDto;
import hocpte.entities.MediaEntity;
import hocpte.models.MediaModel;

import java.util.List;

public interface IMediaService extends IBaseService<MediaEntity, MediaDto, MediaModel, Long> {
    List<String> getAllMediaType();

    List<String> getAllMediaDate();
}
