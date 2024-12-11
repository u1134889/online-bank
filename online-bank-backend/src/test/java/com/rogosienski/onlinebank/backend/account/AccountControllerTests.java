package com.rogosienski.onlinebank.backend.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AccountControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void testGetAccount() throws Exception {
        mvc.perform(get("/account/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance", is(10)));
    }

    @Test
    void testOpenAccount() throws Exception {
        mvc.perform(post("/account/open").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance", is(0)));
    }

    @Test
    void testModifyBalance_add() throws Exception {
        mvc.perform(put("/account/1").contentType(MediaType.APPLICATION_JSON).content("{\"value\":14,\"operation\":\"ADD\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance", is(24)));
    }

    @Test
    void testModifyBalance_subtract() throws Exception {
        mvc.perform(put("/account/1").contentType(MediaType.APPLICATION_JSON).content("{\"value\":7,\"operation\":\"SUBTRACT\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance", is(3)));
    }
}
