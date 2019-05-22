package challenge.controller;

import challenge.dto.SecretKeyDTO;
import challenge.dto.StatusDTO;
import challenge.service.AuthenticatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ChallengeApiController {

    private final AuthenticatorService service;

    @RequestMapping("/generateKey")
    @ApiOperation(value = "Generates a key to two step verification", httpMethod = "GET", response = SecretKeyDTO.class)
    public ResponseEntity<SecretKeyDTO> generateKey() {
        SecretKeyDTO dto = this.service.generateKey();
        return ResponseEntity.ok(dto);
    }

    @RequestMapping("/validateCode")
    @ApiOperation(value = "Validates a key by auth code", httpMethod = "POST", response = StatusDTO.class)
    public ResponseEntity<Object> validateCode(@Valid @RequestBody String generatedKey) {
        StatusDTO dto = new StatusDTO(true);
        return ResponseEntity.ok(dto);
    }
}
