package com.web.pojo.DO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Photo {

    @ApiModelProperty(value = "照片")
    private String photoUrl;

    /*@ApiModelProperty(value = "图片")
    private byte[] photo;*/

}
