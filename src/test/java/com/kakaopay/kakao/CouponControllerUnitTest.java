package com.kakaopay.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.kakao.service.CouponService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filter;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.web.FilterChainProxy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
