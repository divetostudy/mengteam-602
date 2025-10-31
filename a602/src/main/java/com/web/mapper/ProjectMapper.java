package com.web.mapper;

import com.web.pojo.DO.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-15
 */
public interface ProjectMapper extends BaseMapper<Project> {

    void add(Project project);

    String getUrl(Integer id);

    void update(Project project);

    void delete(Integer id);

    List<Project> getById(Integer id);
}
