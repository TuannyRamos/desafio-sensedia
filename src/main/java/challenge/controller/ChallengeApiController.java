package challenge.controller;

import challenge.dto.CredentialDTO;
import challenge.dto.SecretKeyDTO;
import challenge.dto.StatusReponseDTO;
import challenge.service.AuthenticatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
public class ChallengeApiController {

    private final AuthenticatorService service;

    @GetMapping("/tokens")
    @ApiOperation(value = "Generates a key to two step verification", httpMethod = "GET", response = SecretKeyDTO.class)
    public ResponseEntity<SecretKeyDTO> generateKey() {
        log.info("Generating a secret key");
        SecretKeyDTO dto = this.service.generateKey();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/tokens")
    @ApiOperation(value = "Validates a key by auth code", httpMethod = "POST", response = StatusReponseDTO.class)
    public ResponseEntity<Object> validateCode(@Valid @RequestBody CredentialDTO credentialDTO)
            throws NoSuchAlgorithmException, InvalidKeyException {
        log.info("Validating a secret key by Google Authenticator Code: {}, Token: {}",
                credentialDTO.getAuthCode(), credentialDTO.getGeneratedKey());
        StatusReponseDTO dto = this.service.validateCode(credentialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
