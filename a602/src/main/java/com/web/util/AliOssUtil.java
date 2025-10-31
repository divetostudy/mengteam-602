package com.web.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes,String whereName, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

            // 文件上传路径设置 https://BucketName.Endpoint/image/whereName/ObjectName
            String folderName = "images/" + whereName; // 文件夹路径
            String fullObjectName = folderName + "/" + objectName; // 完整的对象名称

            // 创建PutObject请求。
            ossClient.putObject(bucketName, fullObjectName, new ByteArrayInputStream(bytes));

           // ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/image/whereName/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append("images")
                .append("/")
                .append(whereName)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }



    public void deleteObjectByUrl(String whereName,String url) {

        // 解析出对象名
        String objectName = getObjectKeyFromUrl(url);
        String folderName = "images/" + whereName; // 文件夹路径
        String fullObjectName = folderName + "/" + objectName; // 完整的对象名称

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 删除对象。
            ossClient.deleteObject(bucketName, fullObjectName);
            System.out.println("成功删除对象: " + objectName);
        } catch (OSSException oe) {
            // OSS 异常处理
            System.out.println("Caught an OSSException: " + oe.getErrorMessage());
        } catch (ClientException ce) {
            // 客户端异常处理
            System.out.println("Caught a ClientException: " + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    // 从URL中解析出对象名
    public String getObjectKeyFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            // Assuming the path looks like /path/to/objectName
            // Extract object name from path
            String[] segments = path.split("/");
            if (segments.length > 0) {
                return segments[segments.length - 1]; // last segment is the object name
            }
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL: " + url);
        }
        return null;
    }


    public String getObjectName(MultipartFile file){
        //上传的原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件名后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件名称
        String objectName = UUID.randomUUID().toString() + extension;

        return objectName;
    }
}