package com.tua.wanchalerm.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.StringWriter;

@Slf4j
public class RequestFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        val ctx = RequestContext.getCurrentContext();
        val request = ctx.getRequest();

        if (RequestMethod.valueOf(request.getMethod()) == RequestMethod.POST
                || RequestMethod.valueOf(request.getMethod()) == RequestMethod.PUT
                || RequestMethod.valueOf(request.getMethod()) == RequestMethod.PATCH) {

            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(request.getInputStream(), writer, request.getCharacterEncoding());
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.info("Body [{}]",  writer.toString());
        }

        log.info(String.format("%s request %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }
}
