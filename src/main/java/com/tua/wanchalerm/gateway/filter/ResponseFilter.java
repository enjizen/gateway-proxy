package com.tua.wanchalerm.gateway.filter;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tua.wanchalerm.gateway.service.AESService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class ResponseFilter extends ZuulFilter {

    @Autowired
    private AESService aesService;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        val aesKey = ctx.getRequest().getHeader("message");
        val responseDataStream = ctx.getResponseDataStream();
        String response = CharStreams.toString(new InputStreamReader(responseDataStream, StandardCharsets.UTF_8));
        String responseEncrypt = aesService.encrypt(response, aesKey);
        InputStream stream = new ByteArrayInputStream(responseEncrypt.getBytes(StandardCharsets.UTF_8));
        ctx.setResponseDataStream(stream);
        return null;
    }
}
