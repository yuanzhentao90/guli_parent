package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//用于封装条件查询的类
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程标题,模糊查询")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;
}
