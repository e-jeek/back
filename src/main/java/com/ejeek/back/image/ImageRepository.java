package com.ejeek.back.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

//    List<Image> findByReference(ImageReference reference);

    List<Image> findByTypeAndRefId(ImageReference.MappingType type, Long refId);
}
