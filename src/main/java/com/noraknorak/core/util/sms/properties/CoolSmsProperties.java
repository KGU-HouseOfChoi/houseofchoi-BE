package com.noraknorak.core.util.sms.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "coolsms")
public class CoolSmsProperties {
    private String apiKey;
    private String apiSecret;
    private String senderPhone;
    private String domain;
}
