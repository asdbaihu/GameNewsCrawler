package spider;

import org.junit.Test;
import tv.duojiao.utils.SpiderExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
public class UtilTest {
    @Test
    public void testStringUtil(){
        List<String> list =  SpiderExtractor.getImageList("<div class=\"textdetail\" id=\"textdetail\">\n" +
                "    <h4>2017-9-26 14:08:44 &nbsp;&nbsp; 文章来源：游久uuu9 &nbsp;&nbsp; 作者：冥</h4>\n" +
                "    <div class=\"Introduction\"><strong>导读</strong>RPG三战全败，已经确认淘汰无法晋级入围赛第二轮；而HKA两胜一败确认晋级。\n" +
                "</div>\n" +
                "  <p>擅长打后期的RPG这次却选择了套前中期阵容，但缺少开团手段和硬控时新尝试最大的弊端。</p>\n" +
                "<p align=\"center\"><img style=\"cursor: hand\" class=\"\" title=\"点击图片翻页\" onclick=\"gonext();\" width=\"550\" height=\"321\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol//UploadFiles//201709/201709261410547031.jpg\"></p>\n" +
                "<p>习惯了日本赛区的慢节奏来到世界赛后RPG显然被打懵了，HKA前期快节奏的进攻给RPG带来巨大的冲击，比赛全程被HKA掌控。至此，RPG三战全败，已经确认淘汰无法晋级入围赛第二轮；而HKA两胜一败确认晋级。</p>\n" +
                "<p align=\"center\"><img style=\"cursor: hand\" class=\"\" title=\"点击图片翻页\" onclick=\"gonext();\" width=\"550\" height=\"332\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol//UploadFiles//201709/201709261411240151.png\"></p>\n" +
                "<p>本场比赛MVP给到了上单Riris的加里奥，他和皇子全场带动节奏帮助HKA轻松取胜，比赛后期加里奥的坦度完全不惧怕对方的集火。</p>\n" +
                "<p>&nbsp;</p>\n" +
                "                                              <p class=\"article\" align=\"center\">&nbsp;</p>\n" +
                "<p class=\"article\" align=\"center\">&nbsp;<strong><font color=\"#000000\" size=\"3\"><a target=\"_blank\" href=\"http://www.uuu9.com/gamelive/\"><img style=\"cursor: hand\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" src=\"http://image.uuu9.com/pcgame/lol/UploadFiles/201707/201707171139396561.png\"></a></font></strong></p>\n" +
                "<!--\n" +
                "\n" +
                "<p class=\"article\" align=\"center\"><strong><font color=\"#000000\" size=\"3\">【</font></strong><font color=\"#000000\" size=\"3\"><strong> <a target=\"_blank\" href=\"http://lol.uuu9.com/201506/493941.shtml\">招募LOL原创写手 巨额现金等你拿</a></strong></font><font color=\"#000000\"><strong><font size=\"3\">】</font></strong><font size=\"3\"><strong>【</strong></font></font><font color=\"#000000\" size=\"3\"><strong> <a target=\"_blank\" href=\"http://hao.uuu9.com/\">主播直播预告 游久直播导航</a></strong></font><font color=\"#000000\" size=\"3\"><strong>】</strong></font></p>\n" +
                "<center>\n" +
                "\n" +
                "-->\n" +
                " <script type=\"text/javascript\" src=\"http://www.uuu9.com/adpic/dj600.js\"></script>\n" +
                "\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"http://js.feitian001.com/js/c/10964_2101.js\"></script>\n" +
                "                                        \n" +
                " \n" +
                "                 <script type=\"text/javascript\">\n" +
                "                            var sUserAgent = navigator.userAgent.toLowerCase();\n" +
                "                            var bIsIpad = sUserAgent.match(/ipad/i) == \"ipad\";\n" +
                "                            var bIsIphoneOs = sUserAgent.match(/iphone os/i) == \"iphone os\";\n" +
                "                            var bIsMidp = sUserAgent.match(/midp/i) == \"midp\";\n" +
                "                            var bIsAndroid = sUserAgent.match(/android/i) == \"android\";\n" +
                "                            var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == \"rv:1.2.3.4\";\n" +
                "                            var bIsCE = sUserAgent.match(/windows ce/i) == \"windows ce\";\n" +
                "                            var bIsWM = sUserAgent.match(/windows mobile/i) == \"windows mobile\";\n" +
                "                            var bIsUc = sUserAgent.match(/ucweb/i) == \"ucweb\";\n" +
                "\n" +
                "                            if (bIsIpad || bIsIphoneOs) {\n" +
                "                                // ipad || iphone\n" +
                "                                jQuery(\"embed\").each(function (count) {\n" +
                "                                    jQuery(this).css(\"display\", \"none\");\n" +
                "                                    var wzUrl = jQuery(this).attr(\"src\");\n" +
                "                                    var clUrl = wzUrl.substring(wzUrl.lastIndexOf(\"sid/\") + 4, wzUrl.lastIndexOf(\"/\"));\n" +
                "                                    var height = jQuery(this).attr(\"height\");\n" +
                "                                    var width = jQuery(this).attr(\"width\");\n" +
                "                                    jQuery(this).parent().append().html('<video id=\"sampleMovie\" height=\"' + height + '\" width=\"' + width + '\" src=\"http://v.youku.com/player/getRealM3U8/vid/' + clUrl + '/type/video.m3u8\" controls></video>');\n" +
                "                                });\n" +
                "                            }\n" +
                "                      \n" +
                "                        </script>\n" +
                "  </div>");
        System.out.println(list.toString());
    }
}
