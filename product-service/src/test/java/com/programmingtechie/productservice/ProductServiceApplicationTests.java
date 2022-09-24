package com.programmingtechie.productservice;

import com.programmingtechie.productservice.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

  @Container static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.1");

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

//  @Test
  void shouldCreateProduct() throws Exception {
    ProductRequest build =
        ProductRequest.builder().name("iphone10").description("Iphone12").price("1142343").build();

    String productRequest = objectMapper.writeValueAsString(build);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequest))
        .andExpect(status().isCreated());
  }
}
