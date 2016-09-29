package com.yisheng.http;

public class ConnectionConstant {

	/**
	 * 测试地址
	 */
//	 public static final String DEV_DOMAIN = "http://192.168.10.63:8080/tdcctp/";//刘本地
	 public static final String DEV_DOMAIN = "http://wx.mposbank.com/tdcctp/";//线上
//	 public static final String DEV_DOMAIN = "http://192.168.10.137:8088/tdcctp/";

//	/**
//	 * 正式地址
//	 */
//	 public static final String IP="101.231.74.100";
//	 public static final int PORT=26013;
//	 public static final String DEV_DOMAIN = "http://101.231.74.102:16666/";
//	 public static final String brhId=null;

	/**
	 *  汇支付 1116
	 */
	 public static boolean DEBUG=true;

	
	/**
	 * 新订单
	 */
	public static final String QUERYNEWSURL = DEV_DOMAIN+"appBiz/load_new_order.tran";
	/**
	 * 商户交易查询
	 */
	public static final String QUERYALLURL = DEV_DOMAIN+"appBiz/load_orders.tran";
	/**
	 * 商户近期交易查询
	 */
	public static final String QUERYRECENTLYURL = DEV_DOMAIN+"appBiz/load_dates_order.tran";
	/**
	 * 商户某天交易量查询
	 */
	public static final String QUERYSOMEDAYURL = DEV_DOMAIN+"appBiz/load_date_order.tran";
	/**
	 * 交易订单详情
	 */
	public static final String QUERYDETAILURL = DEV_DOMAIN+"appBiz/load_order.tran";
	/**
	 * 登录
	 */
	public static final String LOGINURL = DEV_DOMAIN+"SY0003.tran";
	/**
	 * 商户短信发送
	 */
	public static final String CUSTSMSURL = DEV_DOMAIN+"sms/send_captcha.tran";
	/**
	 * 验证码验证
	 */
	public static final String SMSCHECKURL = DEV_DOMAIN+"sms/check_captcha.tran";
	/**
	 * 密码修改
	 */
	public static final String PSWCHANGEURL = DEV_DOMAIN+"app/update_psw.tran";
	/**
	 * 版本更新
	 */
	public static final String CHECKUPDATEURL = DEV_DOMAIN+"app/checkUpdate.tran";



	/**
	 * 注册
	 */
	public static final String RGISTURL = "user/doReg.html";



	/**
	 * 注册发送验证码 注册 修改手机号 新手机号
	 */
	public static final String SMSURL = "user/doMobile.html";

	/**
	 * 发送验证码
	 */
	public static final String SMSSENDURL = "dynKey/doAppSend.html";

	/**
	 * 验证码验证
	 */
	public static final String DOCHECKURL = "dynKey/doCheck.html";

	/**
	 * 上传认证
	 */
	public static final String DOUPLOADFILEURL = "mchtIn/doUploadFile.html";

	/**
	 * 开户行列表查询
	 */
	public static final String BANKURL = "bank/doSelectBank.html";

	/**
	 * 1.10 交易流水查询
	 */
	public static final String LOADTXNURL = "txn/loadTxn.html";

	/**
	 * 1.13 电子签名上传
	 */
	public static final String UPLOADSIGNURL = "txn/upLoadSign.html";

	/**
	 * 获取单笔消费信息
	 */
	public static final String LOADTXNDETURL = "txn/loadTxnDet.html";

	/**
	 * 终端绑定
	 */
	public static final String DOBOUNDLEURL = "termInf/doBoundle.html";
	/**
	 * 查询终端状态
	 */
	public static final String DOSELECTSTUURL = "termInf/doSelectStu.html";

	/**
	 * 终端扩展信息
	 */
	public static final String DOUPDATETERMKEYURL = "termInf/doUpdateTermKey.html";

	/**
	 * 1.4 修改密码
	 */
	public static final String DOUPTPWDURL = "user/doUptPwd.html";

	/**
	 * 1.11 当日收款汇总查询
	 */
	public static final String LOADDAYTXNAMTURL = "txn/loadDayTxnAmt.html";

	/**
	 * 收款账户变更
	 */
	public static final String DOUPDATEMCHTURL = "mchtIn/doUpdateMcht.html";

	/**
	 * 修改绑定手机号
	 */
	public static final String DOBINDPHONEURL = "user/doBindPhone.html";

	/**
	 * 终端解绑
	 */
	public static final String DOUNDUNDINGURL = "termInf/doUnbundling.html";

	/**
	 * 重置随机数
	 */
	public static final String DOSELECTTERMINFURL = "termInf/doSelectTermInf.html";

	/**
	 * 忘记密码
	 */
	public static final String DOFORGETURL = "user/doForget.html";

	/**
	 * 交易限额
	 */
	public static final String DOAMTLIMITURL = "mchtIn/doAmtLimit.html";
	
	/**
	 * T+0申请
	 */
	public static final String DOADDT0APPLAYURL = "T0Applay/doAddT0Applay.html";

	/**
	 * 商户余额查询
	 */
	public static final String DOSELECTAMTURL = "distillApplay/doSelectAmt.html";
	
	/**
	 * T0提现申请
	 */
	public static final String DOADDDISTILLAPPLAYURL = "distillApplay/doAddDistillApplay.html";
	
	/**
	 * 提现记录查询
	 */
	public static final String DOSELECTLISTURL = "distillApplay/doSelectList.html";
	
	/**
	 * T0白名单申请
	 */
	public static final String DOUPLOADCARDURL = "card/doUploadCard.html";
	
	/**
	 * T0白名单查询
	 */
	public static final String DOSELECTCARDURL = "card/doSelectCard.html";
	
	/**
	 * 卡号搜索
	 */
	public static final String DOSELECTEXTCARDURL = "card/doSelectExtCard.html";
	
	/**
	 * 结算状态查询
	 */
	public static final String DOADDT0APPLAYSTURL = "T0Applay/doAddT0ApplayStu.html";
	
	/**
	 * 上传头像
	 */
	public static final String DOUSERPHOTOURL = "user/doUserPhoto.html";
	
	/**
	 * 卡名单认证
	 */
	public static final String DOCARDVALIDURL = "cardvalid/doCardValid.html";
	
	/**
	 * 卡名单认证记录查询
	 */
	public static final String CARDVALIDDOSELECTLISTURL = "cardvalid/doSelectList.html";
	
	/**
	 * 卡名单查询状态
	 */
	public static final String DOSELECTBYKEYURL = "cardvalid/doselectBykey.html";

	/**
	 * 1.31	限额申请变更接口
	 */
	public static final String DOINSERTQUOTACHANGEURL = "QuotaChangeInf/doInsertQuotaChange.html";

	/**
	 * 限额变更记录查询
	 */
	public static final String QUOTACHANGEINFDOSELECTLISTURL = "QuotaChangeInf/doSelectList.html";

	/**
	 * 风险认证是否获取验证码
	 */
	public static final String DOVAILDSENDCARDURL = "card/doVaildSendCard.html";

	/**
	 * 验证验证码，加入白名单
	 */
	public static final String DOCHKVAILDURL = "card/doChkVaild.html";

	/**
	 * 登陆请求
	 */
	public static final int LOGINREQUEST = 1001;

	/**
	 * 注册
	 */
	public static final int REGISTREQUEST = 1002;

	/**
	 * 发送注册验证码请求
	 */
	public static final int SMSREQUEST = 1003;

	/**
	 * 发送验证码请求
	 */
	public static final int SMSSENDREQUEST = 1004;

	/**
	 * 验证码验证
	 */
	public static final int DOCHECKREQUEST = 1005;

	/**
	 * 上传认证
	 */
	public static final int DOUPLOADFILEREQUEST = 1006;

	/**
	 * 银行选择
	 */
	public static final int BANKREQUEST = 1007;

	/**
	 * 1.10 交易流水查询
	 */
	public static final int LOADTXNREQUEST = 1008;

	/**
	 * 获取单笔消费信息
	 */
	public static final int LOADTXNDETREQUEST = 1009;

	/**
	 * 终端绑定
	 */
	public static final int DOBOUNDLEREQUEST = 1010;

	/**
	 * 终端扩展信息
	 */
	public static final int DOUPDATETERMKEYREQUEST = 1011;

	/**
	 * 1.4 修改密码
	 */
	public static final int DOUPTPWDREQUEST = 1012;

	/**
	 * 1.11 当日收款汇总查询
	 */
	public static final int LOADDAYTXNAMTREQUEST = 1013;

	/**
	 * 收款账户变更
	 */
	public static final int DOUPDATEMCHTREQUEST = 1014;

	/**
	 * 修改绑定手机号
	 */
	public static final int DOBINDPHONEREQUEST = 1015;

	/**
	 * 终端解绑
	 */
	public static final int DOUNDUNDINGREQUEST = 1016;

	/**
	 * 重置随机数
	 */
	public static final int DOSELECTTERMINFREQUEST = 1017;

	/**
	 * 忘记密码
	 */
	public static final int DOFORGETREQUEST = 1018;

	/**
	 * 交易限额
	 */
	public static final int DOAMTLIMITREQUEST = 1019;
	
	/**
	 * T+0申请
	 */
	public static final int DOADDT0APPLAYREQUEST = 1020;
	
	/**
	 * 商户余额查询
	 */
	public static final int DOSELECTAMTREQUEST = 1021;
	
	/**
	 * T0提现申请
	 */
	public static final int DOADDDISTILLAPPLAYREQUEST = 1022;
	
	/**
	 * 提现记录查询
	 */
	public static final int DOSELECTLISTREQUEST = 1023;
	
	/**
	 * T0白名单申请
	 */
	public static final int DOUPLOADCARDREQUEST = 1024;
	
	/**
	 * T0白名单查询
	 */
	public static final int DOSELECTCARDREQUEST = 1025;

	/**
	 * 卡号搜索
	 */
	public static final int DOSELECTEXTCARDREQUEST = 1026;
	
	/**
	 * 结算状态查询
	 */
	public static final int DOADDT0APPLAYSTREQUEST = 1027;
	
	/**
	 * 上传头像
	 */
	public static final int DOUSERPHOTOREQUEST = 1028;
	
	/**
	 * 卡名单认证
	 */
	public static final int DOCARDVALIDREQUEST = 1029;
	
	/**
	 * 卡名单认证记录查询
	 */
	public static final int CARDVALIDDOSELECTLISTREQUEST = 1030;
	
	/**
	 * 卡名单查询状态
	 */
	public static final int DOSELECTBYKEYREQUEST = 1031;

	/**
	 * 1.31	限额申请变更接口
	 */
	public static final int DOINSERTQUOTACHANGEREQUEST = 1032;

	/**
	 * 限额变更记录查询
	 */
	public static final int QUOTACHANGEINFDOSELECTLISTREQUEST = 1033;

	/**
	 * 风险认证是否获取验证码
	 */
	public static final int DOVAILDSENDCARDREQUEST = 1034;

	/**
	 * 验证验证码，加入白名单
	 */
	public static final int DOCHKVAILDREQUEST = 1035;
	/**
	 * 查询终端状态
	 */
	public static final int DOSELECTSTUREQUEST = 1036;

	/**
	 * 
	 * token失效验证代码
	 */
	public static final String TOKEN_DISABLE = "100102";

	/**
	 * 没有用户信息
	 */
	public static final String NOUSERMESSAGE = "100113";

	/**
	 * 登陆是否成功识别码
	 */
	public static final String RESULT_SUCCESS = "1";

	/**
	 * 手机号码已经使用的code
	 */
	public final static String PHONE_USED = "300000";

	/**
	 * 手机号码已经使用未注册
	 */
	public final static String PHONE_UNUSED = "100111";

	/**
	 * 需要显示提示
	 */
	public final static String NEEDNOTI = "200";

	/**
	 * 新浪微博token失效
	 */
	public final static String WEIBO_DISABLE = "999403";

	/**
	 * 消息请求
	 */
	public static final int PAGESIZE = 5;

	/**
	 * 文件路径识别
	 */
	public static final int COUPONFILE = 9997;

	/**
	 * 文件路径识别
	 */
	public static final int BRANDFILE = 9998;

	/**
	 * 文件路径识别
	 */
	public static final int RESERVEFILE = 9999;

}
