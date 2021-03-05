package com.saman.springsecurityauthenticator.security;

import com.saman.springsecurityauthenticator.model.User;
import com.saman.springsecurityauthenticator.model.UserRepository;
import com.saman.springsecurityauthenticator.service.MyUserDetailServiceImpl;
import com.saman.springsecurityauthenticator.service.MyUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyUserDetailsServiceUnitTests {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @MockBean
    UserRepository userRepository;

    private String getUser1() {
        return "user5";
    }

    private List<User> getUsersFromDB() {
        List<User> usersFromDB = new ArrayList<>();
        User user = new User();
        user.setUsername("user5");
        user.setPassword("pwd1");
        usersFromDB.add(user);
        return usersFromDB;
    }

    @Test
    public void viewRegisteredUsersTest_success() {
        List<User> userListFromDB = getUsersFromDB();
        when(userRepository.findAll()).thenReturn(userListFromDB);
        List usersList = myUserDetailsService.viewRegisteredUsers();
        assertNotNull(usersList);
        assertTrue(usersList.contains(getUser1()));
    }

}
