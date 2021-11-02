package com.tua.wanchalerm.gateway.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("client")
@Data
public class ClientDocument {
    @Id
    @Field("client_id")
    private String clientId;
    @Field("client_secret")
    private String clientSecret;
    @Field("client_name")
    private String clientName;
    @Field("is_deleted")
    private boolean isDeleted;
    @Field("is_suspended")
    private boolean isSuspended;
}
