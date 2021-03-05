package com.saman.springsecurityauthenticator.security;

import com.saman.springsecurityauthenticator.util.TokenUtil;
import org.apache.tomcat.websocket.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserAuthenticationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenAuthenticatedUser_thenSuccess() throws Exception{
        UserDetails userDetails = new User("user1", "password1", new ArrayList<>());
        String token = "Bearer " + TokenUtil.generateTokenFromUserDetails(userDetails);
        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/welcomeUser")
                .header(Constants.AUTHORIZATION_HEADER_NAME, token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user1")));

    }

    @Test
    public void givenInvalidCredentials_thenError() throws Exception {
        UserDetails userDetails = new User("randomUser", "randomPassword", new ArrayList<>());
        String token = "Bearer " + TokenUtil.generateTokenFromUserDetails(userDetails);
        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/welcomeUser")
                .header(Constants.AUTHORIZATION_HEADER_NAME, token))
                .andExpect(status().isUnauthorized());}
}
