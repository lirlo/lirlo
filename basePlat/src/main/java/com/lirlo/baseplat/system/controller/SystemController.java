package com.lirlo.baseplat.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lirlo.baseplat.core.common.ResponseStatusEnum;
import com.lirlo.baseplat.system.pojo.T_SYS_ACCOUNT;
import com.lirlo.baseplat.system.service.impl.SystemServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "系统管理")
@RestController
@RequestMapping("/system")
public class SystemController {

    @Resource
    SystemServiceImpl systemService;

    @PostMapping("/get")
    @ApiOperation(value="系统管理-获取")
    public String get(){

        return "";
    }

    @PostMapping("/selectPage")
    @ApiOperation(
            value = "分页查询账号",
            httpMethod = "POST"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(value = "pageNum",name = "pageNum",required = true),
            @ApiImplicitParam(value = "pageSize",name = "pageSize",required = true)
    })
    public IPage<T_SYS_ACCOUNT> selectPage(@RequestBody T_SYS_ACCOUNT entity, int pageNum, int pageSize){
        return this.systemService.selectPage(entity, pageNum, pageSize);
    }

    @PostMapping("/getOne")
    @ApiOperation(value = "查询单个",httpMethod = "POST")
    public T_SYS_ACCOUNT getOne(){
        return this.systemService.getOne();
    }
}
