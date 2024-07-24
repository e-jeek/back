package com.ejeek.back.global.referable;

import com.ejeek.back.image.ImageReference;

public interface ImageReferable {

    ImageReference.MappingType getImageMappingType();

    Long getRefId();
}
