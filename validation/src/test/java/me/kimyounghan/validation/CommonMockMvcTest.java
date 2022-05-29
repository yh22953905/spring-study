package me.kimyounghan.validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CommonMockMvcTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    public MultiValueMap<String, String> convertToMultiValueMap(Object obj) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<>() {});

        parameters.setAll(maps);

        return parameters;
    }
}
