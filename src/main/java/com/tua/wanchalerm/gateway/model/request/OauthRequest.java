package com.tua.wanchalerm.gateway.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OauthRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("digital_pass")
    private String digitalPass;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_secret")
    private String clientSecret;
}
