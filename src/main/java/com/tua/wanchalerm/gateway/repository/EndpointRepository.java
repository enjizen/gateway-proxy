package com.tua.wanchalerm.gateway.repository;

import com.tua.wanchalerm.gateway.model.document.EndpointDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointRepository extends MongoRepository<EndpointDocument, String> {

    EndpointDocument findByHttpMethodAndPattern(String httpMethod, String pattern);

}
