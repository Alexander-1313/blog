package ru.leverx.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.leverx.blog.controller.ArticleController;
import ru.leverx.blog.controller.RegistrationController;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("alexander.rybak2020@mail.ru")
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArticleController controller;

    @Test
    public void getPublicArticles() throws Exception {
        this.mockMvc.perform(get("/articles/2"))
                .andDo(print())
                .andExpect(authenticated());
    }

//    @Test
//    public void getMyArticles() throws Exception{
//        this.mockMvc.perform(get("/articles/my"))
//                .andDo(print())
//                .andExpect(authenticated());
//    }
}
