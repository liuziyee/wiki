package com.dorohedoro.wiki.mapstruct;

public interface BaseMapper<D, V> {

    V dto2VO(D dto);
}
