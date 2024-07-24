package com.ejeek.back.image;

import com.ejeek.back.global.fileupload.S3UploadService;
import com.ejeek.back.global.referable.ImageReferable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final S3UploadService s3UploadService;
    private final ImageRepository imageRepository;

    public Image createImage(MultipartFile file, ImageReferable entity) {
        String filePath = upload(file);
        ImageReference imageReference = new ImageReference(entity.getImageMappingType(), entity.getRefId());
        Image image = new Image(imageReference, filePath);
        return imageRepository.save(image);
    }

    private String upload(MultipartFile file) {
        String fileName = s3UploadService.saveUploadFile(file);
        log.info("[AWS S3] Save to file S3 complete");
        return s3UploadService.getFilePath(fileName);
    }
}
