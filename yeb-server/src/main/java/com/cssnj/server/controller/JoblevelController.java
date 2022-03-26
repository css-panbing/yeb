package com.cssnj.server.controller;


import com.cssnj.server.common.response.RespData;
import com.cssnj.server.pojo.Joblevel;
import com.cssnj.server.service.IJoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 职称管理
 *
 * @author panbing
 * @since 2021-12-16
 */
@RestController
@RequestMapping("/system/basic/joblevel")
@Api(tags = "职称管理")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation("查询所有职称信息")
    @GetMapping("/")
    public List<Joblevel> query(){
        List<Joblevel> joblevels = joblevelService.list();
        return joblevels;
    }

    @ApiOperation("查询单条职称信息")
    @GetMapping("/{id}")
    public Joblevel getJoblevel(@PathVariable("id") Integer id){
        Joblevel joblevel = joblevelService.getById(id);
        return joblevel;
    }

    @ApiOperation("添加职称信息")
    @PostMapping("/")
    public RespData addJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if(joblevelService.save(joblevel)){
            return RespData.success("添加成功");
        }
        return RespData.error("添加失败");
    }

    @ApiOperation("更新职称信息")
    @PutMapping("/")
    public RespData updateJoblevel(@RequestBody Joblevel joblevel){
        if(joblevelService.updateById(joblevel)){
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

    @ApiOperation("删除职称信息")
    @DeleteMapping("/{id}")
    public RespData deleteJoblevel(@PathVariable("id") Integer id){
        if(joblevelService.removeById(id)){
            return RespData.success("删除成功");
        }
        return RespData.error("删除失败");
    }

    @ApiOperation("批量删除职称信息")
    @DeleteMapping("/")
    public RespData deleteJoblevels(String ids){
        String[] split = ids.split(",");
        if (joblevelService.removeByIds(Arrays.asList(split))) {
            return RespData.success("删除成功");
        }
        return RespData.error("删除失败");
    }

}
