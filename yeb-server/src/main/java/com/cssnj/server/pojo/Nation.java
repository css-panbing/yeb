package com.cssnj.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 民族类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "name")//of表示重写EqualsAndHashCode方法的属性
@Accessors(chain = true)
@TableName("t_nation")
@ApiModel(value="Nation对象", description="")
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "民族")
    @Excel(name = "民族")
    @NonNull//有参构造时name不能为空
    private String name;


}
