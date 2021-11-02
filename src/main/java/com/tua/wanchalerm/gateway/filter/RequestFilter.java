package com.tua.wanchalerm.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.tua.wanchalerm.gateway.model.document.EndpointDocument;
import com.tua.wanchalerm.gateway.service.AESService;
import com.tua.wanchalerm.gateway.service.EndpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.StringWriter;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public class RequestFilter extends ZuulFilter {

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private AESService aesService;


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
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
        val aesKey = request.getHeader("message");

        log.info("Method {}", request.getMethod());

        if (RequestMethod.valueOf(request.getMethod()) == RequestMethod.POST
                || RequestMethod.valueOf(request.getMethod()) == RequestMethod.PUT
                || RequestMethod.valueOf(request.getMethod()) == RequestMethod.PATCH) {
            try {
                val writer = new StringWriter();
                IOUtils.copy(request.getInputStream(), writer, request.getCharacterEncoding());
                log.info("Body {}", writer);
                val requestBody = aesService.decrypt(writer.toString(), aesKey);

                log.info("Body decrypt {}", requestBody);

                val reqBodyBytes = requestBody.getBytes();

                ctx.setRequest(new HttpServletRequestWrapper(request) {

                    @Override
                    public ServletInputStream getInputStream() {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }

                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }

                });
            } catch (Exception e) {
                log.error("Request filter exception", e);
                ctx.set("throwable", new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage()));
            }
        }
        log.info("{} request {}", request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
