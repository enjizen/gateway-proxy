package com.tua.wanchalerm.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tua.wanchalerm.gateway.model.response.GeneralResponse;
import com.tua.wanchalerm.gateway.service.AESService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
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
        val ctx = RequestContext.getCurrentContext();
        Object e = ctx.get("throwable");
        if (e instanceof ZuulException) {
            GeneralResponse<Object> generalResponse = new GeneralResponse<>();
            generalResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            ctx.remove("throwable");
            ctx.setResponseBody(new ObjectMapper().writeValueAsString(generalResponse));
            ctx.getResponse().setContentType("application/json");
            ctx.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            val aesKey = ctx.getRequest().getHeader("message");
            val responseDataStream = ctx.getResponseDataStream();
            String response = CharStreams.toString(new InputStreamReader(responseDataStream, StandardCharsets.UTF_8));
            log.info("Body {}", response);
            String responseEncrypt = aesService.encrypt(response, aesKey);
            InputStream stream = new ByteArrayInputStream(responseEncrypt.getBytes(StandardCharsets.UTF_8));
            ctx.setResponseDataStream(stream);
        }

      //  log.info(new ObjectMapper().writeValueAsString(ctx.get("throwable")));
       /* RequestContext ctx = RequestContext.getCurrentContext();
        Object e = ctx.get("error.exception");
        log.info("ooooo = {}",e.toString());

        val aesKey = ctx.getRequest().getHeader("message");
        val responseDataStream = ctx.getResponseDataStream();
        String response = CharStreams.toString(new InputStreamReader(responseDataStream, StandardCharsets.UTF_8));
        String responseEncrypt = aesService.encrypt(response, aesKey);
        InputStream stream = new ByteArrayInputStream(responseEncrypt.getBytes(StandardCharsets.UTF_8));
        ctx.setResponseDataStream(stream);*/
        return null;
    }
}
