package com.ejeek.back.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfo {

    private int page;
    private int size;
    private boolean first;
    private boolean last;
}
