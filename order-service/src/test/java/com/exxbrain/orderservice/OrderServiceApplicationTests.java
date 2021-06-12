package com.exxbrain.orderservice;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.cloud.discovery.enabled = false"})
class OrderServiceApplicationTests {

}
