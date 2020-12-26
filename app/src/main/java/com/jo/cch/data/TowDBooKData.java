package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TowDBooKData extends YuWenData {
    @Override
    public TreeMap<Integer, String> getCourses() {
        TreeMap<Integer, String> courseMap = new TreeMap<>();
        //初始化课程
        courseMap.put(1, "1、古诗二首 村居 咏柳");
        courseMap.put(2, "2、找春天");
        courseMap.put(3, "3、开满鲜花的小路");
        courseMap.put(4, "4、邓小平爷爷植树");
        courseMap.put(5, "语文园地一");
        courseMap.put(6, "5、雷锋叔叔，你在哪里");
        courseMap.put(7, "6、千人糕");
        courseMap.put(8, "7、一匹出色的马");
        courseMap.put(9, "语文园地二");
        courseMap.put(10, "1、神州谣");
        courseMap.put(11, "2、传统节日");
        courseMap.put(12, "3、“贝”的故事");
        courseMap.put(13, "4、中国美食");
        courseMap.put(14, "语文园地三");
        courseMap.put(15, "8、彩色的梦");
        courseMap.put(16, "9、枫树上的喜鹊");
        courseMap.put(17, "10、沙滩上的童话");
        courseMap.put(18, "11、我是一只小虫子");
        courseMap.put(19, "语文园地四");
        courseMap.put(20, "12、寓言二则 亡羊补牢 揠苗助长");
        courseMap.put(21, "13、画杨桃");
        courseMap.put(22, "14、小马过河");
        courseMap.put(23, "语文园地五");
        courseMap.put(24, "15、古诗二首 晓出净慈寺送林子方 绝句");
        courseMap.put(25, "16、雷雨");
        courseMap.put(26, "17、要是你在野外迷了路");
        courseMap.put(27, "18、太空生活趣事多");
        courseMap.put(28, "语文园地六");
        courseMap.put(29, "19、大象的耳朵");
        courseMap.put(30, "20、蜘蛛开店");
        courseMap.put(31, "21、青蛙卖泥塘");
        courseMap.put(32, "22、小毛虫");
        courseMap.put(33, "语文园地七");
        courseMap.put(34, "23、祖先的摇篮");
        courseMap.put(35, "24、当世界年纪还小的时候");
        courseMap.put(36, "25、羿射九日");
        courseMap.put(37, "语文园地八");
        return courseMap;
    }

    @Override
    public Map<Integer, String> getXzNWords() {
        //课程对应的初始化写字表
        Map<Integer, String> xzNWordMap = new HashMap<>();
        xzNWordMap.put(1, "诗村童碧妆绿丝剪");
        xzNWordMap.put(2, "冲寻姑娘吐柳荡桃杏");
        xzNWordMap.put(3, "鲜邮递员原叔局堆礼");
        xzNWordMap.put(4, "邓植格引注满休息");
        xzNWordMap.put(6, "锋昨冒弯留背洒暖温");
        xzNWordMap.put(7, "能桌味买具甘甜菜劳");
        xzNWordMap.put(8, "匹妹波纹像景恋舍求");
        xzNWordMap.put(10, "州湾岛峡民族谊齐奋");
        xzNWordMap.put(11, "贴街舟艾敬转团热闹");
        xzNWordMap.put(12, "贝壳甲骨钱币与财关");
        xzNWordMap.put(13, "烧茄烤鸭肉鸡蛋炒饭");
        xzNWordMap.put(15, "彩梦森拉结苹般精灵");
        xzNWordMap.put(16, "伞姨弟便教游戏母");
        xzNWordMap.put(17, "周围句补充药合死记");
        xzNWordMap.put(18, "屁股尿净屎幸使劲");
        xzNWordMap.put(20, "亡牢钻劝丢告筋疲");
        xzNWordMap.put(21, "图课摆座交哈页抢嘻");
        xzNWordMap.put(22, "愿意麦该伯刻突掉");
        xzNWordMap.put(24, "湖莲穷荷绝含岭吴");
        xzNWordMap.put(25, "雷乌黑压垂户迎扑");
        xzNWordMap.put(26, "指针帮助导永碰特积");
        xzNWordMap.put(27, "宇宙杯失板容易浴室桶");
        xzNWordMap.put(29, "扇慢遇兔安根痛最");
        xzNWordMap.put(30, "店决定商夫终完换期");
        xzNWordMap.put(31, "蛙卖搬倒籽泉破应");
        xzNWordMap.put(32, "整抽纺织编怎布消");
        xzNWordMap.put(34, "祖啊浓望蓝摘掏赛忆");
        xzNWordMap.put(35, "世界功反复式简弄由");
        xzNWordMap.put(36, "觉值类艰弓炎害此新");
        return xzNWordMap;
    }

    @Override
    public Map<Integer, String> getSzNWords() {
        //课程对应的初始化识字表
        Map<Integer, String> szNWordMap = new HashMap<>();
        szNWordMap.put(1, "莺拂堤柳醉咏妆丝绦裁剪");
        szNWordMap.put(2, "脱袄寻羞姑遮掩探嫩符解触杜鹃");
        szNWordMap.put(3, "邮递裹寄局堆破漏懊丧啊猬绚籽礼");
        szNWordMap.put(4, "邓坛龄格握致勃挖选茁移挥填扶");
        szNWordMap.put(5, "亭咨询剧管理宝塔餐厅");
        szNWordMap.put(6, "曾蒙泞顺迈踏荆棘瓣莹觅需献");
        szNWordMap.put(7, "糕特嘛买粉糖蔗汁菜熬算销劳的确应");
        szNWordMap.put(8, "郊泛波纹葱软毯异恋舍求株拾骑跨");
        szNWordMap.put(9, "程魔术建筑演营务判饲养");
        szNWordMap.put(10, "州华涌峰耸隔峡与陆谊浓齐奋繁荣");
        szNWordMap.put(11, "传统贴宵巷祭舟艾堂乞巧郎饼赏菊");
        szNWordMap.put(12, "甲骨类漂珍饰品随易损币财赚赔购贫");
        szNWordMap.put(13, "菠煎腐茄烤煮爆炖蘑菇蒸饺炸酱粥蛋");
        szNWordMap.put(14, "津溜辣乎喷腻绵脆邦");
        szNWordMap.put(15, "盒聊坪郁囱般精叮咛");
        szNWordMap.put(16, "渡荫蔽撑拼母冈懂案");
        szNWordMap.put(17, "堡插凶狠补充攻商量驾轰驳药赞合记");
        szNWordMap.put(18, "屁股昏泡尿茸醒晃免费列屎撞贪脾婶");
        szNWordMap.put(19, "陀螺毽倒翁枪橡板控坦克");
        szNWordMap.put(20, "寓则亡牢圈钻叼坊悔此焦筋疲喘截");
        szNWordMap.put(21, "室靠而班哈倒审视页肃晌抢嘻悦诲");
        szNWordMap.put(22, "棚驮磨坊挡伯浅刻突叹唉试蹄既");
        szNWordMap.put(23, "厨厕厢厦穴窟窿窑窄");
        szNWordMap.put(24, "晓慈毕竟映绝鹂鸣行含岭泊");
        szNWordMap.put(25, "压蝉垂户扑");
        szNWordMap.put(26, "慌辨忠实导盏永闯碰稠稀渠积");
        szNWordMap.put(27, "航宇宙稳固舱杯饮件题密浴桶");
        szNWordMap.put(28, "博馆览技育研究哨诊");
        szNWordMap.put(29, "似耷咦竖竿舞痛烦扇");
        szNWordMap.put(30, "店蹲寂寞罩编顾付夫换颈袜匆蜈蚣");
        szNWordMap.put(31, "卖烂牌喝坑挺舒集播撒茵灌缺泳愣");
        szNWordMap.put(32, "昆怜挪仿佛尽力任何纺纱竭力规律待挣愉绒");
        szNWordMap.put(33, "扫帚抹拖簸箕玻璃垃圾");
        szNWordMap.put(34, "祖掏逗蔷薇逮忆");
        szNWordMap.put(35, "纪必须功譬糙敏式简由睁秩序哦");
        szNWordMap.put(36, "射值熔艰箭裂窜炎庄稼滋腾");
        szNWordMap.put(37, "钩铲梅柿源涨炬灿垮坟");
        return szNWordMap;
    }

    @Override
    public int getHighLightWordNum() {
        return 14;
    }
}
