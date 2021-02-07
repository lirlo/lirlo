package com.lirlo.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "基础管理")
@RestController
@RequestMapping("/base")
public class BaseController {

    @PostMapping("/get")
    @ApiOperation(value="基础管理-获取")
    public String get(){

        return "";
    }
}
