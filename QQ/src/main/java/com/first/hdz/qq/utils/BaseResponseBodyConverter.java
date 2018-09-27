package com.first.hdz.qq.utils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * created by hdz
 * on 2018/9/25
 */
public abstract class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    @Override
    public abstract T convert(ResponseBody value) throws IOException;

}
