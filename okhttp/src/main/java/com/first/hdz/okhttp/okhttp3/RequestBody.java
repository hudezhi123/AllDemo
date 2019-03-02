package com.first.hdz.okhttp.okhttp3;

/**
 * created by hdz
 * on 2018/8/8
 */
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


import okhttp3.MediaType;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;


public abstract class RequestBody {
    /** Returns the Content-Type header for this body. */
    public abstract @Nullable MediaType contentType();

    /**
     * Returns the number of bytes that will be written to {@code sink} in a call to {@link #writeTo},
     * or -1 if that count is unknown.
     */
    public long contentLength() throws IOException {
        return -1;
    }

    /** Writes the content of this request to {@code sink}. */
    public abstract void writeTo(BufferedSink sink) throws IOException;

    /**
     * Returns a new request body that transmits {@code content}. If {@code contentType} is non-null
     * and lacks a charset, this will use UTF-8.
     */
    public static RequestBody create(@Nullable MediaType contentType, String content) {
        Charset charset = Util.UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
            }
        }
        byte[] bytes = content.getBytes(charset);
        return create(contentType, bytes);
    }

    /** Returns a new request body that transmits {@code content}. */
    public static RequestBody create(
            final @Nullable MediaType contentType, final ByteString content) {
        return new RequestBody() {
            @Override public @Nullable MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() throws IOException {
                return content.size();
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content);
            }
        };
    }

    /** Returns a new request body that transmits {@code content}. */
    public static RequestBody create(final @Nullable MediaType contentType, final byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    /** Returns a new request body that transmits {@code content}. */
    public static RequestBody create(final @Nullable MediaType contentType, final byte[] content,
                                     final int offset, final int byteCount) {
        if (content == null) throw new NullPointerException("content == null");
        Util.checkOffsetAndCount(content.length, offset, byteCount);
        return new RequestBody() {
            @Override public @Nullable MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return byteCount;
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, byteCount);
            }
        };
    }

    /** Returns a new request body that transmits the content of {@code file}. */
    public static RequestBody create(final @Nullable MediaType contentType, final File file) {
        if (file == null) throw new NullPointerException("content == null");

        return new RequestBody() {
            @Override public @Nullable MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return file.length();
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(file);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}
