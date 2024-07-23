package com.ejeek.back.image;

import com.ejeek.back.challenge.Challenge;
import com.ejeek.back.global.exception.CustomException;
import com.ejeek.back.global.exception.ExceptionCode;
import com.ejeek.back.global.fileupload.S3UploadService;
import com.ejeek.back.member.Member;
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

    public Image createImage(MultipartFile file, Object entity) {
        String filePath = upload(file);
        Image image = linkImageToEntity(entity, filePath);
        return imageRepository.save(image);
    }

    private Image linkImageToEntity(Object entity, String filePath) {
        Image image;
        if (entity instanceof Member) {
            ImageReference imageReference = new ImageReference(ImageReference.MappingType.MEMBER, ((Member) entity).getId());
            image = new Image(imageReference, filePath);
        } else if (entity instanceof Challenge) {
            ImageReference imageReference = new ImageReference(ImageReference.MappingType.CHALLENGE, ((Challenge) entity).getId());
            image = new Image(imageReference, filePath);
        } else {
            throw new CustomException(ExceptionCode.IMAGE_SAVE_FAIL);
        }
        return image;
    }

    private String upload(MultipartFile file) {
        String fileName = s3UploadService.saveUploadFile(file);
        log.info("[AWS S3] Save to file S3 complete");
        return s3UploadService.getFilePath(fileName);
    }
}
