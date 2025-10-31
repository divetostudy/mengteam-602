package com.web.mapper;

import com.web.entity.Environment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
public interface EnvironmentMapper extends BaseMapper<Environment> {


   // @Insert("insert into environment(photo_url) values(#{url})")
    void add(String url);

   // @Select("select photo_url from environment")
    List<String> get();

    //@Delete("delete from environment where photo_url = #{url}")
    void deletePhoto(String url);
}
