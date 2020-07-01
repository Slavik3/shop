package com.ukrposhta.shop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ShopApplication.class })
@WebAppConfiguration
@Sql(scripts = "/test.sql")
public class ControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void getProductsTest() throws Exception {
		this.mockMvc.perform(get("/list")).andExpect(status().isOk()).andExpect(content().string(
				"[{\"id\":1,\"name\":\"Samsung Galaxy A40\",\"price\":4799.00,\"description\":\"cтаньте ближе к тому, что важно для вас, c 5.9-дюймовом безграничным U-экраном Galaxy A40.\",\"category\":{\"id\":1,\"name\":\"phones\"}},{\"id\":2,\"name\":\"HP Pavilion Gaming 15-cx0033ua\",\"price\":19999.00,\"description\":\"Уверенность и комфорт при выполнении повседневных задач\",\"category\":{\"id\":2,\"name\":\"laptops\"}},{\"id\":3,\"name\":\"Huawei P30 Lite\",\"price\":5299.00,\"description\":\"Huawei P30 Lite\",\"category\":{\"id\":1,\"name\":\"phones\"}},{\"id\":4,\"name\":\"Prestigio Muze J5 5524 Duo Dark Night\",\"price\":1299.00,\"description\":\"Prestigio Muze J5 5524 Duo Dark Night\",\"category\":{\"id\":1,\"name\":\"phones\"}}]"));
	}

	@Test
	public void getProductsByCategoryTest() throws Exception {
		this.mockMvc.perform(get("/list?category=phones")).andExpect(status().isOk()).andExpect(content().string(
				"[{\"id\":1,\"name\":\"Samsung Galaxy A40\",\"price\":4799.00,\"description\":\"cтаньте ближе к тому, что важно для вас, c 5.9-дюймовом безграничным U-экраном Galaxy A40.\",\"category\":{\"id\":1,\"name\":\"phones\"}},{\"id\":3,\"name\":\"Huawei P30 Lite\",\"price\":5299.00,\"description\":\"Huawei P30 Lite\",\"category\":{\"id\":1,\"name\":\"phones\"}},{\"id\":4,\"name\":\"Prestigio Muze J5 5524 Duo Dark Night\",\"price\":1299.00,\"description\":\"Prestigio Muze J5 5524 Duo Dark Night\",\"category\":{\"id\":1,\"name\":\"phones\"}}]"));
	}

}
