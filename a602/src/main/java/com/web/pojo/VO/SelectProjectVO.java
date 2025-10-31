package com.web.pojo.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.web.pojo.DO.Photo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SelectProjectVO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目")
    private String title;

    @ApiModelProperty(value = "介绍")
    private String content;

    @ApiModelProperty(value = "项目封面")
    @TableField("image")
    private Photo image = new Photo();

    @ApiModelProperty(value = "项目配图")
    @TableField("photos")
    private List<Photo> photos = new ArrayList<>();


    public void setPhotos(String imgUrl) {
        Photo photo = new Photo();
        photo.setPhotoUrl(imgUrl);
        photos.add(photo);
    }

    public void setPhoto(String imgUrl) {
//        image.setPhoto(imageData);
        image.setPhotoUrl(imgUrl);
    }
}

