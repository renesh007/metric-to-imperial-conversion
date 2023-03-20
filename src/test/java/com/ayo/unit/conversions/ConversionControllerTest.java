package com.ayo.unit.conversions;


import com.ayo.unit.conversions.model.AddConversionRequest;
import com.ayo.unit.conversions.model.ConversionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ConversionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    private ObjectWriter ow;
    @BeforeEach
    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new ConversionController(new ConversionService()))
//                .build();

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
    }
    @Test
    public void whenPingEndpointCalled_thenOkResponseObtained() throws Exception {

        this.mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());

    }

    @Test
    public void whenConvertEndpointCalled_withValidInput_thenOkResponseObtained() throws Exception {

        ConversionRequest cr = new ConversionRequest("Celsius","Fahrenheit",30.0);
        String requestJson=ow.writeValueAsString(cr);

        MvcResult result = this.mockMvc.perform(post("/api/temperature/convert")
                                    .content(requestJson)
                                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("86.0"))
                .andReturn();
    }

    @Test
    public void whenConvertEndpointCalled_withNotValidInput_thenBadRequestObtained() throws Exception {

        ConversionRequest cr = new ConversionRequest("Celsius","",30.0);
        String requestJson=ow.writeValueAsString(cr);

        MvcResult result = this.mockMvc.perform(post("/api/temperature/convert")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("{\"errors\":[\"Destination Unit missing\"]}"))
                .andReturn();

    }

    @Test
    public void whenAddEndpointCalled_withValidInput_thenOkResponseObtained() throws Exception {

        AddConversionRequest cr = new AddConversionRequest("Celsius","metric","Kelvin","SI",
                "Temperature","i + 273.15",true);
        String requestJson=ow.writeValueAsString(cr);

        MvcResult result = this.mockMvc.perform(post("/api/add")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andReturn();
    }

    @Test
    public void whenAddEndpointCalled_withInvalidInput_thenBadRequestObtained() throws Exception {

        AddConversionRequest cr = new AddConversionRequest("Meter per second","metric","kilometer per second","metric",
                "Temperature","i + 273.15",true);
        String requestJson=ow.writeValueAsString(cr);

        MvcResult result = this.mockMvc.perform(post("/api/add")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Cannot convert units of the same system: metric -> metric"))
                .andReturn();




    }

}
