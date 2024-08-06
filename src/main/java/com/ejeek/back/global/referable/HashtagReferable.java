package com.ejeek.back.global.referable;

import com.ejeek.back.hashtag.Hashtag;

public interface HashtagReferable {

    Hashtag.MappingType getHashtagMappingType();

    Long getRefId();
}
