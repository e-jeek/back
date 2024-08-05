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
        ImageReference reference = new ImageReference(entity.getImageMappingType(), entity.getRefId());
        Image image = new Image(reference, filePath);
        return imageRepository.save(image);
    }

    public Image updateImage(MultipartFile file, ImageReferable entity) {
        ImageReference reference = new ImageReference(entity.getImageMappingType(), entity.getRefId());
        List<Image> imageList = imageRepository.findByReference(reference);
        imageList.forEach(image -> s3UploadService.deleteFile(image.getUrl()));
        return createImage(file, entity);
    }

    private String upload(MultipartFile file) {
        String fileName = s3UploadService.saveUploadFile(file);
        log.info("[AWS S3] Save to file S3 complete");
        return s3UploadService.getFilePath(fileName);
    }
}
