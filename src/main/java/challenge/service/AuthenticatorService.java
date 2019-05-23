package challenge.service;

import challenge.dto.CredentialDTO;
import challenge.dto.SecretKeyDTO;
import challenge.dto.StatusReponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticatorService {

    private static final int DEFAULT_SIZE_KEY = 20;

    public SecretKeyDTO generateKey() {
        byte[] buffer = new byte[DEFAULT_SIZE_KEY];

        new Random().nextBytes(buffer);

        String encodedKey = new Base32().encodeToString(buffer);
        SecretKeyDTO dto = new SecretKeyDTO(encodedKey);
        return dto;
    }

    public StatusReponseDTO validateCode(CredentialDTO credentialDTO) throws InvalidKeyException, NoSuchAlgorithmException {
        StatusReponseDTO dto = new StatusReponseDTO(false);
        long t = new Date().getTime() / TimeUnit.SECONDS.toMillis(30);
        dto.setStatus(this.check_code(credentialDTO.getGeneratedKey(), credentialDTO.getAuthCode(), t));
        return dto;
    }

    private boolean check_code(String secret, long code, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);

        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        int window = 1;
        for (int i = -window; i <= window; ++i) {
            long hash = this.verify_code(decodedKey, t + i);

            if (hash == code) {
                return true;
            }
        }

        // The validation code is invalid.
        return false;
    }

    private int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);

        int offset = hash[20 - 1] & 0xF;

        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;

        return (int) truncatedHash;
    }
}
