package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//用户注册时用的类，保存用户输入信息
@Data
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    private String nikeName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
