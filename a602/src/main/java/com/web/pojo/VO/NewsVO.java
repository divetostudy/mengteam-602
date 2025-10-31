package com.web.pojo.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.pojo.DO.ImgNews;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NewsVO {

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

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty(value = "封面")
    @TableField("img")
    private String img;

    /*@ApiModelProperty(value = "新闻配图")
    private List<String> imgs;*/
}
