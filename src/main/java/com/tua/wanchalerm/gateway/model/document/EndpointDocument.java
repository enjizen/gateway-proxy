package com.tua.wanchalerm.gateway.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("endpoint")
@Data
public class EndpointDocument {
    @Id
    private String _id;
    private String id;
    private String name;
    @Field("http_method")
    private String httpMethod;
    private String pattern;
}
