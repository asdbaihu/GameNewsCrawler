package tv.duojiao.utils.spider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/24
 */
@Component
public class DownloadUtil {
    Logger logger = LogManager.getLogger(DownloadUtil.class);

    @Value("${download.pic.min-width}")
    private int minWidthOfDownloadPic;

    @Value("${download.pic.max-width}")
    private int maxWidthOfDownloadPic;

    @Value("${download.pic.min-height}")
    private int minHeightOfDownloadPic;

    @Value("${download.pic.max-height}")
    private int maxHeightOfDownloadPic;

    /**
     * 图片名称生成
     *
     * @return
     */
    public String imageName() {
        Random random = new Random();//生成随机数
        String strDate = Long.toString(System.currentTimeMillis());
        for (int i = 0; i < 3; i++) {
            strDate = strDate + random.nextInt(9);
        }
        return strDate;
    }

    /**
     * 文件夹名称-年月
     *
     * @return
     */
    public static String folderNameYM() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String strDate = formatter.format(new Date());
        return strDate;
    }

    /**
     * 文件夹名称-天
     *
     * @return
     */
    public static String folderNameD() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String strDate = formatter.format(new Date());
        return strDate;
    }

    /**
     * 下载图片到本地
     *
     * @param urlString
     * @param filename
     * @param savePath
     * @throws Exception
     */
    public String download(String urlString, String domain, String filename, String savePath) throws Exception {
        // 构造URL
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException exception) {
            try {
                url = new URL("http:" + urlString);
            } catch (MalformedURLException e) {
                url = new URL("https:" + urlString);
            }
        }
//        System.out.println(url);
        // 打开连接
        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
        httpcon.addRequestProperty("Referer", "http:" + domain);
        //设置请求超时为5s
        httpcon.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = httpcon.getInputStream();
        BufferedImage image = ImageIO.read(is);
        if (image == null) {
            logger.error("图片【{}】无法读取", url);
            return "";
        }
        if (image.getWidth() < minWidthOfDownloadPic || image.getWidth() > maxWidthOfDownloadPic
                || image.getHeight() < minHeightOfDownloadPic || image.getHeight() > maxHeightOfDownloadPic) {
            logger.warn("图片【{}】尺寸不符合要求，{} * {}", urlString, image.getWidth(), image.getHeight());
            return "";
        }
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename);

        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();

        return savePath + "/" + filename;
    }

    /**
     * 下载图片至本地并返回对应的本地url
     *
     * @param url
     * @return
     */
    public String getUrlAfterDownload(String url, String domain) {
        String imageName = imageName() + ".jpg";    //图片名称
        Date date = Calendar.getInstance().getTime();
        String path = new File("").getAbsolutePath() + "/DownloadFiles/pic/";
        path += new SimpleDateFormat("yyyyMM/dd/").format(date);
        String localUrl = "";
        try {
            localUrl = download(url, domain, imageName, path);   //下载图片到本地（见下面）
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localUrl;   //根据下载的文件创建新文件
    }

    @Override
    public String toString() {
        return "DownloadUtil{" +
                "minWidthOfDownloadPic=" + minWidthOfDownloadPic +
                ", maxWidthOfDownloadPic=" + maxWidthOfDownloadPic +
                ", minHeightOfDownloadPic=" + minHeightOfDownloadPic +
                ", maxHeightOfDownloadPic=" + maxHeightOfDownloadPic +
                '}';
    }
}
