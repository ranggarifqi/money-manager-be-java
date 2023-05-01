package com.ranggarifqi.moneymanager.user.controllers;

import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerVerifyUserTests {

    private final String token = "someToken";
    private final String url = "/v1/users/verify?token=" + this.token;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IUserService userService;

    @Test
    public void shouldReturnErrorResponseOnVerifyUserError() throws Exception {
        Mockito.when(this.userService.verifyUser(this.token)).thenThrow(new NotFoundException("Invalid token"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(this.url)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("{\"type\":\"about:blank\",\"title\":\"Not Found\",\"status\":404,\"detail\":\"Invalid token\",\"instance\":\"/v1/users/verify\"}"));
    }

    @Test
    public void shouldRedirectOnSuccess() throws Exception {
        Mockito.when(this.userService.verifyUser(this.token)).thenReturn(new User());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(this.url)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
