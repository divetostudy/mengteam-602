package com.web.constant;

import com.web.common.util.FileNameUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Component
public class PhotoConstant {
    public String saveFile(MultipartFile file , String u){
        Random r = new Random();
        String preUrl = "\\src\\main\\resources\\images\\"+ u + "\\";//设置存储路径
        String originalFilename = file.getOriginalFilename();//原来图片名
        //String urlName = FileNameUtils.getFileName(originalFilename);//获取随机名字
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                preUrl;
        //准备要存的地址
        String url = pre + r.nextInt() + originalFilename;
        //储存文件
        File path = new File(url);
        try {
            file.transferTo(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

}
