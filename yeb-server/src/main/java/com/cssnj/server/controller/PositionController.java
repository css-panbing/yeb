package com.cssnj.server.controller;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Position;
import com.cssnj.server.service.IPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 职位管理
 *
 * @author panbing
 * @since 2021-12-16
 */
@RestController
@RequestMapping("/system/cfg/position")
@Api(tags = "职位管理")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @ApiOperation("查询所有职位信息")
    @GetMapping("/")
    public List<Position> queryAllPositions(){
        List<Position> positions = positionService.list();
        return positions;
    }

    @ApiOperation("查询单个职位信息")
    @GetMapping("/{id}")
    public Position getPosition(@PathVariable("id") Integer id){
        Position position = positionService.getById(id);
        return position;
    }

    @ApiOperation("添加职位信息")
    @PostMapping("/")
    public ResponseData addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if(positionService.save(position)){
            return ResponseData.success("添加成功");
        }
        return ResponseData.error("添加失败");
    }

    @ApiOperation("更新职位信息")
    @PutMapping("/")
    public ResponseData updatePosition(@RequestBody Position position){
        if(positionService.updateById(position)){
            return ResponseData.success("更新成功");
        }
        return ResponseData.error("更新失败");
    }

    @ApiOperation("删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseData deletePosition(@PathVariable("id") Integer id){
        if(positionService.removeById(id)){
            return ResponseData.success("删除成功");
        }
        return ResponseData.error("删除失败");
    }

    @ApiOperation("批量删除职位信息")
    @DeleteMapping("/")
    public ResponseData deletePositions(String ids){
        String[] split = ids.split(",");
        if(positionService.removeByIds(Arrays.asList(split))){
            return ResponseData.success("删除成功");
        }
        return ResponseData.error("删除失败");
    }

}
