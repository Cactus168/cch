package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class ThreeUBooKData extends YuWenData {
    @Override
    public TreeMap<Integer, String> getCourses() {
        //初始化课程
        TreeMap<Integer, String> courseMap = new TreeMap<>();
        courseMap.put(1,"1、大青树下的小学");
        courseMap.put(2,"2、花的学校");
        courseMap.put(3,"3、不懂就要问");
        courseMap.put(4,"4、古诗三首山行  蹭刘景文  夜书所见");
        courseMap.put(5,"5、铺满金色巴掌的水泥道");
        courseMap.put(6,"6、秋天的雨");
        courseMap.put(7,"7、听听，秋的声音");
        courseMap.put(8,"8、卖火柴的小女孩");
        courseMap.put(9,"9、那一定会很好");
        courseMap.put(10,"10、在牛肚子里旅行");
        courseMap.put(11,"11、一块奶酪");
        courseMap.put(12,"语文园地");
        courseMap.put(13,"12、总也倒不了的老屋");
        courseMap.put(14,"13、胡萝卜先生的长胡子");
        courseMap.put(15,"14、小狗学叫");
        courseMap.put(16,"语文园地");
        courseMap.put(17,"15、搭船的鸟");
        courseMap.put(18,"16、金色的草地");
        courseMap.put(19,"17、古诗三首 望天门山 饮湖上初睛后雨 望洞庭");
        courseMap.put(20,"18、富饶的西沙群岛");
        courseMap.put(21,"19、海滨小城");
        courseMap.put(22,"20、美丽的小兴安岭");
        courseMap.put(23,"语文园地");
        courseMap.put(24,"21、大自然的声音");
        courseMap.put(25,"22、父亲、树林和鸟");
        courseMap.put(26,"23、带刺的朋友");
        courseMap.put(27,"24、司马光");
        courseMap.put(28,"25、掌声");
        courseMap.put(29,"26、灰雀");
        courseMap.put(30,"27、手术台就是阵");
        courseMap.put(31,"语文园地");
        return courseMap;
    }

    @Override
    public Map<Integer, String> getXzNWords() {
        //课程对应的初始化写字表
        Map<Integer, String> xzNWordMap = new HashMap<>();
        xzNWordMap.put(1,"晨绒球汉艳服装扮读静停粗影");
        xzNWordMap.put(2,"落荒笛舞狂罚假互所够猜扬臂");
        xzNWordMap.put(4,"寒径斜霜赠刘盖菊残君橙送挑");
        xzNWordMap.put(5,"铺泥晶紧院印排列规则乱棕迟");
        xzNWordMap.put(6,"盒颜料票飘争仙闻梨勾油曲丰");
        xzNWordMap.put(8,"柴冷旧裙怜饿乎焰蜡烛富诉离");
        xzNWordMap.put(10,"旅咱救命拼扫胃管等刚流泪算");
        xzNWordMap.put(13,"洞准备暴墙壁砍蜘蛛漂撞饱晒");
        xzNWordMap.put(17,"搭亲父沙啦响羽翠嘴悄吞哦捕");
        xzNWordMap.put(18,"蒲英盛耍喊欠钓而察拢趣喜睡");
        xzNWordMap.put(19,"断楚至孤帆饮初镜未磨遥银盘");
        xzNWordMap.put(20,"优淡浅错岩虾挺鼓数厚宝贵");
        xzNWordMap.put(21,"滨灰渔遍躺载靠栽亚夏除踩洁");
        xzNWordMap.put(22,"脑袋严实挡视线坛显材软刮库");
        xzNWordMap.put(24,"妙演奏琴柔感受激击器滴敲鸣");
        xzNWordMap.put(25,"朝雾蒙鼻总抖露湿吸猎翅膀重");
        xzNWordMap.put(26,"刺枣颗忽暗伸匆沟聪偷追腰");
        xzNWordMap.put(28,"司庭登跌众弃持");
        xzNWordMap.put(29,"掌班默腿轮投调摇晃烈勇");
        xzNWordMap.put(30,"雀郊养粉谷粒男或者冻惜肯诚");
        return xzNWordMap;
    }

    @Override
    public Map<Integer, String> getSzNWords() {
        //课程对应的初始化识字表
        Map<Integer, String> szNWordMap = new HashMap<>();
        szNWordMap.put(1, "坝汉艳扮扬读摔跤凤洁");
        szNWordMap.put(2, "荒笛罚假裳");
        szNWordMap.put(3, "背诵例圈段练糊涂呆戒厉挨楚");
        szNWordMap.put(4, "径斜赠刘残犹傲君橙橘挑");
        szNWordMap.put(5, "洼印凌增棕靴");
        szNWordMap.put(6, "钥匙缤枚勾喇叭厚曲丰");
        szNWordMap.put(7, "抖蟋蟀振韵掠吟辽阔");
        szNWordMap.put(8, "旧饿卷挣几燃焰蜡烛富晃划喷");
        szNWordMap.put(9, "缩努茎锯斧推吱拆");
        szNWordMap.put(10, "咱偷答应骨齿嚼吞胃悲咽泪眯");
        szNWordMap.put(11, "宣处诱舔毅强犯禁稍豫跺聚");
        szNWordMap.put(12, "申介绍宗乙召孝");
        szNWordMap.put(13, "暴凑喵孵叽偶尔");
        szNWordMap.put(14, "萝卜愁沾晾");
        szNWordMap.put(15, "吗讨厌怒批访担压差忍模中弹疯汪搞");
        szNWordMap.put(16, "典基础阁佳盲唐");
        szNWordMap.put(17, "父啦鹦鹉悄");
        szNWordMap.put(18, "蒲英耍欠钓拢");
        szNWordMap.put(19, "亦抹宜庭未磨盘");
        szNWordMap.put(20, "饶优瑰岩参虾武粪辈设");
        szNWordMap.put(21, "滨鸥胳臂睬载凰亚榕凳逢除");
        szNWordMap.put(22, "兴融侧欣浸乳梢舍显材膝临库");
        szNWordMap.put(23, "蝌蚪蛾鲤鲫鲨");
        szNWordMap.put(24, "妙奏呢喃伟击汇喳");
        szNWordMap.put(25, "黎凝畅瞬猎");
        szNWordMap.put(26, "枣馋缓讶测监恍悟逐扎聪");
        szNWordMap.put(27, "司跌皆弃持");
        szNWordMap.put(28, "默落姿势投调况烈镇述普忧联");
        szNWordMap.put(29, "宁胸脯惹仰渣或者惜诚");
        szNWordMap.put(30, "斗棒恩大血撤险瓦帘迅速夺秒");
        szNWordMap.put(31, "眨瞪瞅眶睹");
        return szNWordMap;
    }

    @Override
    public int getHighLightWordNum() {
        return 4;
    }
}
