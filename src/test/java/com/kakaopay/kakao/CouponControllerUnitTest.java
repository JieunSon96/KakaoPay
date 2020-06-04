package com.kakaopay.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.kakao.service.CouponService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CouponControllerUnitTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    
    @MockBean
    private CouponService couponService;

    ObjectMapper om=new ObjectMapper();

    @Before
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @WithMockUser(username = "admin@admin.com",roles = {"ADMIN"})
    @Test
    public void createCouponTest() throws Exception {

        MvcResult mvcResult=mvc.perform(post("/api/coupon/createCoupon").with(SecurityMockMvcRequestPostProcessors.csrf().asHeader()))
                .andExpect(status().isOk())
                .andReturn();

            String result=mvcResult.getResponse().getContentAsString();

    }
}
