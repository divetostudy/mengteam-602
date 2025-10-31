package com.web.service.impl;

import com.web.constant.PhotoConstant;
import com.web.pojo.DO.Img;
import com.web.pojo.DO.Prize;
import com.web.mapper.PrizeMapper;
import com.web.pojo.DTO.PrizeDTO;
import com.web.pojo.VO.PrizeVO;
import com.web.service.IPrizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 奖项 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-16
 */
@Service
public class PrizeServiceImpl extends ServiceImpl<PrizeMapper, Prize> implements IPrizeService {

    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private PhotoConstant photoConstant;
    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public List<PrizeVO> getPrize() {
        List<Prize> prizes= prizeMapper.selectList(null);
        List<PrizeVO> prizeVOList = new ArrayList<>();
        for (Prize prize : prizes){
            PrizeVO prizeVO = new PrizeVO();
            BeanUtils.copyProperties(prize , prizeVO);
//            String imgUrl = prize.getUrl();
//            // 使用Optional，如果imageUrl不为空，则尝试读取图片数据
//            Optional.ofNullable(imgUrl)
//                    .ifPresent(url -> {
//                        try {
//                            // 读取图片文件的所有字节
//                            byte[] imageData = Files.readAllBytes(Path.of(url));
//                            // 将图片数据设置到StudentVO中
//                            prizeVO.setPhoto(imageData);
//                        } catch (IOException e) {
//                            // 如果发生错误，抛出RuntimeException
//                            throw new RuntimeException(e);
//                        }
//                    });
            prizeVOList.add(prizeVO);
        }
        return prizeVOList;
    }

    @Override
    public void add(PrizeDTO prizeDTO, MultipartFile file) throws IOException {
        Prize prize = new Prize();
        BeanUtils.copyProperties(prizeDTO , prize);
        if (file != null){
            String objectName = aliOssUtil.getObjectName(file);
            String url = aliOssUtil.upload(file.getBytes(), "prize",objectName);
            //String url = photoConstant.saveFile(file, "prize");
            prize.setUrl(url);
        }
        prizeMapper.add(prize);
    }

    @Override
    public void update(PrizeDTO prizeDTO, Img img) throws IOException {
        Prize prize = new Prize();
        BeanUtils.copyProperties(prizeDTO , prize);
        String originalUrl = prizeMapper.getUrl(prize.getId());
        if (!originalUrl.isEmpty()){//判断原来是否有图片
            //有图片
            if (img.getUrl() != null && img.getFile() == null){
                prize.setUrl(img.getUrl());//如果图片没有修改，则保持不变
            }else if (img.getUrl() == null && img.getFile() != null){//如果图片改变
                aliOssUtil.deleteObjectByUrl("prize", originalUrl);//删除
                String objectName = aliOssUtil.getObjectName(img.getFile());
                String url = aliOssUtil.upload(img.getFile().getBytes(), "prize",objectName);
                prize.setUrl(url);
            }else {
                prize.setUrl(null);
            }
        }else {
            //无图片
            if (img.getFile() != null){//判断是否有新增图片
                String objectName = aliOssUtil.getObjectName(img.getFile());
                String url = aliOssUtil.upload(img.getFile().getBytes(), "prize",objectName);
                prize.setUrl(url);
            }
        }

        /*if (originalUrl != null){
            aliOssUtil.deleteObjectByUrl("prize", originalUrl);
//            File path = new File(originalUrl);
//            path.delete();
        }
        if (file != null){
            String objectName = aliOssUtil.getObjectName();
            String url = aliOssUtil.upload(file.getBytes(), "prize",objectName);
            //String url = photoConstant.saveFile(file , "prize");
            prize.setUrl(url);
        }
        else {
            prize.setUrl(null);
        }*/
        prizeMapper.update(prize);
    }

    @Override
    public void delete(Integer id) {
        String url = prizeMapper.getUrl(id);
        if (url != null){
            aliOssUtil.deleteObjectByUrl("prize", url);
//            File path = new File(url);
//            path.delete();
        }
        prizeMapper.deleteById(id);
    }
}
