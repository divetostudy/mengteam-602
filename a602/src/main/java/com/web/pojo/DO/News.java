package com.web.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 新闻信息
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news")
@ApiModel(value="News对象", description="新闻信息")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "新闻标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "新闻简介")
    @TableField("descr")
    private String descr;

    @ApiModelProperty(value = "新闻分类")
    @TableField("category")
    private String category;

    @ApiModelProperty(value = "新闻内容")
    @TableField("contect")
    private String contect;

    @ApiModelProperty(value = "配图")
    @TableField("img")
    private String img;

    @ApiModelProperty(value = "发布时间")
    @TableField("time")
    private LocalDateTime time;


}
