package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OneDBooKData extends YuWenData {
    @Override
    public TreeMap<Integer, String> getCourses() {
        TreeMap<Integer, String> courseMap = new TreeMap<>();
        //初始化课程
        courseMap.put(1, "1、春夏秋冬");
        courseMap.put(2, "2、姓氏歌");
        courseMap.put(3, "3、小青蛙");
        courseMap.put(4, "4、猜字谜");
        courseMap.put(5, "语文园地一");
        courseMap.put(6, "1、吃水不忘挖井人");
        courseMap.put(7, "2、我多想去看看");
        courseMap.put(8, "3、一个接一个");
        courseMap.put(9, "4、四个太阳");
        courseMap.put(10, "语文园地二");
        courseMap.put(11, "5、小公鸡和小鸭子");
        courseMap.put(12, "6、树和喜鹊");
        courseMap.put(13, "7、怎么都快乐");
        courseMap.put(14, "语文园地三");
        courseMap.put(15, "8、静夜思");
        courseMap.put(16, "9、夜色");
        courseMap.put(17, "10、端午粽");
        courseMap.put(18, "11、彩虹");
        courseMap.put(19, "语文园地四");
        courseMap.put(20, "5、动物儿歌");
        courseMap.put(21, "6、古对今");
        courseMap.put(22, "7、操场上");
        courseMap.put(23, "8、人之初");
        courseMap.put(24, "语文园地五");
        courseMap.put(25, "12、古诗二首 池上 小池");
        courseMap.put(26, "13、荷叶圆圆");
        courseMap.put(27, "14、要下雨了");
        courseMap.put(28, "语文园地六");
        courseMap.put(29, "15、文具的家");
        courseMap.put(30, "16、一分钟");
        courseMap.put(31, "17、动物王国开大会");
        courseMap.put(32, "18、小猴子下山");
        courseMap.put(33, "语文园地七");
        courseMap.put(34, "19、棉花姑娘");
        courseMap.put(35, "20、咕咚");
        courseMap.put(36, "21、小壁虎借尾巴");
        courseMap.put(37, "语文园地八");
        return courseMap;
    }

    @Override
    public Map<Integer, String> getXzNWords() {
        //课程对应的初始化写字表
        Map<Integer, String> xzNWordMap = new HashMap<>();
        xzNWordMap.put(1, "春冬雪花飞入");
        xzNWordMap.put(2, "姓什么双国方");
        xzNWordMap.put(3, "青清气晴情请生");
        xzNWordMap.put(4, "字左右红时动万");
        xzNWordMap.put(6, "吃叫主江住没以");
        xzNWordMap.put(7, "会走北京门广");
        xzNWordMap.put(8, "过各种样伙伴这");
        xzNWordMap.put(9, "太阳校金秋因为");
        xzNWordMap.put(11, "他河说也地听哥");
        xzNWordMap.put(12, "单居招呼快乐");
        xzNWordMap.put(13, "玩很当音讲行许");
        xzNWordMap.put(15, "思床前光低故乡");
        xzNWordMap.put(16, "色外看爸晚笑再");
        xzNWordMap.put(17, "午节叶米真分豆");
        xzNWordMap.put(18, "那着到高兴千成");
        xzNWordMap.put(20, "间迷造运池欢网");
        xzNWordMap.put(21, "古凉细夕李语香");
        xzNWordMap.put(22, "打拍跑足声身体");
        xzNWordMap.put(23, "之相近习远玉义");
        xzNWordMap.put(25, "首采无树爱尖角");
        xzNWordMap.put(26, "亮机台放鱼朵美");
        xzNWordMap.put(27, "直呀边呢吗吧加");
        xzNWordMap.put(29, "文次找平办让包");
        xzNWordMap.put(30, "钟元洗共已经坐");
        xzNWordMap.put(31, "要连百还舌点");
        xzNWordMap.put(32, "块非常往瓜进空");
        xzNWordMap.put(34, "病医别干奇七星");
        xzNWordMap.put(35, "吓怕跟家羊象都");
        xzNWordMap.put(36, "捉条爬姐您草房");
        return xzNWordMap;
    }

    @Override
    public Map<Integer, String> getSzNWords() {
        //课程对应的初始化识字表
        Map<Integer, String> szNWordMap = new HashMap<>();
        szNWordMap.put(1, "霜吹落降飘游池入");
        szNWordMap.put(2, "姓氏李张古吴赵钱孙周王官");
        szNWordMap.put(3, "清晴眼睛保护害事情请让病");
        szNWordMap.put(4, "相遇喜欢怕言互令动万纯净");
        szNWordMap.put(5, "阴雷电阵冰冻夹");
        szNWordMap.put(6, "吃忘井村叫毛主席乡亲战士面");
        szNWordMap.put(7, "想告诉路京安门广非常壮观");
        szNWordMap.put(8, "接觉再做各种样梦伙伴却趣这");
        szNWordMap.put(9, "太阳道送忙尝香甜温暖该颜因");
        szNWordMap.put(10, "辆匹册支铅棵架");
        szNWordMap.put(11, "块捉急直河行死信跟忽喊身");
        szNWordMap.put(12, "只窝孤单种都邻居招呼静乐");
        szNWordMap.put(13, "怎独跳绳讲得羽球戏排篮连运");
        szNWordMap.put(14, "部首音序组词");
        szNWordMap.put(15, "夜思床光疑举望低故");
        szNWordMap.put(16, "胆敢往外勇窗乱偏散原像微");
        szNWordMap.put(17, "端粽节总米间分豆肉带知据念");
        szNWordMap.put(18, "虹座浇提洒挑兴镜拿照千裙");
        szNWordMap.put(19, "眉鼻嘴脖臂肚腿脚");
        szNWordMap.put(20, "蜻蜓迷藏造蚂蚁食粮蜘蛛网");
        szNWordMap.put(21, "圆严寒酷暑凉晨细朝霞夕杨");
        szNWordMap.put(22, "操场拔拍跑踢铃热闹锻炼体");
        szNWordMap.put(23, "之初性善习教迁贵专幼玉器义");
        szNWordMap.put(24, "饭能饱茶泡轻鞭炮");
        szNWordMap.put(25, "饭能饱茶泡轻鞭炮");
        szNWordMap.put(26, "首踪迹浮萍泉流爱柔荷露角");
        szNWordMap.put(27, "珠摇躺晶停机展透翅膀唱朵");
        szNWordMap.put(28, "腰坡沉伸潮湿呢空闷消息搬响");
        szNWordMap.put(29, "棍汤扇椅荧牵织斗");
        szNWordMap.put(30, "具次丢哪新每平她些仔检查所");
        szNWordMap.put(31, "钟元迟洗背刚叹共汽决定已经");
        szNWordMap.put(32, "物虎熊通注意遍百舌鬼脸准第");
        szNWordMap.put(33, "猴结掰扛满扔摘捧瓜抱蹦追");
        szNWordMap.put(34, "吵胖岁现票交弓甘");
        szNWordMap.put(35, "棉娘治燕别干然奇颗瓢碧吐啦");
        szNWordMap.put(36, "壁墙蚊咬断您拨甩赶房傻转");
        szNWordMap.put(37, "卫刷梳巾擦皂澡盆");
        return szNWordMap;
    }

    @Override
    public int getHighLightWordNum() {
        return 6;
    }
}
