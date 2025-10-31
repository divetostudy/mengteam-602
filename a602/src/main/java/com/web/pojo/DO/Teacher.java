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
 * @since 2024-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("images/teacher")
@ApiModel(value="Teacher对象", description="")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;



    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "图片")
    private String photoUrl;

    @ApiModelProperty(value = "头像背景色")
    private String avatarColor;
    @ApiModelProperty(value = "盒子背景色")
    private String bgColor;


}
