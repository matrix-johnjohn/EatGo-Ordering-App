package com.example.demo;


import com.example.demo.mapper.DemoMapper;
import com.example.demo.po.Gender;
import com.example.demo.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTest {

    @Autowired
    private DemoMapper demoMapper;

    @Test
    void test1() {

    }

}
