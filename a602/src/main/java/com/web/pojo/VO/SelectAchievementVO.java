package com.web.pojo.VO;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class SelectAchievementVO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "介绍")
    private String content;

    @ApiModelProperty(value = "配图")
    private List<Photo> photos = new ArrayList<>();

    public void setPhotos(String imgUrl) {
        Photo photo = new Photo().setPhotoUrl(imgUrl);
        photos.add(photo);
    }
}
