package com.web.service;

import com.web.pojo.DO.Img;
import com.web.pojo.DO.ImgProject;
import com.web.pojo.DO.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.DTO.ProjectDTO;
import com.web.pojo.VO.ProjectVO;
import com.web.pojo.VO.SelectProjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-07-15
 */
public interface IProjectService extends IService<Project> {

    List<SelectProjectVO> get();

    void add(MultipartFile[] files, MultipartFile image, ProjectVO projectVO) throws IOException;

    void delete(Integer id);

    List<SelectProjectVO> select(Integer id);

    void update(MultipartFile[] files, Img img, ProjectDTO projectDTO, ImgProject[] imgProjects, String[] deleteUrl) throws IOException;
}
