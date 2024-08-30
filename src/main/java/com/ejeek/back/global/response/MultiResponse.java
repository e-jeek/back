package com.ejeek.back.global.response;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class MultiResponse<T> {

    private final List<T> data;
    private final PageInfo pageInfo;

    public MultiResponse(Slice<T> slice) {
        this.data = slice.getContent();
        this.pageInfo = new PageInfo(slice.getNumber() + 1, slice.getSize(), slice.isFirst(), slice.isLast());
    }
}
