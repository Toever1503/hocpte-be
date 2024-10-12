package hocpte.utils;

import hocpte.configs.BeanConfiguration;
import hocpte.dtos.MediaDto;
import hocpte.entities.MediaEntity;
import hocpte.resources.exception.CustomHandleException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Component
public class FileUploadProvider {


    public MediaEntity uploadFile(MultipartFile file) {

        Calendar calendar = Calendar.getInstance();
        StringBuilder datePath = new StringBuilder()
                .append(calendar.get(Calendar.YEAR))
                .append("/")
                .append(calendar.get(Calendar.MONTH) + 1)
                .append("/");

        return this.uploadFile(file, datePath.toString());

    }

    public MediaEntity uploadFile(MultipartFile file, String path) {
        MediaEntity mediaEntity = new MediaEntity();

        StringBuilder pathFile = new StringBuilder(BeanConfiguration.ROOT_CONTENT_SYS)
                .append(path);

        new File(pathFile.toString()).mkdirs();
        pathFile.append(file.getOriginalFilename());


        File checkFile = checkFileExist(pathFile.toString());
        int i = 0;
        while (checkFile == null) {
            pathFile.setLength(0);
            pathFile
                    .append(BeanConfiguration.ROOT_CONTENT_SYS)
                    .append(path)
                    .append(i++)
                    .append("-")
                    .append(file.getOriginalFilename());
            checkFile = checkFileExist(pathFile.toString());
        }

        try {
            file.transferTo(new File(pathFile.toString()));
        } catch (IOException e) {
            throw new CustomHandleException(1);
        }

        String domainPath = pathFile.replace(0, BeanConfiguration.ROOT_CONTENT_SYS.length(), BeanConfiguration.DOMAIN).toString();

        mediaEntity.setFileName(file.getOriginalFilename());
        mediaEntity.setFileType(file.getContentType());
        mediaEntity.setCreatedBy(SecurityUtils.getCurrentUser().getUser());
        mediaEntity.setUrl(domainPath);
        return mediaEntity;
    }

    private File checkFileExist(String path) {
        File file = new File(path);
        if (file.exists())
            return null;
        return file;
    }

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().get(Calendar.MONTH));
    }
}
