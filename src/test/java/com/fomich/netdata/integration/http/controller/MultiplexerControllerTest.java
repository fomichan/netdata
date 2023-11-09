package com.fomich.netdata.integration.http.controller;

import com.fomich.netdata.dto.MultiplexerCreateEditDto.Fields;
import com.fomich.netdata.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class MultiplexerControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    //@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER", "MANAGER"}) // при необходимости она заменит из IntegrationTestBase
    void findAll() throws Exception {
        mockMvc.perform(get("/multiplexers")
                        .param("name", "ul")
                        .param("serialNumber", "e")
                        .param("siteId", "9")
                        .param("sort", "serialNumber")
                        .param("direction", "desc")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("channel/multiplexers"))
                .andExpect(model().attributeExists("multiplexers"))
                //.andExpect(model().attribute("multiplexers", hasSize(3)));
                .andExpect(model().attribute("multiplexers", Matchers.hasProperty("content", Matchers.hasSize(3)))); // что в content 4 мультиплексора
    }



    @Test
    void create() throws Exception {
        mockMvc.perform(post("/multiplexers")
                                .param(Fields.name, "testmux2")
                                .param(Fields.serialNumber, "123456")
                                .param(Fields.siteId, "5")
                                .with(csrf())
                )
                .andExpectAll(
                       status().is3xxRedirection(),
                        //redirectedUrlPattern("/multiplexers/{\d+}")
                        redirectedUrl("/multiplexers")
                );
    }


    @Test
    void update() throws Exception {
        mockMvc.perform(post("/multiplexers/{id}/update", 6)
                                .param(Fields.name, "testmux6")
                                .param(Fields.serialNumber, "654321")
                                .param(Fields.siteId, "6")
                                .with(csrf())
                )
                .andExpectAll(
                       status().is3xxRedirection(),
                        redirectedUrlPattern("/multiplexers/{\\d+}")
                        //redirectedUrl("/multiplexers")

                );
    }




    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/multiplexers/{id}/delete", 7)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/multiplexers"));
    }


}