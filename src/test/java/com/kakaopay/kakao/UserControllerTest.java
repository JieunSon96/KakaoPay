package com.kakaopay.kakao;

import com.kakaopay.kakao.controller.UserController;
import com.kakaopay.kakao.service.UserService;
import com.kakaopay.kakao.vo.SignUpRequest;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication*/
public class UserControllerTest {


    private MockMvc mvc;


    @Autowired
    private UserService userService;


    private String TEST_EMAIL = "test3@gmail.com";
    private String TEST_PW = "password";
    private String TEST_NAME = "test3";

    @Test
    public void signUpTest() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(TEST_EMAIL);
        signUpRequest.setPassword(TEST_PW);
        signUpRequest.setName(TEST_NAME);

        mvc.perform(MockMvcRequestBuilders.post("/api/user/signup"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        System.out.println(" 테스트");


    }

    // 회원가입 테스트
//    @Test
//    public void signUpTest() throws Exception{
//        SignUpRequest signUpRequest=new SignUpRequest();
//        signUpRequest.setEmail(TEST_EMAIL);
//        signUpRequest.setName(TEST_NAME);
//        signUpRequest.setPassword(TEST_PW);

//        String jsonRequest=objectMapper.writeValueAsString(signUpRequest);
//        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
//                .content(jsonRequest).content(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        String resultContent=result.getResponse().getContentAsString();
//
//        ApiResponse apiResponse=objectMapper.readValue(resultContent,ApiResponse.class);
//        Assert.assertTrue(apiResponse.getSuccess() ==Boolean.TRUE);
//    }

    // 로그인 테스트
//    @Test
//    public void testSignIn() throws Exception{
//        System.out.println("로그인  한번에 되어야지 밤을 안샐거야 제발");
//
//        //given
//        final SignUpRequest request=buildSignupRequest();
//
//        //when
//
//        //then
//    }
}
