var zhengji = createObject(1, '整机');
var baohumo = createObject(2, '保护膜');
var shoujike = createObject(3, '手机壳');
var qitashouji = createObject(4, '其他');
var shouji = new Object();
shouji.name = '手机';
shouji.content = [zhengji, baohumo, shoujike, qitashouji];


var bijiben = createObject(5, '笔记本');
var taishiji = createObject(6, '台式机');
var pingban = createObject(7, '平板');
var shubiaojianpan = createObject(8, '鼠标键盘');
var xianshiqi = createObject(9, '显示器');
var zhuji = createObject(10, '主机');
var wangluochanpin = createObject(11, '网络产品');
var qitadiannao = createObject(12, '其他');
var content = [bijiben,taishiji,pingban,shubiaojianpan,xianshiqi,zhuji,wangluochanpin,qitadiannao];
var diannao = createType('电脑', content);


var xiangji = createObject(13, '相机');
var jingtou = createObject(14, '镜头');
var sheyingqicai = createObject(15, '摄影器材');
var qitaxiangji = createObject(16, '其他');
var content1 = [xiangji, jingtou, sheyingqicai, qitaxiangji];
var sheyingshexiang = createType('摄影摄像', content1);


var xiyiji = createObject(17, '洗衣机');
var yinshuiji = createObject(18, '饮水机');
var dianchuifeng = createObject(19, '电吹风');
var fengshan = createObject(20, '风扇');
var kongtiao = createObject(21, '空调');
var qitadianqi = createObject(22, '其他');
var content2 = [xiyiji,yinshuiji,dianchuifeng,fengshan,kongtiao,qitadianqi];
var dianqi = createType('电器', content2);


var erji = createObject(23, '耳机');
var shouyinji = createObject(24, '收音机');
var wurenji = createObject(25, '无人机');
var qitaqitashumadianqi = createObject(26, '其他');
var content3 = [erji,shouyinji,wurenji,qitaqitashumadianqi];
var qitashumadianqi = createType('其他', content3);


var bushui = createObject(27, '补水');
var baoshi = createObject(28, '保湿');
var qudouqujiaozhi = createObject(29, '祛痘去角质');
var qitahufupin = createObject(30, '其他');
var content4 = [bushui, baoshi, qudouqujiaozhi, qitahufupin];
var hufupin = createType('护肤品', content4);


var meirong = createObject(31, '美容');
var meifa = createObject(32, '美发');
var meijia = createObject(33, '美甲');
var qitacaizhuang = createObject(34, '其他');
var content5 = [meirong,meifa,meijia,qitacaizhuang];
var caizhuang = createType('彩妆', content5);


var xifahufa = createObject(35, '洗发护发');
var muyuyongpin = createObject(36, '沐浴用品');
var kouqiangqingjie = createObject(37, '口腔清洁');
var qitaqingjieweisheng = createObject(38, '其他');
var content6 = [xifahufa,muyuyongpin,kouqiangqingjie,qitaqingjieweisheng];
var qingjieweisheng = createType('清洁卫生', content6);


var qitaqitagerenhuli = createObject(39, '其他');
var content7 = [qitaqitagerenhuli];
var qitagerenhuli = createType('其他', content7);



var wenzhang = createObject(40, '蚊帐');
var yanzhaoersai = createObject(41, '眼罩耳塞');
var chuangtoudeng = createObject(42, '床头灯');
var qitachuangshangyongpin = createObject(43, '其他');
var content8 = [wenzhang, yanzhaoersai, chuangtoudeng, qitachuangshangyongpin];
var chuangshangyongpin = createType('床上用品', content8);


var penzaipenjing = createObject(44, '盆栽盆景');
var duorouzhiwu = createObject(45, '多肉植物');
var shuipeizhiwu = createObject(46, '水培植物');
var yuanyigongju = createObject(47, '园艺工具');
var qitahuahuilvzhi = createObject(48, '其他');
var content9 = [penzaipenjing, duorouzhiwu,shuipeizhiwu,yuanyigongju,qitahuahuilvzhi];
var huahuilvzhi = createType('花卉绿植', content9);


var chuanglian = createObject(49, '窗帘');
var kaodianzuodian = createObject(50, '靠垫坐垫');
var baozhen = createObject(51, '抱枕');
var qiangzhi = createObject(52, '墙纸');
var baijianzhuangshi = createObject(53, '摆件装饰');
var naozhong = createObject(54, '闹钟');
var qitabuyijiashi = createObject(55, '其他');
var content10 = [chuanglian,kaodianzuodian,baozhen,qiangzhi,baijianzhuangshi,naozhong,qitabuyijiashi];
var buyijiashi = createType('布艺家饰', content10);



var yijia = createObject(56, '衣架');
var shounahe_shounadai = createObject(57, '收纳盒&收纳袋');
var lajitong_lajidai = createObject(58, '垃圾桶&垃圾袋');
var fangchen = createObject(59, '防尘');
var saozhoutuoba = createObject(60, '扫帚拖把');
var chushifangchao = createObject(61, '除湿防潮');
var chujunfangchong = createObject(62, '除菌防虫');
var qitashounaqingjie = createObject(63, '其他');
var content11 = [yijia,shounahe_shounadai,lajitong_lajidai,fangchen,saozhoutuoba,chushifangchao,chujunfangchong,qitashounaqingjie];
var shounaqingjie = createType('收纳清洁', content11);



var qitaqitashenghuojiaju = createObject(64, '其他');
var content12 = [qitaqitashenghuojiaju];
var qitashenghuojiaju = createType('其他', content12);



var shangzhuangnan = createObject(65, '上装');
var xiazhuangnan = createObject(66, '下装');
var xiemaonan = createObject(67, '鞋帽');
var jiafanan = createObject(68, '假发');
var qitananzhuang = createObject(69, '其他');
var content13 = [shangzhuangnan,xiazhuangnan,xiemaonan,jiafanan,qitananzhuang];
var nanzhuang = createType('男装', content13);



var shangzhuangnv = createObject(70, '上装');
var xiazhuangnv = createObject(71, '下装');
var xiemaonv = createObject(72, '鞋帽');
var jiafanv = createObject(73, '假发');
var qitanvzhuang = createObject(74, '其他');
var content14 = [shangzhuangnv,xiazhuangnv,xiemaonv,jiafanv,qitanvzhuang];
var nvzhuang = createType('女装', content14);



var shoubiao = createObject(75, '手表');
var erhuan = createObject(76, '耳环');
var shoulianxianglain = createObject(77, '手链项链');
var qitapeishi = createObject(78, '其他');
var content15 = [shoubiao,erhuan,shoulianxianglain,qitapeishi];
var peishi = createType('配饰', content15);



var nanbao = createObject(79, '男包');
var nvbao = createObject(80, '女包');
var beibao = createObject(81, '背包');
var shouti = createObject(82, '手提');
var xinglixiang = createObject(83, '行李箱');
var qitaxiangbao = createObject(84, '其他');
var content16 = [nanbao,nvbao,beibao,shouti,xinglixiang,qitaxiangbao];
var xiangbao = createType('箱包', content16);



var qitaqitafushixiangbao = createObject(85, '其他');
var content17 = [qitaqitafushixiangbao];
var qitafushixiangbao = createType('其他', content17);



var jiaocaijiaofu = createObject(86, '教材教辅');
var kaoshiziliao = createObject(87, '考试资料');
var changxiaowenxue = createObject(88, '畅销文学');
var qikanzazhi = createObject(89, '期刊杂志');
var qitatushu = createObject(90, '其他');
var content18 = [jiaocaijiaofu,kaoshiziliao,changxiaowenxue,qikanzazhi,qitatushu];
var tushu = createType('图书', content18);



var bi = createObject(91, '笔');
var mo = createObject(92, '墨');
var zhi = createObject(93, '纸');
var qitawenju = createObject(94, '其他');
var content19 = [bi,mo,zhi,qitawenju];
var wenju = createType('文具', content19);


var yueqi = createObject(95, '乐器');
var huihuagongju = createObject(96, '绘画工具');
var qitayishu = createObject(97, '其他');
var content20 = [yueqi,huihuagongju,qitayishu];
var yishu = createType('艺术', content20);



var qitaqitajiaoyu = createObject(98, '其他');
var content21 = [qitaqitajiaoyu];
var qitajiaoyu = createType('其他', content21);




var lingshi = createObject(99, '零食');
var chajiu = createObject(100, '茶酒');
var rupinchongyin = createObject(101, '乳品冲饮');
var shuiguo = createObject(102, '水果');
var qitashipin = createObject(103, '其他');
var content22 = [lingshi,chajiu,rupinchongyin,shuiguo,qitashipin];
var shipin = createType('食品', content22);



var weishengsu = createObject(104, '维生素');
var anshenzhumian = createObject(105, '安神助眠');
var huanjiepilao = createObject(106, '缓解疲劳');
var tishenxingnao = createObject(107, '提神醒脑');
var qitabaojianpin = createObject(108, '其他');
var content23 = [weishengsu,anshenzhumian,huanjiepilao,tishenxingnao,qitabaojianpin];
var baojianpin = createType('保健品', content23);



var shangkoubaoza = createObject(109, '伤口包扎');
var changbeiyaowu = createObject(110, '常备药物');
var qitayiyaoyongpin = createObject(111, '其他');
var content24 = [shangkoubaoza,changbeiyaowu,qitayiyaoyongpin];
var yiyaoyongpin = createType('医药用品', content24);



var qitaqitashipinyiyao = createObject(112, '其他');
var content25 = [qitaqitashipinyiyao];
var qitashipinyiyao = createType('其他', content25);


var qitaqitaqita = createObject(113, '其他');
var content26 = [qitaqitaqita];
var qitaqita = createType('其他', content26);



var type_list = [
    [shouji,diannao,sheyingshexiang,dianqi,qitashumadianqi],
    [hufupin,caizhuang,qingjieweisheng,qitagerenhuli],
    [chuangshangyongpin,huahuilvzhi,buyijiashi,shounaqingjie,qitashenghuojiaju],
    [nanzhuang,nvzhuang,peishi,xiangbao,qitafushixiangbao],
    [tushu,wenju,yishu,qitajiaoyu],
    [shipin,baojianpin,yiyaoyongpin,qitashipinyiyao],
    [qitaqita]
];


function createObject(id, name) {
    var temp = new Object();
    temp.id = id;
    temp.name = name;
    return temp;
}
function createType(name, content) {
    var temp = new Object();
    temp.name = name;
    temp.content = content;
    return temp;
}
function getTypeList() {
    return type_list;
}
