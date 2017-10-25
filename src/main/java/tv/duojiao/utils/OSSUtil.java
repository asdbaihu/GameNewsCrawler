package tv.duojiao.utils;

import com.alibaba.druid.sql.ast.statement.SQLCreateViewStatement;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.sun.prism.impl.Disposer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tv.duojiao.core.Exceptions.ImgException;
import tv.duojiao.utils.spider.DownloadUtil;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

import static org.mariadb.jdbc.internal.ColumnType.BIGINT;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/20
 */
@Component
public class OSSUtil {
    private Logger logger = LogManager.getLogger(OSSUtil.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.filedir}")
    private String filedir;

    @Value(("${aliyun.oss.picdir-formate}"))
    private String picdirFormate;

    @Value("${download.pic.auto-delete}")
    private boolean autoDelete;
    @Autowired
    private DownloadUtil downloadUtil;
    private OSSClient ossClient;


    /**
     * 初始化
     */
    public synchronized void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    public synchronized void destory() {
        ossClient.shutdown();
    }

    /**
     * 从web端获取图片并上传至oss
     *
     * @param url
     */
    public String uploadImg2OssFromSite(String url) {
        String result = uploadImg2Oss(downloadUtil.getUrlAfterDownload(url));
        return result;
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public String uploadImg2Oss(String url) {
        if (StringUtils.isBlank(url)) {
            logger.warn("图片地址为空");
            return "";
        }
        File fileOnServer = new File(url);
        FileInputStream fin;
        Date date = Calendar.getInstance().getTime();
        String picDir = "", realUrl = "";
        if (StringUtils.isNotBlank(picdirFormate)) {
            picDir = new SimpleDateFormat(picdirFormate).format(date);
        }
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            realUrl = "http://" + bucketName + "." + endpoint + "/" + filedir + picDir + split[split.length - 1];
            this.uploadFile2OSS(fin, split[split.length - 1], filedir + picDir);
        } catch (FileNotFoundException e) {
            throw new ImgException("图片上传失败，未存在此图片");
        }
        boolean deleteSucc = false;
        if (autoDelete && fileOnServer.exists()) {
            deleteSucc = fileOnServer.delete();
        }
        return realUrl;
    }


    public String uploadImg2Oss(MultipartFile file) {
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ImgException("上传图片大小不能超过5M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name, picdirFormate);
            return name;
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName, String picDir) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, picDir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public String toString() {
        return "OSSUtil{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", filedir='" + filedir + '\'' +
                '}';
    }

}
