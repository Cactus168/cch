package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TowUBooKData extends YuWenData {
    @Override
    public TreeMap<Integer, String> getCourses() {
        //初始化课程
        TreeMap<Integer, String> courseMap = new TreeMap<>();
        courseMap.put(1, "1、小蝌蚪找妈妈");
        courseMap.put(2, "2、我是什么");
        courseMap.put(3, "3、植物妈妈有办法");
        courseMap.put(4, "语文园地一");
        courseMap.put(5, "1、场景歌");
        courseMap.put(6, "2、树之歌");
        courseMap.put(7, "3、拍手歌");
        courseMap.put(8, "4、田家四季歌");
        courseMap.put(9, "语文园地二");
        courseMap.put(10, "4、曹冲称象");
        courseMap.put(11, "5、红马的故事");
        courseMap.put(12, "6、一封信");
        courseMap.put(13, "7、妈妈睡了");
        courseMap.put(14, "语文园地三");
        courseMap.put(15, "8、古诗二首 登鹳雀楼 望庐山瀑布");
        courseMap.put(16, "9、黄山奇石");
        courseMap.put(17, "10、日月潭");
        courseMap.put(18, "11、葡萄沟");
        courseMap.put(19, "语文园地四");
        courseMap.put(20, "12、坐井观天");
        courseMap.put(21, "13、寒号鸟");
        courseMap.put(22, "14、我要的是葫芦");
        courseMap.put(23, "语文园地五");
        courseMap.put(24, "15、大禹治水");
        courseMap.put(25, "16、朱德的扁担");
        courseMap.put(26, "17、难忘的泼水节");
        courseMap.put(27, "语文园地六");
        courseMap.put(28, "18、古诗二首 夜宿山寺 敕勒歌");
        courseMap.put(29, "19、雾在哪里");
        courseMap.put(30, " 20、雪孩子");
        courseMap.put(31, "21、雪孩子狐假虎威");
        courseMap.put(32, "语文园地七");
        courseMap.put(33, "22、狐狸分奶酪");
        courseMap.put(34, "23、纸船和风筝");
        courseMap.put(35, "24、风娃娃");
        courseMap.put(36, "语文园地八");
        return courseMap;
    }

    @Override
    public Map<Integer, String> getXzNWords() {
        //课程对应的初始化写字表
        Map<Integer, String> xzNWordMap = new HashMap<>();
        xzNWordMap.put(1, "两哪宽顶眼睛肚皮孩跳");
        xzNWordMap.put(2, "变极片傍海洋作坏给带");
        xzNWordMap.put(3, "法如脚它娃她毛更知识");
        xzNWordMap.put(5, "处园桥群队旗铜号领巾");
        xzNWordMap.put(6, "杨壮桐枫松柏棉杉化桂");
        xzNWordMap.put(7, "歌写丛深六熊猫九朋友");
        xzNWordMap.put(8, "季吹肥农事忙归戴辛苦");
        xzNWordMap.put(10, "称柱底杆秤做岁站船然");
        xzNWordMap.put(11, "画幅评奖纸报另及拿并");
        xzNWordMap.put(12, "封信今支圆珠笔灯电影");
        xzNWordMap.put(13, "哄先闭脸沉发窗");
        xzNWordMap.put(15, "楼依尽黄层照炉烟挂川");
        xzNWordMap.put(16, "南部些巨位每升闪狗");
        xzNWordMap.put(17, "湾名胜迹央丽展现披");
        xzNWordMap.put(18, "份坡枝起蓉老收城市利");
        xzNWordMap.put(20, "井观沿答渴喝话际");
        xzNWordMap.put(21, "面阵朗枯却将纷夜");
        xzNWordMap.put(22, "棵谢想盯言邻治怪");
        xzNWordMap.put(24, "洪灾难道年认被业产");
        xzNWordMap.put(25, "扁担志伍师军战士");
        xzNWordMap.put(26, "忘泼度龙炮穿向令");
        xzNWordMap.put(28, "危敢惊阴似野苍茫");
        xzNWordMap.put(29, "于论岸屋切久散步");
        xzNWordMap.put(30, "唱赶旺旁浑候谁轻汽");
        xzNWordMap.put(31, "食爷物就爪神活猪");
        xzNWordMap.put(33, "奶始吵仔急咬第公");
        xzNWordMap.put(34, "折张祝扎抓但哭");
        xzNWordMap.put(35, "车得秧苗汗场伤路");
        return xzNWordMap;
    }

    @Override
    public Map<Integer, String> getSzNWords() {
        //课程对应的初始化识字表
        Map<Integer, String> szNWordMap = new HashMap<>();
        szNWordMap.put(1, "塘脑袋灰哇教捕迎阿姨宽龟顶披鼓");
        szNWordMap.put(2, "晒极傍越滴溪奔洋坏淹没冲毁屋猜");
        szNWordMap.put(3, "植如为旅备纷刺底炸离察识粗得");
        szNWordMap.put(4, "套帽登鞋裤图壶帐篷指针");
        szNWordMap.put(5, "帆艘军舰稻园翠队铜号");
        szNWordMap.put(6, "梧桐掌枫松柏装桦耐守疆银杉化桂");
        szNWordMap.put(7, "世界孔雀锦雄鹰翔雁丛深猛灵休");
        szNWordMap.put(8, "季蝴蝶麦苗桑肥农归戴场谷粒虽辛苦了");
        szNWordMap.put(9, "葡萄紫狐狸笨酸");
        szNWordMap.put(10, "曹称员根柱议论重杆秤砍线止量");
        szNWordMap.put(11, "玲详幅评奖催啪脏报另及懒并糟肯");
        szNWordMap.put(12, "封削锅朝刮胡修冷肩团重完期结束鲜");
        szNWordMap.put(13, "哄先梦闭紧润等吸发粘汗额沙乏");
        szNWordMap.put(14, "弹钢琴捏泥滚铁环荡滑梯楼");
        szNWordMap.put(15, "依尽欲穷层瀑布炉烟遥川");
        szNWordMap.put(16, "闻名景区省部秀尤其仙巨位都著形状");
        szNWordMap.put(17, "潭湾湖绕茂盛围胜央岛纱童境引客");
        szNWordMap.put(18, "沟产梨份枝搭淡好够收城市干留钉利分味");
        szNWordMap.put(19, "昌铺调硬卧限乘售");
        szNWordMap.put(20, "沿答渴喝话弄错际哪抬");
        szNWordMap.put(21, "号堵缝当鹊朗衔枯劝趁将且腊狂吼复哀");
        szNWordMap.put(22, "葫芦藤谢蚜盯啊赛感怪慢");
        szNWordMap.put(23, "锋蜜蜂幕扫墓慕抄炒");
        szNWordMap.put(24, "洪毒蛇兽伤灾难仍退继续认训被恢");
        szNWordMap.put(25, "朱德扁担志伍泽敌陡难争仗疼料敬");
        szNWordMap.put(26, "泼族民度敲龙驶容踩铺盛碗祝福健康寿");
        szNWordMap.put(27, "轿救摩托防渔货油轮科考");
        szNWordMap.put(28, "宿寺危辰恐惊似庐笼盖苍茫");
        szNWordMap.put(29, "雾淘于暗岸街梁甚至切躲失");
        szNWordMap.put(30, "累添柴烧旺渐哎呀冒烫终浑淋灭激");
        szNWordMap.put(31, "假威转扯嗓派违抗爪趟神猪纳闷受骗借");
        szNWordMap.put(32, "滩椰壳漠骆驼骏悬崖");
        szNWordMap.put(33, "酪捡俩始拌帮匀嚷瞧便剩整");
        szNWordMap.put(34, "筝鼠折漂扎抓幸但愿哭取");
        szNWordMap.put(35, "助抽使劲哗秧拉表示摆翻栽责");
        szNWordMap.put(36, "狼猩鹤鸽羚蚯蚓螃蟹蚕");
        return szNWordMap;
    }

    @Override
    public int getHighLightWordNum() {
        return 22;
    }
}
