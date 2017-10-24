package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import tv.duojiao.utils.spider.PageExtractor;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/20
 */
public class TestPageExtractor {
    private PageExtractor pageExtractor;
    private String testStr4ExtractImage;

    @Before
    public void initVal() {
        pageExtractor = new PageExtractor();
    }

    @Test
    public void testExtractImage() {
        testStr4ExtractImage = "<div class=\"textdetail\" id=\"content\">\n" +
                "                        <div class=\"img_321321321321\" style=\"display: none;\">\n" +
                "<p><img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375498402.JPG\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375498402.JPG\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375620313.JPG\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375620313.JPG\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375678124.JPG\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375678124.JPG\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375728135.JPG\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375728135.JPG\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375790646.JPG\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375790646.JPG\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375851557.JPG\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375851557.JPG\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375915668.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375915668.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375982879.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375982879.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z20171013103800375810.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/20171013103800375810.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z20171013103800953911.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/20171013103800953911.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038017811012.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038017811012.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038023901113.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038023901113.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038030311214.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038030311214.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038038281315.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038038281315.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038048121416.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038048121416.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038054061517.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038054061517.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038059681618.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038059681618.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038068431719.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038068431719.jpg\">看大图</a><br>\n" +
                "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z201710131038074061820.jpg\"><br>\n" +
                "<a style=\"text-decoration: underline\" target=\"_blank\" href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038074061820.jpg\">看大图</a></p>\n" +
                "</div><div><div class=\"nph_photo nph_skin_white\" id=\"PhotoGallery_0_92\"><div class=\"bigpic_ck\" id=\"originPicture_0_92\"><a href=\"/viewpic.htm?url=http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375498402.JPG\" target=\"_blank\">查看大图</a></div><div class=\"nph_photo_view\">    <div id=\"photoView_0_92\" class=\"nph_cnt\" style=\"width: 880px; margin: 0px;\"><img src=\"http://dev.uuu9.com/tuji/img/loading.gif\">    </div>    <div class=\"nph_photo_prev\"><a target=\"_self\" id=\"preArrow_0_92\" class=\"nph_btn_pphoto\" hidefocus=\"true\"></a>    </div>    <div class=\"nph_photo_next\"><a target=\"_self\" id=\"nextArrow_0_92\" class=\"nph_btn_nphoto\" hidefocus=\"true\"></a>    </div>    <div id=\"photoLoading_0_92\" class=\"nph_photo_loading hidden\" style=\"display: block;\">    </div></div><div class=\"nph_cnt\" style=\"margin-left: 0px; margin-right: 0px;\">    <div><span class=\"nph_set_cur\"><strong>（<span id=\"photoIndex_0_92\" class=\"nph_c_lh\">1</span>/<em id=\"photoCount_0_92\">19</em>）</strong></span></div>    <div id=\"photoDesc_0_92\" class=\"nph_photo_desc\"><h2></h2></div></div><span class=\"nph_hr_solid\"></span><div class=\"nph_cnt clearfix\" style=\"margin-left: 0px; margin-right: 0px;\">    <div class=\"nph_photo_thumb\" style=\"width: 905px;\"><div class=\"clearfix\">    <div class=\"nph_scrl\"><div class=\"nph_scrl_thumb\">    <div class=\"nph_scrl_main\" id=\"areaThumb_0_92\" style=\"width: 846px;\"><ul id=\"thumb_0_92\" class=\"nph_list_thumb\" style=\"width: 2147.25px;\"><li i=\"0\" class=\"nph_list_active\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375498402_s.JPG\"></a></li><li i=\"1\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375620313_s.JPG\"></a></li><li i=\"2\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375678124_s.JPG\"></a></li><li i=\"3\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375728135_s.JPG\"></a></li><li i=\"4\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375790646_s.JPG\"></a></li><li i=\"5\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375851557_s.JPG\"></a></li><li i=\"6\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375915668_s.jpg\"></a></li><li i=\"7\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/2017101310375982879_s.jpg\"></a></li><li i=\"8\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/20171013103800375810_s.jpg\"></a></li><li i=\"9\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/20171013103800953911_s.jpg\"></a></li><li i=\"10\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038017811012_s.jpg\"></a></li><li i=\"11\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038023901113_s.jpg\"></a></li><li i=\"12\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038030311214_s.jpg\"></a></li><li i=\"13\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038038281315_s.jpg\"></a></li><li i=\"14\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038048121416_s.jpg\"></a></li><li i=\"15\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038054061517_s.jpg\"></a></li><li i=\"16\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038059681618_s.jpg\"></a></li><li i=\"17\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038068431719_s.jpg\"></a></li><li i=\"18\"><a><img src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710131038074061820_s.jpg\"></a></li></ul>    </div>    <div class=\"nph_scrl_bar clearfix\"><span class=\"nph_scrl_lt\"></span><span class=\"nph_scrl_rt\"></span><div class=\"nph_scrl_bd\">    <div class=\"nph_scrl_ct\" id=\"dragAreaBar_0_92\" style=\"width: 840px;\"><div id=\"dragBar_0_92\" class=\"nph_btn_scrl\" style=\"width:100px; left:0;\">    <b class=\"nph_btn_lt\"></b><b class=\"nph_btn_rt\"></b><span class=\"nph_btn_bd\"><span><b class=\"nph_btn_ct\"></b></span></span></div>    </div></div>    </div></div>    </div>    <span class=\"nph_scrl_prev\"><a id=\"photoPer_0_92\" class=\"nph_btn_pscrl\"></a></span>    <span class=\"nph_scrl_next\"><a id=\"photoNext_0_92\" class=\"nph_btn_nscrl\">    </a></span></div>    </div></div><iframe id=\"tmp_downloadhelper_iframe\" style=\"display: none;\"></iframe></div></div>\n" +
                "\t\n" +
                "\t\t                <div class=\"bshare-custom m-10\" align=\"center\">        \n" +
                "<p style=\"text-align:center; height:10px; margin-top:10px;\">Ti7国际邀请赛专题报道:<a target=\"_blank\" href=\"http://dota2.uuu9.com/ti/\">http://dota2.uuu9.com/ti/</a></p> \n" +
                "\n" +
                "<p style=\"text-align:center; height:10px; margin-top:10px;\">更多内容：\n" +
                "<a target=\"_blank\" href=\"http://dota2.uuu9.com/v/\">dota2视频</a>  \n" +
                "<a target=\"_blank\" href=\"http://es.dota2.uuu9.com/\">dota2赛事</a> \n" +
                "</p> \n" +
                "                        </div>\n" +
                "                        <!--\n" +
                "                        <div style=\"width:900px; height:60px; margin:10px auto 0\">  \n" +
                "\t\t\t\t\t\t\t<script type=\"text/javascript\" id=\"adm-59\">\n" +
                "                            (function() {\n" +
                "                              window.ADMBlocks = window.ADMBlocks || [];\n" +
                "                              ADMBlocks.push({\n" +
                "                                id\t\t: '59',  // 广告位id\n" +
                "                                width\t: '900',  // 宽\n" +
                "                                height\t: '60'  // 高\n" +
                "                              });\n" +
                "                              var h=document.getElementsByTagName('head')[0], s=document.createElement('script');\n" +
                "                              s.async=true; s.src='http://ad.uuu9.com/js/show.js';\n" +
                "                              h && h.insertBefore(s,h.firstChild)\n" +
                "                            })()\n" +
                "                            </script>\n" +
                "                        </div>\n" +
                "                        -->\n" +
                "                    </div>";
        String testStr2 = "<div class=\"textdetail\" id=\"content222\">\n" +
                "                            <p>在昨天进行的PGL首日比赛中，来自中国的LGD战队先是二比零横扫巴西的INF为中国队拿到新赛季的首胜，之后面对着刚刚血虐秘密的韩国队IMT，LGD让一追二，拿到了A组的小组第一顺利出线。</p>\n" +
                "<p align=\"center\"><img style=\"cursor: hand\" class=\"\" title=\"点击图片翻页\" onclick=\"gonext();\" width=\"543\" height=\"202\" alt=\"\" src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710201018281711.jpg\"></p>\n" +
                "<p align=\"left\">在第一局比赛中，LGD拿出了<a href=\"http://db.dota2.uuu9.com/hero/show/ST\" target=\"_blank\" class=\"u9kwlk\">蓝猫</a>和三号位大圣，IMT非常针对maybe，多次来中路健身，不过maybe补刀不落下风，不过无奈IMT的流浪实在太肥，几波团战之后LGD渐渐处于了下风，然而LGD并没有破罐破摔而是一直找机会，在IMT一次上高时秒掉SVEN打了对面一个团灭扳回了一点局势，不过最后一波肉山团IMT完美发挥团灭了LGD，LGD买活后不敌打出GG。整场下来虽然IMT都处于上风，但是LGD一直都在找机会，站着死不丢人。不过这里得说一句，三号位大圣真的有毒，慎拿。</p>\n" +
                "<p align=\"center\"><img style=\"cursor: hand\" class=\"\" title=\"点击图片翻页\" onclick=\"gonext();\" width=\"514\" height=\"194\" alt=\"\" src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710201022557501.jpg\"></p>\n" +
                "<p align=\"left\">第二局LGD转换阵形，拿出了一套比较灵活的大屁股体系，maybe的绝活疯脸白虎发挥出色，十几分钟就超神了，后期团战多次IMT买活想反打，无奈LGD众人上了大屁股的车，只能干生气，最后不到三十分钟IMT就打出了GG。</p>\n" +
                "<p align=\"center\"><img style=\"cursor: hand\" class=\"\" title=\"点击图片翻页\" onclick=\"gonext();\" width=\"516\" height=\"172\" alt=\"\" src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710201040051711.jpg\"></p>\n" +
                "<p align=\"left\">决胜局LGD拿出了一套更灵动的阵容，整局中Yao的土猫非常亮眼，开局就帮助maybe的蓝猫拿下<a href=\"http://db.dota2.uuu9.com/hero/show/BS\" target=\"_blank\" class=\"u9kwlk\">血魔</a>的一血，然后又转游上路帮FY多次击杀骨法，后期更是上演多次凌空抽射先手留人让人惊呼Yao GOD，简直和之前Yao的土猫判若两人。FY的<a href=\"http://db.dota2.uuu9.com/hero/show/CG\" target=\"_blank\" class=\"u9kwlk\">发条</a>也打出了水准，不过AME的<a href=\"http://db.dota2.uuu9.com/hero/show/lancer\" target=\"_blank\" class=\"u9kwlk\">猴子</a>多次被对面针对没有什么声音，不过maybe的蓝猫站了出来，特别是更新血精之后，简直为所欲为，最后在maybe的连续超神中IMT打出了GG。maybe最后数据17-0-7，一次都没死。</p>\n" +
                "<p align=\"center\"><img style=\"cursor: hand\" title=\"点击图片翻页\" onclick=\"gonext();\" width=\"525\" height=\"399\" alt=\"\" src=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/201710201012125621.jpg\"></p>\n" +
                "<p align=\"left\">在人员不齐的情况下，LGD这几场打出了自己的血性，教练357宝刀不老，FY可圈可点，在AME低迷的情况下Maybe则是站了出来扛起了局面，Yao帝的发挥让人惊喜，特别是第三场的土猫，相比以前真的是进步神速，可以看出肯定是练了的。我们也祝贺LGD顺利晋级，这几场比赛看的人非常舒服，不知道比SLi上中国队的发挥高到哪里去了。希望他们接下来继续保持发挥，终结“一日王朝”的流言。</p>\n" +
                "<p align=\"left\"><strong>今日赛程：</strong></p>\n" +
                "<p>16:00 EG vs VGJ.T</p>\n" +
                "<p>19:20 Na’Vi vs Mineski</p>\n" +
                "<p>22:40 B组胜者组决赛</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p style=\"text-align:center; height:10px; margin-top:10px;\">Ti7国际邀请赛专题报道:<a target=\"_blank\" href=\"http://dota2.uuu9.com/ti/\">http://dota2.uuu9.com/ti/</a></p>\n" +
                "<p style=\"text-align:center; height:10px; margin-top:10px;\">更多内容：<a target=\"_blank\" href=\"http://dota2.uuu9.com/v/\">dota2视频</a>  <a target=\"_blank\" href=\"http://es.dota2.uuu9.com/\">DOTA2赛事</a></p> \n" +
                "\n" +
                "                        <iframe id=\"tmp_downloadhelper_iframe\" style=\"display: none;\"></iframe></div>";
        PageExtractor.filterTagsInConditions(testStr4ExtractImage);
//        PageExtractor.filterTagsInConditions(testStr2);
    }

    @Test
    public void testExtra() {
        String testStr = "<img style=\"display: none;\" title=\"点击图片翻页\" alt=\"\" onclick=\"gonext();\" onload=\"javascript:resizepic(this);\" url=\"http://image.uuu9.com/pcgame/dota2//UploadFiles//201710/_Z2017101310375498402.JPG\">";

        Elements elements = Jsoup.parse(testStr).select("img[url$=.JPG]");
        for (Element element : elements) {
            testStr = testStr.replace(element.toString(), PageExtractor.removeImgExtraTags(new Elements(element)));
        }
        System.out.println(testStr);
    }
}
