package challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SecretKeyDTO implements Serializable {

    @JsonProperty("secretKey")
    private String secretKey;
}
