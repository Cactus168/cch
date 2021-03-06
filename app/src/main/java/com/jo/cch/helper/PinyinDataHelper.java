package com.jo.cch.helper;

import java.util.HashMap;
import java.util.Map;

public class PinyinDataHelper {

    private static final Map<String, String> datas = new HashMap<String, String>();
    private static final Map<String, String> specialDatas = new HashMap<String, String>();
    static {
        datas.put("a","a,ai,an,ang,ao");
        datas.put("b","ba,bai,ban,bang,bao,bei,ben,beng,bi,bian,biao,bie,bin,bing,bo,bu");
        datas.put("c","ca,cai,can,cang,cao,ce,cen,ceng,cha,chai,chan,chang,chao,che,chen,cheng,chi,chong,chou,chu,chua,chuai,chuan,chuang,chui,chun,chuo,ci,cong,cou,cu,cuan,cui,cun,cuo");
        datas.put("d","da,dai,dan,dang,dao,de,den,dei,deng,di,dia,dian,diao,die,ding,diu,dong,dou,du,duan,dui,dun,duo");
        datas.put("e","e,ei,en,eng,er");
        datas.put("f","fa,fan,fang,fei,fen,feng,fo,fou,fu");
        datas.put("g","ga,gai,gan,gang,gao,ge,gei,gen,geng,gong,gou,gu,gua,guai,guan,guang,gui,gun,guo");
        datas.put("h","ha,hai,han,hang,hao,he,hei,hen,heng,hong,hou,hu,hua,huai,huan,huang,hui,hun,huo");
        datas.put("j","ji,jia,jian,jiang,jiao,jie,jin,jing,jiong,jiu,ju,juan,jue,jun");
        datas.put("k","ka,kai,kan,kang,kao,ke,ken,keng,kong,kou,ku,kua,kuai,kuan,kuang,kui,kun,kuo");
        datas.put("l","la,lai,lan,lang,lao,le,lei,leng,li,lia,lian,liang,liao,lie,lin,ling,liu,long,lou,lu,lü,luan,lue,lüe,lun,luo");
        datas.put("m","m,ma,mai,man,mang,mao,me,mei,men,meng,mi,mian,miao,mie,min,ming,miu,mo,mou,mu");
        datas.put("n","na,nai,nan,nang,nao,ne,nei,nen,neng,ng,ni,nian,niang,niao,nie,nin,ning,niu,nong,nou,nu,nü,nuan,nüe,nuo,nun");
        datas.put("o","o,ou,uo");
        datas.put("p","pa,pai,pan,pang,pao,pei,pen,peng,pi,pian,piao,pie,pin,ping,po,pou,pu");
        datas.put("q","qi,qia,qian,qiang,qiao,qie,qin,qing,qiong,qiu,qu,quan,que,qun");
        datas.put("r","ran,rang,rao,re,ren,reng,ri,rong,rou,ru,ruan,rui,run,ruo");
        datas.put("s","sa,sai,san,sang,sao,se,sen,seng,sha,shai,shan,shang,shao,she,shei,shen,sheng,shi,shou,shu,shua,shuai,shuan,shuang,shui,shun,shuo,si,song,sou,su,suan,sui,sun,suo");
        datas.put("t","ta,tai,tan,tang,tao,te,teng,ti,tian,tiao,tie,ting,tong,tou,tu,tuan,tui,tun,tuo");
        datas.put("w","wa,wai,wan,wang,wei,wen,weng,wo,wu");
        datas.put("x","xi,xia,xian,xiang,xiao,xie,xin,xing,xiong,xiu,xu,xuan,xue,xun");
        datas.put("y","ya,yan,yang,yao,ye,yi,yin,ying,yo,yong,you,yu,yuan,yue,yun");
        datas.put("z","za,zai,zan,zang,zao,ze,zei,zen,zeng,zha,zhai,zhan,zhang,zhao,zhe,zhei,zhen,zheng,zhi,zhong,zhou,zhu,zhua,zhuai,zhuan,zhuang,zhui,zhun,zhuo,zi,zong,zou,zu,zuan,zui,zun,zuo");

        specialDatas.put("lv","lü");
        specialDatas.put("lve","lüe");
        specialDatas.put("nv","nü");
        specialDatas.put("nve","nüe");
    }

    public static String getSpecialDatasByKey(String key){
        return specialDatas.get(key) == null ? key : specialDatas.get(key);
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public static String[] getDataByKey(String key){
        return datas.get(key).split(",");
    }
}
