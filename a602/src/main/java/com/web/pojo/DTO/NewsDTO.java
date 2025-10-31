package com.web.pojo.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NewsDTO {
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

    /*@ApiModelProperty(value = "配图")
    @TableField("img")
    private String img;*/

    @ApiModelProperty(value = "发布时间")
    @TableField("time")
    private LocalDateTime time;

}
