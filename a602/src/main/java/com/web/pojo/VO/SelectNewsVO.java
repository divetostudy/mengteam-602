package com.web.pojo.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.pojo.DO.Photo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SelectNewsVO {
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

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("time")
    private LocalDateTime time;

    @ApiModelProperty(value = "新闻封面")
    @TableField("image")
    private Photo image = new Photo();

    @ApiModelProperty(value = "新闻配图")
    @TableField("photos")
    private List<Photo> photos = new ArrayList<>();

    public void setPhotos(String imgUrl) {
        Photo photo = new Photo();
        photo.setPhotoUrl(imgUrl);
//        photo.setPhoto(imageData);
        photos.add(photo);
    }

    public void setPhoto(String imgUrl) {
//        image.setPhoto(imageData);
        image.setPhotoUrl(imgUrl);
    }
}
