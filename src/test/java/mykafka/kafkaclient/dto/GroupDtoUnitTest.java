package mykafka.kafkaclient.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class GroupDtoUnitTest {

    private static ObjectMapper mapper;

    @BeforeAll
    static void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void jsonDeserialize_whenValid_thenDo() throws JsonProcessingException {
        String source = "{\n" +
                "  \"name\": \"demo\",\n" +
                "  \"item\": [\n" +
                "    {\n" +
                "      \"id\": \"100\",\n" +
                "      \"name\": \"Ізмаїл Ізмаїлов Ізмаїлович\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"101\",\n" +
                "      \"name\": \"Іван Іванов Іванович\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        GroupDto expected = new GroupDto()
                .setName("demo")
                .setUsers(List.of(
                        new UserDto()
                                .setId("100")
                                .setName("Ізмаїл Ізмаїлов Ізмаїлович"),
                        new UserDto()
                                .setId("101")
                                .setName("Іван Іванов Іванович")
                ));

        GroupDto actual = mapper.readValue(source, GroupDto.class);
        assertThat(actual, is(expected));
    }

    @Test
    void jsonDeserialize_whenEmpty_thenDo() throws JsonProcessingException {
        String source = "{}";
        GroupDto expected = new GroupDto();

        GroupDto actual = mapper.readValue(source, GroupDto.class);
        assertThat(actual, is(expected));
    }

}