package com.web.service;

import com.web.pojo.DO.Achievement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.DO.ImgAchievement;
import com.web.pojo.DTO.AchievementDTO;
import com.web.pojo.VO.AchievementVO;
import com.web.pojo.VO.SelectAchievementVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 成果 服务类
 * </p>
 *
 * @author author
 * @since 2024-07-13
 */
public interface IAchievementService extends IService<Achievement> {

    List<SelectAchievementVO> select();

    void add(MultipartFile[] files, AchievementVO achievementVO) throws IOException;

    void update(AchievementDTO achievementDTO, MultipartFile[] files , String[] deleteUrl , ImgAchievement[] imgAchievements) throws IOException;

    void delete(Integer id);
}
