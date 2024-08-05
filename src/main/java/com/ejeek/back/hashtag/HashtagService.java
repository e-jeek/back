package com.ejeek.back.hashtag;

import com.ejeek.back.global.referable.HashtagReferable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagService {

    public List<Hashtag> createHashtags(List<String> tagNames, HashtagReferable entity) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (String tagName : tagNames) {
            Hashtag hashtag = createHashtag(tagName, entity);
            hashtags.add(hashtag);
        }
        return hashtags;
    }

    private Hashtag createHashtag(String tagName, HashtagReferable entity) {
        HashtagReference hashtagReference =
                        new HashtagReference(entity.getHashtagMappingType(), entity.getRefId(), tagName);
        return new Hashtag(hashtagReference);
    }
}
