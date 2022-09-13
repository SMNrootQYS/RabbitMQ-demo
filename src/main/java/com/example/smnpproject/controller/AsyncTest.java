package com.example.smnpproject.controller;

import com.example.smnpproject.conpoment.AsyncTestCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *  异步测试
 * ***/

@RestController
@RequestMapping("/testAsync")
public class AsyncTest {

    @Autowired
    AsyncTestCom asyncTestCom;

    @GetMapping("/test")
    public void test() throws Exception {
        asyncTestCom.doTaskOne();
        asyncTestCom.doTaskTwo();
        asyncTestCom.doTaskThree();
    }



}
