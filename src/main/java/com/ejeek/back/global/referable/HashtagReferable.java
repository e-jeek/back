package com.ejeek.back.global.referable;

import com.ejeek.back.hashtag.HashtagReference;

public interface HashtagReferable {

    HashtagReference.MappingType getHashtagMappingType();

    Long getRefId();
}
