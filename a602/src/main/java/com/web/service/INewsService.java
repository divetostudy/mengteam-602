package com.web.service;

import com.web.pojo.DO.Img;
import com.web.pojo.DO.ImgNews;
import com.web.pojo.DO.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.DTO.NewsDTO;
import com.web.pojo.VO.NewsVO;
import com.web.pojo.VO.SelectNewsVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 新闻信息 服务类
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
public interface INewsService extends IService<News> {

    void add(MultipartFile[] files, NewsVO newsVO, MultipartFile img) throws IOException;

    List<SelectNewsVO> getnews();

    List<SelectNewsVO> getnews(Integer id);

    void update(MultipartFile[] files, NewsDTO newsVO, Img img, ImgNews[] imgNews, String[] deleteUrl) throws IOException;

    void delete(Integer id);

}
