package cn.felord.spring.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityLearningApplicationTests {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(value = "Felordcn", password = "12345")
    public void contextLoads() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get("/foo/test")).andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

}
