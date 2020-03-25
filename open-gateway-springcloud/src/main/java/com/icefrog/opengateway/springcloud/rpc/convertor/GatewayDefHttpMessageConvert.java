/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc.convertor;

import com.icefrog.opengateway.springcloud.core.Response;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author IceFrog
 */
public class GatewayDefHttpMessageConvert implements HttpMessageConverter<Response> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.MULTIPART_FORM_DATA);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.ALL);
        return mediaTypes;
    }

    @Override
    public Response read(Class<? extends Response> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String body = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
        System.out.println("body:~~" + body);
        return null;
    }

    @Override
    public void write(Response response, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("write:~~");
    }
}
