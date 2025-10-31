package com.web.mapper;

import com.web.pojo.DO.Prize;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 奖项 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-16
 */
public interface PrizeMapper extends BaseMapper<Prize> {

    void add(Prize prize);

    String getUrl(Integer id);

    void update(Prize prize);
}
