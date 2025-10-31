package com.web.mapper;

import com.web.pojo.DO.ImgAchievement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 成果展示图片 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-13
 */
public interface ImgAchievementMapper extends BaseMapper<ImgAchievement> {

    List<String> getUrl(Integer id);

    void add(Integer id, String url);
    Integer[] getId(Integer id);

    String getOriginalUrl(Integer integer);

    void update(Integer integer, String url);

    void delete(Integer id);

    String getUrlById(Integer integer);

    void deleteByUrl(String s);
}
