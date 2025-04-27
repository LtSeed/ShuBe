package org.example.shubackend.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    /**
     * HS256 secret
     */
    private String secret = "ChangeThisSecret!!";
    /**
     * access token lifetime in minutes
     */
    private long accessMinutes = 15;
    /**
     * refresh token lifetime in days
     */
    private long refreshDays = 7;
}
