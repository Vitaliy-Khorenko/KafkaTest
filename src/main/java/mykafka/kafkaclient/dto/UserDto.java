package mykafka.kafkaclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class UserDto implements Serializable {

    static final long serialVersionUID = 6386499820164944989L;

    private String id;
    private String name;

}
