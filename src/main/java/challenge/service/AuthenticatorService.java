package challenge.service;

import challenge.dto.SecretKeyDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticatorService {

    private static final int DEFAULT_SIZE_KEY = 20;

    public SecretKeyDTO generateKey() {
        byte[] buffer = new byte[DEFAULT_SIZE_KEY];

        new Random().nextBytes(buffer);

        Base32 codec = new Base32();
        byte[] secretKey = Arrays.copyOf(buffer, DEFAULT_SIZE_KEY);
        byte[] bEncodedKey = codec.encode(secretKey);

        String encodedKey = new String(bEncodedKey);
        SecretKeyDTO dto = new SecretKeyDTO(encodedKey);
        return dto;
    }
}
