package com.web.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mai
 * @since 2024-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student")
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "毕业年份")
    private String graduationYear;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "格言")
    private String lifeMotto;

    @ApiModelProperty(value = "在校获奖情况")
    private String awardAtschool;

    @ApiModelProperty(value = "工作经验")
    private String workExperience;

    @ApiModelProperty(value = "爱好")
    private String hobby;

    @ApiModelProperty(value = "想对师弟师妹说的话")
    private String say;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "个人简介")
    private String introduction;

    @ApiModelProperty(value = "学生照片")
    private String photoUrl;


}
