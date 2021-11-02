package com.tua.wanchalerm.gateway.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("client_endpoint")
@Data
public class ClientEndpointEntity {
    @Id
    private String id;

    @Field("client_id")
    private String clientId;

    @Field("endpoint_id")
    private String endpointId;

    @Field("is_deleted")
    private boolean isDeleted;
}
