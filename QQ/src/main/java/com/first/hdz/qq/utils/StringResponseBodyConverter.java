package com.first.hdz.qq.utils;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * created by hdz
 * on 2018/9/30
 */
public abstract class StringResponseBodyConverter<T> extends BaseResponseBodyConverter<T> {

    @Override
    public abstract T convert(ResponseBody value) throws IOException;
}
