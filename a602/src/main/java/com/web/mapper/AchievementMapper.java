package com.web.mapper;

import com.web.pojo.DO.Achievement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 成果 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-13
 */
public interface AchievementMapper extends BaseMapper<Achievement> {

    void add(Achievement achievement);

    void update(Achievement achievement);

    void delete(Integer id);
}
