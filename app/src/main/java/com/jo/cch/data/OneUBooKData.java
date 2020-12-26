package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OneUBooKData extends YuWenData {

    @Override
    public TreeMap<Integer, String> getCourses() {
        TreeMap<Integer, String> courseMap = new TreeMap<>();
        //初始化课程
        courseMap.put(1, "1、天地人");
        courseMap.put(2, "2、金木水火土");
        courseMap.put(3, "3、口耳目");
        courseMap.put(4, "4、日月水火");
        courseMap.put(5, "5、对韵歌");
        courseMap.put(6, "语文园地一");
        courseMap.put(7, "汉语拼音1");
        courseMap.put(8, "语文园地二");
        courseMap.put(9, "汉语拼音2");
        //courseMap.put(10, "语文园地三");
        courseMap.put(11, "1、秋天");
        courseMap.put(12, "2、小小的船");
        courseMap.put(13, "3、江南");
        courseMap.put(14, "4、四季");
        courseMap.put(15, "语文园地四");
        courseMap.put(16, "6、画");
        courseMap.put(17, "7、大小多少");
        courseMap.put(18, "8、小书包");
        courseMap.put(19, "9、日月明");
        courseMap.put(20, "10、升国旗");
        courseMap.put(21, "语文园地五");
        courseMap.put(22, "5、影子");
        courseMap.put(23, "6、比尾巴");
        courseMap.put(24, "7、青蛙写诗");
        courseMap.put(25, "8、雨点儿");
        //courseMap.put(26, "语文园地六");
        courseMap.put(27, "9、明天要远足");
        courseMap.put(28, "10、大还是小");
        courseMap.put(29, "11、项链");
        courseMap.put(30, "语文园地七");
        courseMap.put(31, "12、雪地里的小画家");
        courseMap.put(32, "13、乌鸦喝水");
        courseMap.put(33, "14、小蜗牛");
        courseMap.put(34, "语文园地八");
        return courseMap;
    }

    @Override
    public Map<Integer, String> getXzNWords() {
        //课程对应的初始化写字表
        Map<Integer, String> xzNWordMap = new HashMap<>();
        xzNWordMap.put(2, "一二三上");
        xzNWordMap.put(3, "口目耳手");
        xzNWordMap.put(4, "日田禾火");
        xzNWordMap.put(5, "虫云山");
        xzNWordMap.put(6, "八十");
        xzNWordMap.put(11, "了子人大");
        xzNWordMap.put(12, "月儿头里");
        xzNWordMap.put(13, "可东西");
        xzNWordMap.put(14, "天四是");
        xzNWordMap.put(15, "女开");
        xzNWordMap.put(16, "水去来不");
        xzNWordMap.put(17, "小少牛果鸟");
        xzNWordMap.put(18, "早书刀尺本");
        xzNWordMap.put(19, "木林土力心");
        xzNWordMap.put(20, "中五立正");
        xzNWordMap.put(22, "在后我好");
        xzNWordMap.put(23, "长比巴把");
        xzNWordMap.put(24, "下个雨们");
        xzNWordMap.put(25, "问有半从你");
        xzNWordMap.put(27, "才明同学");
        xzNWordMap.put(28, "自己衣");
        xzNWordMap.put(29, "白的又和");
        xzNWordMap.put(31, "竹牙马用几");
        xzNWordMap.put(32, "只石多出见");
        xzNWordMap.put(33, "对妈全回");
        xzNWordMap.put(34, "工厂");
        return xzNWordMap;
    }

    @Override
    public Map<Integer, String> getSzNWords() {
        //课程对应的初始化识字表
        Map<Integer, String> szNWordMap = new HashMap<>();
        szNWordMap.put(1, "天地人你我他");
        szNWordMap.put(2, "一二三四五上下");
        szNWordMap.put(3, "口耳目手足站坐");
        szNWordMap.put(4, "日月水火山石田禾");
        szNWordMap.put(5, "对云雨风花鸟虫");
        szNWordMap.put(6, "六七八九十");
        szNWordMap.put(7, "爸妈马土不画打棋鸡字词语句字桌纸");
        szNWordMap.put(8, "文数学音乐");
        szNWordMap.put(9, "妹奶白皮小桥台雪儿草家是车羊走也");
        szNWordMap.put(11, "秋气了树叶片大飞会个");
        szNWordMap.put(12, "的船两头在里看见闪星");
        szNWordMap.put(13, "江南可采莲鱼东西北");
        szNWordMap.put(14, "尖说春青蛙夏弯地就冬");
        szNWordMap.put(15, "男女开关正反");
        szNWordMap.put(16, "远有色近听无声去还来");
        szNWordMap.put(17, "多少黄牛只猫边鸭苹果杏桃");
        szNWordMap.put(18, "书包尺作业本笔刀课早校");
        szNWordMap.put(19, "明力尘从众双木林森条心");
        szNWordMap.put(20, "升国旗中红歌起么美丽立");
        szNWordMap.put(21, "午夜昨今年");
        szNWordMap.put(22, "影前后黑狗左右它好朋友");
        szNWordMap.put(23, "比尾巴谁长短把伞兔最公");
        szNWordMap.put(24, "写诗点要过给当串们以成");
        szNWordMap.put(25, "数彩半空问到方没更绿出长");
        szNWordMap.put(27, "睡那海真老师吗同什才亮");
        szNWordMap.put(28, "时候觉得自己很穿衣服快");
        szNWordMap.put(29, "蓝又笑着向和贝娃挂活金");
        szNWordMap.put(30, "哥姐弟叔爷");
        szNWordMap.put(31, "群竹牙用几步为参加洞着");
        szNWordMap.put(32, "乌鸦处找办旁许法放进高");
        szNWordMap.put(33, "住孩玩吧发芽爬呀久回全变");
        szNWordMap.put(34, "工厂医院生");
        return szNWordMap;
    }

    @Override
    public int getHighLightWordNum() {
        return 4;
    }
}
