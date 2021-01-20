package mykafka.kafkaclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class GroupDto implements Serializable {

    static final long serialVersionUID = 6335374456565552184L;

    private String name;
    @JsonProperty("item")
    private List<UserDto> users;

}
