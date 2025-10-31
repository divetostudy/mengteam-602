package com.web.pojo.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherVO {

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "职位")
    private String job;

    @ApiModelProperty(value = "介绍")
    private String introduction;

    @ApiModelProperty(value = "荣誉")
    private String honor;

    @ApiModelProperty(value = "方向")
    private String direction;

    @ApiModelProperty(value = "头像背景色")
    private String avatarColor;

    @ApiModelProperty(value = "盒子背景色")
    private String bgColor;

    @ApiModelProperty(value = "图片")
    private byte[] photo;

    @ApiModelProperty(value = "图片路径")
    private String photoUrl;


}
