package challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CredentialDTO implements Serializable {

    @JsonProperty("generatedKey")
    private String generatedKey;

    @JsonProperty("authCode")
    private Long authCode;
}
