package com.web.pojo.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentVO {
    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "毕业年份")
    private String graduationYear;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "兴趣爱好")
    private String hobby;

    @ApiModelProperty(value = "自我介绍")
    private String introduction;

    @ApiModelProperty(value = "格言")
    private String lifeMotto;

    @ApiModelProperty(value = "在校获奖情况")
    private String awardAtschool;

    @ApiModelProperty(value = "工作经验")
    private String workExperience;

    @ApiModelProperty(value = "想对师弟师妹说的话")
    private String say;
    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "图片")
    private byte[] photo;
    @ApiModelProperty(value = "图片路径")
    private String photoUrl;

}
