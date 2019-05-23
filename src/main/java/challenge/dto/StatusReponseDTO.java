package challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StatusReponseDTO implements Serializable {

    @JsonProperty("status")
    private boolean status;
}
