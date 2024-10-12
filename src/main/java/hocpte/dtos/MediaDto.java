package hocpte.dtos;

import hocpte.entities.MediaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MediaDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String url;

    public static MediaDto toDto(MediaEntity mediaEntity) {
        if (mediaEntity == null)
            return null;
        return MediaDto.builder()
                .id(mediaEntity.getId())
                .fileName(mediaEntity.getFileName())
                .fileType(mediaEntity.getFileType())
                .url(mediaEntity.getUrl())
                .build();

    }
}
