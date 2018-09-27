package com.first.hdz.qq.utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * created by hdz
 * on 2018/9/25
 */
public abstract class CustomResponseBodyConverter<T> extends BaseResponseBodyConverter<T> {


    CustomResponseBodyConverter() {

    }

    @Override
    public abstract T convert(ResponseBody value) throws IOException;
}
