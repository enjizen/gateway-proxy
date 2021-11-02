package com.tua.wanchalerm.gateway.service;

import com.tua.wanchalerm.gateway.model.document.EndpointDocument;
import com.tua.wanchalerm.gateway.repository.EndpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndpointService {
    private final EndpointRepository endpointRepository;

    public EndpointDocument get(String httpMethod, String pattern) {
        return endpointRepository.findByHttpMethodAndPattern(httpMethod, pattern);
    }
}
