package com.web.pojo.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AchievementVO {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "介绍")
    private String content;
}
