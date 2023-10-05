package com.fomich.netdata.integration.http.controller;

import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.MultiplexerCreateEditDto.Fields;
import com.fomich.netdata.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class MultiplexerControllerTest extends IntegrationTestBase {


    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/multiplexers"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("channel/multiplexers"))
                .andExpect(model().attributeExists("multiplexers"))
                .andExpect(model().attribute("multiplexers", hasSize(10)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/multiplexers")
                                .param(Fields.name, "testmux")
                                .param(Fields.serialNumber, "123456")
                                .param(Fields.siteId, "5")//
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/multiplexers/{\\d+}")
                );
    }

}