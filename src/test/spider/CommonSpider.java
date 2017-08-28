package spider;

import com.gs.spider.utils.SpiderExtractor;
import com.overzealous.remark.Remark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonSpider {
    public static void main(String[] args) {

        System.out.println(new Remark().convert(" <h1>LCK冒泡赛前瞻：熟悉的配方和熟悉的味道</h1>\n" +
                "  <div class=\"textdetail\" id=\"textdetail\">\n" +
                "    <h4>2017-8-27 3:54:03 &nbsp;&nbsp; 文章来源：UUU9游久网 &nbsp;&nbsp; 作者：宁语诺</h4>\n" +
                "    <div class=\"Introduction\"><strong>导读</strong>LCK冒泡赛前瞻：还是熟悉的老面孔\n" +
                "\n" +
                "</div>\n" +
                "    <script type='text/javascript' src='http://js.feitian001.com/js/c/10883_1934.js'></script> \n" +
                "  <p>随着两个直升名额决出，LCK冒泡赛即将打响，MVP与Afs为第一轮争夺者，胜者与SSG争夺下一个参赛席位，而积分最高的KT在冒泡赛守门。虽然这其中有些剧本看起来似曾相识，可是对于LCK冒泡赛，谁都没有信心下百分之百的定律，毕竟S4夏季赛冠军KTA在冒泡赛守门却被Najin白盾完成了从NBL开始的一串四;去年在冒泡赛守门的KT，在面对SSG有19-0的历史战绩领先却最终输掉了名额，在冒泡赛的舞台上太多的不可思议与匪夷所思曾出现过，因此每个BO5都充满新鲜感，值得期待。</p>\n" +
                "<p><font color=\"#0000ff\">(后文附带结果预测，让打脸来的更猛烈些吧!)</font></p>\n" +
                "<p><strong><font color=\"#ff0000\">第一战：Afs VS MVP</font></strong></p>\n" +
                "<p>MVP在整个夏季赛完美的化身为了大艺术家，旨在阵容的尝试，而不在比赛的胜负。摒弃了自己春季赛的稳定性在夏季赛进行了多种尝试，这也是他们成绩不好的原因。从联赛以及洲际赛的表现来看，他们与进入季后赛的队伍还存在着明显的差距，能看出LCK赛区的梯度差距还是十分鲜明的。</p>\n" +
                "<center><img onclick=\"gonext();\" title=\"点击图片翻页\" class=\"\" style=\"cursor: hand\" width=\"550\" height=\"308\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol//UploadFiles//201708/_Z201708270355338751.jpg\" /></center>\n" +
                "<p>而Afs，在这个上野的版本，至少对于LCK赛区是这样。他们应该能够发挥的更加得心应手，但实际上对于后续的名额进程争夺依旧不容乐观。</p>\n" +
                "<p>获胜队伍预测：Afs</p>\n" +
                "<p><strong><font color=\"#ff0000\">第二关：SSG</font></strong></p>\n" +
                "<p>SSG在整个夏季赛表现的都非常不错，版本来到了属于Ambiton的食草性打野版本，因此SSG在BP和首发人员选择上都需要因地制宜，选择难度最小的阵容，不要轻易就用Hard模式为自己打开局面。同时不得不说，Crown的压力太大了，上野必须站起来承担队伍的节奏;在BP过程中不要过于依赖发条。</p>\n" +
                "<center><img onclick=\"gonext();\" title=\"点击图片翻页\" class=\"\" style=\"cursor: hand\" width=\"550\" height=\"343\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol//UploadFiles//201708/_Z201708270356017181.jpg\" /></center>\n" +
                "<p>在季后赛中SKT通过了对中路的压制打通了SSG的命门赢下了比赛，但Afs与MVP的中单选手在面对Crown的时候，还没有这样恐怖的能力，这也是SSG能够比较放心的一点，或许他们可以选择让对方Ban发条，再度给到Crown在前期能够打出压制或者带动全场节奏的英雄，发条在支援上来的太过缓慢反而容易丧失中路防御塔。</p>\n" +
                "<p>获胜队伍预测：SSG</p>\n" +
                "<p><strong><font color=\"#ff0000\">第三关：KT</font></strong></p>\n" +
                "<p>实际上KT走到这一步也是情非得已，在常规赛时他们曾经有机会锁定联赛第一的宝座进而拿下直升名额，可是却倒在了SKT手中，见证了SKT的状态回暖，最终小分不够将第一拱手让人;在季后赛的他们可以战胜SKT进入决赛凭借积分拿到直升名额，可是却被SKT上演了似曾相识的让二追三;最后他们还能通过SKT获得冠军躺进世界赛，可是SKT也没有。在KT前行的路上为什么总是横着一个SKT呢?</p>\n" +
                "<center><img onclick=\"gonext();\" title=\"点击图片翻页\" class=\"\" style=\"cursor: hand\" width=\"550\" height=\"331\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol//UploadFiles//201708/_Z201708270356112031.jpg\" /></center>\n" +
                "<p>不过在冒泡赛中拦路虎终于走了，从季后赛的表现来看，KT确实是实力最强，表现最好的一支队伍，至少他们不存在着对线的短板，需要遏制KT的节奏必须对上中野节奏进行针对，单独一路的针对无法击破这支银河战舰。但是考虑到季后赛为7.15版本，冒泡赛应该是7.16版本，中路英雄有一个大范围AOE一般的削弱，因此在中路英雄的选择上，Pawn能否轻易拿到带动前期节奏的英雄并打出之前季后赛中的统治力与对线能力，还是一个值得思考深思的问题，不得不提：在于SKT的交锋中，即使处于劣势局面，Pawn的乐芙兰依旧打出了大量的伤害，并随时试图找机会帮助队伍扳平经济。</p>\n" +
                "<center><img onclick=\"gonext();\" title=\"点击图片翻页\" class=\"\" style=\"cursor: hand\" width=\"550\" height=\"338\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol//UploadFiles//201708/_Z201708270356204061.jpg\" /></center>\n" +
                "<p>最让对手忌惮的还是KT的下路，不论前期是否遏制了上中野节奏，在中期团战都有被Deft翻盘的经历，LZ与SSG都曾经在KT的下路组合面前翻车，因此决定KT最终能否出线，Deft团战的发挥也非常重要。</p>\n" +
                "<p><strong><font color=\"#ff0000\">预测：有了前车之鉴此处应该沉默</font></strong></p>\n" +
                "      <p class=\"article\" align=\"center\"><strong><font color=\"#000000\" size=\"3\"><a target=\"_blank\" href=\"http://www.uuu9.com/gamelive/\"><img style=\"cursor: hand\" title=\"点击图片翻页\" onclick=\"gonext();\" alt=\"\" src=\"http://image.uuu9.com/pcgame/lol/UploadFiles/201707/201707171139396561.png\" /></a></font></strong></p>\n"));
    }
}