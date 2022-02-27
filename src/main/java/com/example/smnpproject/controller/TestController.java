package com.example.smnpproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songmeining
 * @description TODO
 * @date 2021-11-23 9:44
 */
@RequestMapping("/image")
@RestController
public class TestController {

    @GetMapping("/getImage")
    public String getImage() {

        return "ok";
    }
}
