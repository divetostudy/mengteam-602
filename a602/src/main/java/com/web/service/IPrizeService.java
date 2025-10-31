package com.web.service;

import com.web.pojo.DO.Img;
import com.web.pojo.DO.Prize;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.DTO.PrizeDTO;
import com.web.pojo.VO.PrizeVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 奖项 服务类
 * </p>
 *
 * @author author
 * @since 2024-07-16
 */
public interface IPrizeService extends IService<Prize> {

    List<PrizeVO> getPrize();

    void add(PrizeDTO prizeDTO, MultipartFile file) throws IOException;

    void update(PrizeDTO prizeDTO, Img file) throws IOException;

    void delete(Integer id);
}
