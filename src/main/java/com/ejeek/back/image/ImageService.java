package com.ejeek.back.image;

import com.ejeek.back.global.fileupload.S3UploadService;
import com.ejeek.back.global.referable.ImageReferable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final S3UploadService s3UploadService;
    private final ImageRepository imageRepository;

    public Image createImage(MultipartFile file, ImageReferable entity) {
        String filePath = upload(file);
        Image image = new Image(entity.getImageMappingType(), entity.getRefId(), filePath);
        return imageRepository.save(image);
    }

    public Image updateImage(MultipartFile file, ImageReferable entity) {
        List<Image> imageList = imageRepository.findByTypeAndRefId(entity.getImageMappingType(), entity.getRefId());
        imageList.forEach(image -> s3UploadService.deleteFile(image.getUrl()));
        return createImage(file, entity);
    }

    private String upload(MultipartFile file) {
        String fileName = s3UploadService.saveUploadFile(file);
        log.info("[AWS S3] Save to file S3 complete");
        return s3UploadService.getFilePath(fileName);
    }
}
