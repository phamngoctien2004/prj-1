package com.web.prj.services.interfaces;

import javax.crypto.SecretKey;
import java.util.List;

public interface ITokenService {
    String generate(String subject, List<String> roles, int expiration);
    SecretKey encodeSecretKey();
}
