package com.jo.cch.helper;

import android.content.Context;
import android.text.TextUtils;

import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.db.SpUtil;
import com.jo.cch.sql.LearnLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDataHelper {

    public static Map<String,Map<String, List<WordInfo>>> datas = new HashMap<String,Map<String, List<WordInfo>>>();

    public static Map<String,Map<String, Map<String,Map<String,List<String>>>>> dataxs = new HashMap<>();

    static {
        //一年级
        Map<String, List<WordInfo>> oneMap = new HashMap<>();
        oneMap.put("上册写字表",getOneGradeU(1));
        oneMap.put("上册识字表",getOneGradeU(2));
        oneMap.put("下册写字表",getOneGradeD(1));
        oneMap.put("下册识字表",getOneGradeD(2));
        datas.put("一年级", oneMap);
        //二年级
        Map<String, List<WordInfo>> twoMap = new HashMap<>();
        twoMap.put("上册写字表",getTwoGradeU(1));
        twoMap.put("上册识字表",getTwoGradeU(2));
        twoMap.put("下册写字表",getTwoGradeD(1));
        twoMap.put("下册识字表",getTwoGradeD(2));
        datas.put("二年级", twoMap);

        Map<String, Map<String,Map<String,List<String>>>> shangceMap = new HashMap<>();

        Map<String,Map<String,List<String>>> kechengMap = new HashMap<>();

        Map<String,List<String>> shengziMap1 = new HashMap<>();
        shengziMap1.put("识", Arrays.asList(new String[]{"天","地","人","你","我","他"}));

        kechengMap.put("第一课：天地人", shengziMap1);

        Map<String,List<String>> shengziMap2 = new HashMap<>();
        shengziMap2.put("写", Arrays.asList(new String[]{"一","二","三","上"}));
        shengziMap2.put("识", Arrays.asList(new String[]{"一","二","三","四","五","上","下"}));

        kechengMap.put("第二课：金木水火土",shengziMap2);

        Map<String,List<String>> shengziMap3 = new HashMap<>();
        shengziMap3.put("写", Arrays.asList(new String[]{"口","耳","目","手"}));
        shengziMap3.put("识", Arrays.asList(new String[]{"口","耳","目","手","足","站","坐"}));

        kechengMap.put("第三课：口耳目",shengziMap3);

        shangceMap.put("上册",kechengMap);

        dataxs.put("一年级", shangceMap);
    }

    public static List<WordInfo> getDatas(Context context, String groupKey, String childKey){
        if("易错字".equals(childKey)){
            return SpUtil.getInstance(context).getList(groupKey, WordInfo.class);
        }else{
            return datas.get(groupKey).get(childKey);
        }
    }

    private static List<WordInfo> getOneGradeU(int type){
        String xzb = "一二三上口目耳手日田禾火虫云山八十了子人大月儿头里可东西天四是女开水去来不小少牛果鸟早书刀尺本木林土力心中五立正在后我好长比巴把下个雨们问有半从你才明同学自己衣白的又和竹牙马用几只石多出见对妈全回工厂";
        String szb = "天地人你我他一二三四五上下口耳目手足站坐日月水火山石田禾对云雨风花鸟虫六七八九十爸妈马土不画打棋鸡字词语句字桌纸文数学音乐妹奶白皮小桥台雪儿草家是车羊走也秋气了树叶片大飞会个的船两头在里看见闪星江南可采莲鱼东西北尖说春青蛙夏弯地就冬男女开关正反远有色近听无声去还来多少黄牛只猫边鸭苹果杏桃书包尺作业本笔刀课早校明力尘从众双木林森条心升国旗中红歌起么美丽立午夜昨今年影前后黑狗左右它好朋友比尾巴谁长短把伞兔最公写诗点要过给当串们以成数彩半空问到方没更绿出长睡那海真老师吗同什才亮时候觉得自己很穿衣服快蓝又笑着向和贝娃挂活金哥姐弟叔爷群竹牙用几步为参加洞着乌鸦处找办旁许法放进高住孩玩吧发芽爬呀久回全变工厂医院生";
        return type == 1 ? createWordList(xzb) : createWordList(szb);
    }

    private static List<WordInfo> getOneGradeD(int type){
        String xzb = "春冬雪花飞入姓什么双国方青清气晴情请生字左右红时动万吃叫主江住没以会走北京门广过各种样伙伴这太阳校金秋因为他河说也地听哥单居招呼快乐玩很当音讲行许思床前光低故乡色外看爸晚笑再午节叶米真分豆那着到高兴千成间迷造运池欢网古凉细夕李语香打拍跑足声身体之相近习远玉义首采无树爱尖角亮机台放鱼朵美直呀边呢吗吧加文次找平办让包钟元洗共已经坐要连百还舌点块非常往瓜进空病医别干奇七星吓怕跟家羊象都捉条爬姐您草房";
        String szb = "霜吹落降飘游池入姓氏李张古吴赵钱孙周王官清晴眼睛保护害事情请让病相遇喜欢怕言互令动万纯净阴雷电阵冰冻夹吃忘井村叫毛主席乡亲战士面想告诉路京安门广非常壮观接觉再做各种样梦伙伴却趣这太阳道送忙尝香甜温暖该颜因辆匹册支铅棵架块捉急直河行死信跟忽喊身只窝孤单种都邻居招呼静乐怎独跳绳讲得羽球戏排篮连运夜思床光疑举望低故胆敢往外勇窗乱偏散原像微端粽节总米间分豆肉带知据念虹座浇提洒挑兴镜拿照千裙眉鼻嘴脖臂肚腿脚蜻蜓迷藏造蚂蚁食粮蜘蛛网圆严寒酷暑凉晨细朝霞夕杨操场拔拍跑踢铃热闹锻炼体之初性善习教迁贵专幼玉器义饭能饱茶泡轻鞭炮首踪迹浮萍泉流爱柔荷露角珠摇躺晶停机展透翅膀唱朵腰坡沉伸潮湿呢空闷消息搬响棍汤扇椅荧牵织斗具次丢哪新每平她些仔检查所钟元迟洗背刚叹共汽决定已经物虎熊通注意遍百舌鬼脸准第猴结掰扛满扔摘捧瓜抱蹦追吵胖岁现票交弓甘棉娘治燕别干然奇颗瓢碧吐啦咕咚熟掉吓鹿逃命象野拦领壁墙蚊咬断您拨甩赶房傻转卫刷梳巾擦皂澡盆";
        return type == 1 ? createWordList(xzb) : createWordList(szb);
    }

    private static List<WordInfo> getTwoGradeU(int type){
        String xzb = "两哪宽顶眼睛肚皮孩跳变极片傍海洋作坏给带法如脚它娃她毛更知识处园桥群队旗铜号领巾杨壮桐枫松柏棉杉化桂歌写丛深六熊猫九朋友季吹肥农事忙归戴辛苦称柱底杆秤做岁站船然画幅评奖纸报另及拿并封信今支圆珠笔灯电影哄先闭脸沉发窗楼依尽黄层照炉烟挂川南部些巨位每升闪狗湾名胜迹央丽展现披份坡枝起蓉老收城市利井观沿答渴喝话际面阵朗枯却将纷夜棵谢想盯言邻治怪洪灾难道年认被业产扁担志伍师军战士忘泼度龙炮穿向令危敢惊阴似野苍茫于论岸屋切久散步唱赶旺旁浑候谁轻汽食物爷就爪神活猪奶始吵仔急咬第公折张祝扎抓但哭车得秧苗汗场伤路";
        String szb = "塘脑袋灰哇教捕迎阿姨宽龟顶披鼓晒极傍越滴溪奔洋坏淹没冲毁屋猜植如为旅备纷刺底炸离察识粗得套帽登鞋裤图壶帐篷指针帆艘军舰稻园翠队铜号梧桐掌枫松柏装桦耐守疆银杉化桂世界孔雀锦雄鹰翔雁丛深猛灵休季蝴蝶麦苗桑肥农归戴场谷粒虽辛苦了葡萄紫狐狸笨酸曹称员根柱议论重杆秤砍线止量玲详幅评奖催啪脏报另及懒并糟肯封削锅朝刮胡修冷肩团重完期结束鲜哄先梦闭紧润等吸发粘汗额沙乏弹钢琴捏泥滚铁环荡滑梯楼依尽欲穷层瀑布炉烟遥川闻名景区省部秀尤其仙巨位都著形状潭湾湖绕茂盛围胜央岛纱童境引客沟产梨份枝搭淡好够收城市干留钉利分味昌铺调硬卧限乘售沿答渴喝话弄错际哪抬号堵缝当鹊朗衔枯劝趁将且腊狂吼复哀葫芦藤谢蚜盯啊赛感怪慢锋蜜蜂幕扫墓慕抄炒洪毒蛇兽伤灾难仍退继续认训被恢朱德扁担志伍泽敌陡难争仗疼料敬泼族民度敲龙驶容踩铺盛碗祝福健康寿轿救摩托防渔货油轮科考宿寺危辰恐惊似庐笼盖苍茫雾淘于暗岸街梁甚至切躲失累添柴烧旺渐哎呀冒烫终浑淋灭激滩椰壳漠骆驼骏悬崖假威转扯嗓派违抗爪趟神猪纳闷受骗借酪捡俩始拌帮匀嚷瞧便剩整筝鼠折漂扎抓幸但愿哭取助抽使劲哗秧拉表示摆翻栽责狼猩鹤鸽羚蚯蚓螃蟹蚕";
        return type == 1 ? createWordList(xzb) : createWordList(szb);
    }

    private static List<WordInfo> getTwoGradeD(int type){
        String xzb = "诗村童碧妆绿丝剪冲寻姑娘吐柳荡桃杏鲜邮递员原叔局堆礼邓植格引注满休息锋昨冒留弯背洒温暖能桌味买具甘甜菜劳匹妹波纹像景恋舍求州湾岛峡民族谊齐奋贴街舟艾敬转团热闹贝壳甲骨钱币与财关烧茄烤鸭肉鸡蛋炒饭彩梦森拉结苹般精灵伞姨弟便教游戏母周围句补充药合死记屁股尿净屎幸使劲亡牢钻劝丢告筋疲图课摆座交哈页抢嘻愿意麦该伯刻突掉湖莲穷荷绝含岭吴雷乌黑压垂户迎扑指针帮助导永碰特积宇宙杯失板容易浴室扇慢遇兔安根痛最店决定商夫终完换期蛙卖搬倒籽泉破应整抽纺织编怎布消祖啊浓望蓝摘掏赛忆世界功反复式简弄由觉值类艰弓炎害此新";
        String szb = "莺拂堤柳醉咏妆丝绦裁剪脱袄寻羞姑遮掩探嫩符解触杜鹃邮递裹寄局堆破漏懊丧啊猬绚籽礼邓坛龄格握致勃挖选茁移挥填扶亭咨询剧管理宝塔餐厅曾蒙泞顺迈踏荆棘瓣莹觅需献糕特嘛买粉糖蔗汁菜熬算销劳的确应郊泛波纹葱软毯异恋舍求株拾骑跨程魔术建筑演营务判饲养州华涌峰耸隔峡与陆谊浓齐奋繁荣传统贴宵巷祭舟艾堂乞巧郎饼赏菊甲骨类漂珍饰品随易损币财赚赔购贫菠煎腐茄烤煮爆炖蘑菇蒸饺炸酱粥蛋津溜辣乎喷腻绵脆邦盒聊坪郁囱般精叮咛渡荫蔽撑拼母冈懂案堡插凶狠补充攻商量驾轰驳药赞合记屁股昏泡尿茸醒晃免费列屎撞贪脾婶陀螺毽倒翁枪橡板控坦克寓则亡牢圈钻叼坊悔此焦筋疲喘截室靠而班哈倒审视页肃晌抢嘻悦诲棚驮磨坊挡伯浅刻突叹唉试蹄既厨厕厢厦穴窟窿窑窄晓慈毕竟映绝鹂鸣行含岭泊压蝉垂户扑慌辨忠实导盏永闯碰稠稀渠积航宇宙稳固舱杯饮件题密浴桶博馆览技育研究哨诊似耷咦竖竿舞痛烦扇店蹲寂寞罩编顾付夫换颈袜匆蜈蚣卖烂牌喝坑挺舒集播撒茵灌缺泳愣昆怜挪仿佛尽任何纺竭规律待挣愉绒扫帚抹拖簸箕玻璃垃圾祖掏逗蔷薇逮忆纪必须功譬糙敏式简由睁秩序哦射值熔艰箭裂窜炎庄稼滋腾钩铲梅柿源涨炬灿垮坟";
        return type == 1 ? createWordList(xzb) : createWordList(szb);
    }


    private static List<WordInfo> createWordList(String word){
        List<WordInfo> rs = new ArrayList<>();
        String[] words = toStringArray(word);
        for(String w : words){
            rs.add(new WordInfo(w));
        }
        return rs;
    }

    public static void addErrorWord(Context context, String key, String errorWord){
        boolean flag = true;
        List<WordInfo> list = SpUtil.getInstance(context).getList(key,WordInfo.class);
        for(WordInfo w : list){
            if(errorWord.equals(w.getWord())){
                flag = false;
                break;
            }
        }
        if(flag){
            list.add(new WordInfo(errorWord));
        }
        SpUtil.getInstance(context).saveList(key,list);
    }

    public static void addItemLog(Context context, String key, LearnLog log){
        boolean flag = true;
        List<LearnLog> list = SpUtil.getInstance(context).getList(key,LearnLog.class);
        for(LearnLog l : list){
            if(log.getId().equals(l.getId())){
                flag = false;
                break;
            }
        }
        if(flag){
            list.add(log);
        }
        SpUtil.getInstance(context).saveList(key,list);
    }

    public static String[] toStringArray(String text){
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        String[] result = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = String.valueOf(text.charAt(i));
        }
        return result;
    }
}
