package com.tua.wanchalerm.gateway.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;

@Service
@Slf4j
public class DecryptionService {

    private JSONPObject jsonpObject;
    private String body;

    public HttpServletRequest parse(HttpServletRequest request) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(request.getInputStream(), writer, request.getCharacterEncoding());
        } catch (IOException e) {
            e.printStackTrace();
        }
        body = writer.toString();


      return null;
    }

}
