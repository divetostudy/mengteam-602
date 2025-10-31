package com.web.pojo.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectVO {
    @ApiModelProperty(value = "题目")
    private String title;

    @ApiModelProperty(value = "介绍")
    private String content;
}
