package com.ejeek.back.global.referable;

import com.ejeek.back.image.Image;

public interface ImageReferable {

    Image.MappingType getImageMappingType();

    Long getRefId();
}
