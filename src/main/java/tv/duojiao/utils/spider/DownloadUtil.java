package tv.duojiao.utils.spider;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    /**
     * 图片名称生成
     *
     * @return
     */
    public static String imageName() {
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
    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();
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
    }

    /**
     * 下载图片至本地并返回对应的本地url
     *
     * @param url
     * @return
     */
    public static String getUrlAfterDownload(String url) {
        String imageName = imageName() + ".jpg";    //图片名称
        Date date = Calendar.getInstance().getTime();
        String path = new File("").getAbsolutePath() + "/DownloadFiles/pic/";
        path += new SimpleDateFormat("yyyyMM/dd/").format(date);
        try {
            download(url, imageName, path);   //下载图片到本地（见下面）
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path + imageName;   //根据下载的文件创建新文件
    }
}
