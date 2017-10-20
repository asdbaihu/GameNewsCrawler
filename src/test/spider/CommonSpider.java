package spider;

import com.google.common.hash.Hashing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import tv.duojiao.dao.CommonWebpageDAO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

public class CommonSpider {


    @Autowired
    private static CommonWebpageDAO commonWebpageDAO;

    public static void main(String[] args) {
//        System.out.println(StringUtils.isNotBlank("   d "));
//        System.out.println(PageExtractor.convertHtml2Text("<h4>2017年8月27日 9:24:41 &nbsp;&nbsp; 文章来源：伐木累 &nbsp;&nbsp; 作者：英雄联盟赛事</h4>"));
//        System.out.println(PageExtractor.getDateBySystem("发发表时间：2：2017年09月17日 11:22 来源：网络 作者：网络 来源：网络 作者：网络", null));
//        System.out.println(new StaticValue().getMaxInvalidDayOfNews());
//        System.out.println(new Remark().convert(""));
//        System.out.println(PageExtractor.getLatestDate());
//        Map count = commonWebpageDAO.countDomainByGatherTime("lol.uuu9.com");
        System.out.println(("asda                   s&nbsp;   <p><br><p><br>      asdsa />  xianzai")
                .replaceAll("( ){4,}", "    ")
                .replaceAll("(&nbsp;\\s*)+", " ")
                .replaceAll("(<br>)+", "<br>")
                .replaceAll("(<br />)+", "<br />"));

//        System.out.println(testURI());

    }

    public static String testURI() {
        Resource resource = new ClassPathResource("/staticValue.json");
        try {
            return Paths.get(resource.getURI()).toUri().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getContext(String url) {
        Page page = new Page();
        page.addTargetRequest(url);
        return page.getHtml().toString();
    }

    public static String test() {
        for (int i = 0; i <= 10; i++)
            System.out.println(Hashing.md5().hashString(i + "", Charset.forName("utf-8")).toString());
        return "";
    }

    @Test
    public void testReplace() {
        String testStr = "<div class=\"glzjshow_con\">\n" +
                "    <br/>\n" +
                "    <p>　　绝地求生饰品哪里来？饰品可以说缓解了游戏紧张的气氛，同时也满足了很多玩家爱美特立独行的心里，不过很多新手玩家对于什么饰品怎么获得不是特别清楚。所以今天小编为大家带来的便是玩家“ghjkl3325”分享的绝地求生全饰品获得方法图文介绍，感兴趣的玩家一起过来看看吧。</p>\n" +
                "    <br/>\n" +
                "    <table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:550px;\" width=\"469\">\n" +
                "        <br/>\n" +
                "        <tbody>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td colspan=\"4\" nowrap style=\"width: 469px; height: 33px; background-color: rgb(0, 204, 255);\">\n" +
                "                    <p align=\"center\"><a href=\"http://gl.ali213.net/z/29339/\" target=\"_blank\"><span style=\"font-size:11px;\">绝地求生大逃杀新手指南</span></a></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/159421.html\" target=\"_blank\">配置要求介绍</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/159959.html\" target=\"_blank\">新手生存指南</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/161335.html\" target=\"_blank\">高倍镜使用方法</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160037.html\" target=\"_blank\">画面设置教程</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:131px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160349.html\" target=\"_blank\">实用技巧汇总</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160631.html\" target=\"_blank\">按键设置指南</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160875.html\" target=\"_blank\">武器装备设定</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160377.html\" target=\"_blank\">低配优化详细教程</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:131px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/161835.html\" target=\"_blank\">快速上手指南</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-5/168665.html\" target=\"_blank\">中老司机进阶教程</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/161231.html\" target=\"_blank\">枪械配件作用大全</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/161245.html\" target=\"_blank\">吃鸡经验技巧</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td colspan=\"4\" nowrap style=\"width: 469px; height: 33px; background-color: rgb(0, 204, 255);\">\n" +
                "                    <p align=\"center\"><a href=\"http://gl.ali213.net/z/29339/\" target=\"_blank\"><span style=\"font-size:11px;\">绝地求生大逃杀疑难问题解决</span></a></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160259.html\" target=\"_blank\">fps提升方法汇总</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-5/170671.html\" target=\"_blank\">AMD CPU游戏卡解决</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-5/167659.html\" target=\"_blank\">快速解决卡顿方法</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-5/167453.html\" target=\"_blank\">游戏问题及解决方法</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-5/166741.html\" target=\"_blank\">虚拟内存不足解决</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/166053.html\" target=\"_blank\">be没有正常启动解决</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/165165.html\" target=\"_blank\">游戏报错解决方法</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width: 80px; height: 33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/163961.html\" target=\"_blank\">loading时间过长解决</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td colspan=\"4\" nowrap style=\"width: 469px; height: 33px; background-color: rgb(0, 204, 255);\">\n" +
                "                    <p align=\"center\"><a href=\"http://gl.ali213.net/z/29339//\" target=\"_blank\"><span style=\"font-size:11px;\">绝地求生大逃杀精品攻略</span></a></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:131px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/163333.html\" target=\"_blank\">游侠图文流程攻略</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/159451.html\" target=\"_blank\">全武器属性可加附件</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/159841.html\" target=\"_blank\">全装备附件属性效果</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-3/160053.html\" target=\"_blank\">武器图鉴大全</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/>\n" +
                "            <tr>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:131px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/162989.html\" target=\"_blank\">全网加速器效果实测</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/162969.html\" target=\"_blank\">最低配置要求详解</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/165093.html\" target=\"_blank\">武器/配件/医疗/防具</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/>\n" +
                "                <td nowrap style=\"width:113px;height:33px;\">\n" +
                "                    <p align=\"center\"><span style=\"font-size:11px;\"><u><a href=\"http://gl.ali213.net/html/2017-4/166005.html\" target=\"_blank\">1060显卡游戏评测</a></u></span></p>\n" +
                "                </td>\n" +
                "                <br/> </tr>\n" +
                "            <br/> </tbody>\n" +
                "        <br/> </table>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#FF0000;\"><strong>　　全饰品获得方法图文介绍</strong></span></p>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#0000FF;\">　　绝版五件套</span></p>\n" +
                "    <br/>\n" +
                "    <p style=\"text-align: center;\"><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/20171020102435878.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_20171020102435878.jpg\" style=\"max-width:550px;\"></a></p>\n" +
                "    <br/>\n" +
                "    <p>　　获得方式——游戏官网预约豪华版赠。</p>\n" +
                "    <br/>\n" +
                "    <p>　　注意：眼睁睁看着从200到5000，没啥好注意的。</p>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#0000FF;\">　　科隆绝版饰品</span></p>\n" +
                "    <br/>\n" +
                "    <p style=\"text-align: center;\"><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/20171020102436549.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_20171020102436549.jpg\" style=\"max-width:550px;\"></a></p>\n" +
                "    <br/>\n" +
                "    <p>　　获得方式——科隆箱子(限定金币购买，已绝版)+科隆钥匙+人品=好东西。</p>\n" +
                "    <br/>\n" +
                "    <p>　　注意：开出来的套装箱子里东西是一套给的。</p>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#0000FF;\">　　普通金币饰品</span></p>\n" +
                "    <br/>\n" +
                "    <p style=\"text-align: center;\"><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/20171020102437297.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_20171020102437297.jpg\" style=\"max-width:550px;\"></a><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/2017102010244024.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_2017102010244024.jpg\" style=\"max-width:550px;\"></a></p>\n" +
                "    <br/>\n" +
                "    <p>　　获得方式——金币箱子购买后随机两个箱子给你一个箱子然后开。</p>\n" +
                "    <br/>\n" +
                "    <p>　　注意：不需要钥匙。</p>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#0000FF;\">　　twitch绝版四件套</span></p>\n" +
                "    <br/>\n" +
                "    <p style=\"text-align: center;\"><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/20171020102440707.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_20171020102440707.jpg\" style=\"max-width:550px;\"></a></p>\n" +
                "    <br/>\n" +
                "    <p>　　获得方式——绑定会员twitch(我也忘了要不要会员)账号活动获得。</p>\n" +
                "    <br/>\n" +
                "    <p>　　注意：活动已结束。</p>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#0000FF;\">　　熊猫直播T恤</span></p>\n" +
                "    <br/>\n" +
                "    <p style=\"text-align: center;\"><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/20171020102443362.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_20171020102443362.jpg\" style=\"max-width:550px;\"></a></p>\n" +
                "    <br/>\n" +
                "    <p>　　获得方式——熊猫TV大主播。熊猫TV活动。王思聪私人赠送。</p>\n" +
                "    <br/>\n" +
                "    <p>　　注意：王思聪那件上写的是别开枪自己人。</p>\n" +
                "    <br/>\n" +
                "    <p><span style=\"color:#0000FF;\">　　平底锅武士服T恤</span></p>\n" +
                "    <br/>\n" +
                "    <p style=\"text-align: center;\"><a target=\"_blank\" href=\"http://www.ali213.net/showbigpic.html?http://img1.ali213.net/glpic/2017/10/20/20171020102443545.jpg\"><img src=\"http://img1.ali213.net/glpic/2017/10/20/584_20171020102443545.jpg\" style=\"max-width:550px;\"></a></p>\n" +
                "    <br/>\n" +
                "    <p>　　获得方式——日服合作网站购买。</p>\n" +
                "    <br/>\n" +
                "    <p>　　注意：需要连接外网才行。</p>\n" +
                "    <br/>\n" +
                "    <p class=\"n_show_m\" style=\"text-align: left\">更多相关资讯请关注：<a href=\"http://www.ali213.net/zt/playunknown/\" target=\"_blank\"><span style=\"color: #0130fd\">绝地求生大逃杀</span></a>\n" +
                "        <font style=\"color: #0130fd\">专题</font>\n" +
                "    </p>\n" +
                "    <br/>\n" +
                "    <p>更多相关讨论请前往：\n" +
                "        <a href=\"http://game.ali213.net/forum-1760-1.html\" target=\"_blank\">\n" +
                "            <font color=\"#0130FD\">绝地求生大逃杀论坛</font>\n" +
                "        </a>\n" +
                "    </p>\n" +
                "    <br/>\n" +
                "    <p align=\"center\">\n" +
                "        <a href=\"http://gl.ali213.net/z/29339/\" target=\"_blank\">\n" +
                "            <font color=\"#0130FD\">>>查看绝地求生大逃杀全部攻略\n" +
                "                <<</font>\n" +
                "        </a>\n" +
                "    </p>\n" +
                "    <br/>\n" +
                "</div>";
        Html html = new Html(testStr);
        String currentStr = html.xpath("//*").get().replaceAll(" +", "");
        List<String> list = html.xpath("//*/table").all();
        for (String filter : list) {
            filter = filter.replaceAll(" +", "");
            System.err.println(filter);
            currentStr = currentStr.replace(filter, "");
        }
        System.out.println(currentStr);
    }
}