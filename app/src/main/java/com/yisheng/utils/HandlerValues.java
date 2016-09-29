package com.yisheng.utils;


public class HandlerValues {

	/**
	 * 请求超时
	 */
	public static final int  NETWORK_TIMEOUT_ERROR = 0;

	/**\
	 * 网络连接不正常
	 */
	public static final int  NETWORK_ERROR = 1;
	
	/**
	 * 网络连接不正常
	 */
	public static final int  UNKNOWNERROR = 2;
	
	/**
	 * http请求错误
	 */
	public static final int  HTTPERROR = 3;
	
	/**
	 * 数据格式错误
	 */
	public static final int  DATAFORMATEERROR = 4;
	
	/**
	 * 登陆成功
	 */
	public static final int  LOGINSUCCESS = 5;
	
	/**
	 * 登陆失败
	 */
	public static final int  LOGINFAIL = 6;
	
	/**
	 * 验证码计时器到0
	 */
	public static final int  TIMEOK = 7;
	
	/**
	 * 验证码更新计时
	 */
	public static final int REFLASHTIME=8;
	
	/**
	 * 返回失败原因
	 */
	public static final int FAIL=9;
	
	/**
	 * 获取验证码成功
	 */
	public static final int SMSSUCCESS=10;
	
	/**
	 * 注册成功/修改密码成功/风险认证成功
	 */
	public static final int REGISTSUCCESS=11;
	
	/**
	 * 修改手机号验证码验证成功
	 */
	public static final int DOCHECKSUCCESS=12;
	
	/**
	 * 开户行列表查询成功
	 */
	public static final int BANKSUCCESS=13;
	
	/**
	 * 1.10	交易流水查询
	 */
	public static final int  CASHFLOWSUCCESS = 14;
	
	/**
	 * 终端绑定
	 */
	public static final int  DOBOUNDLESUCCESS = 15;
	
	/**
	 * 终端扩展信息/重置随机数
	 */
	public static final int  DOUPDATETERMKEYSUCCESS = 16;
	
	/**
	 * 收款账户变更/ 修改绑定手机号/终端解绑 /终端绑定
	 */
	public static final int  DOUPDATEMCHTSUCCESS = 17;

	/**
	 * 重置随机数
	 */
	public static final int  DOSELECTTERMINFSUCCESS = 18;
	
	/**
	 * 交易限额
	 */
	public static final int  DOAMTLIMITSUCCESS = 19;
	
	/**
	 *	交易记录查询
	 */
	public static final int  CASHRECORDSUCCESS = 20;
	
	/**
	 *	提现成功
	 */
	public static final int  APPLYCASHSUCCESS = 21;
	
	/**
	 *	提现失败
	 */
	public static final int  APPLYCAHSFAIL = 22;
	
	/**
	 *	T0白名单查询成功/限额变更查询成功
	 */
	public static final int  DOSELECTCARDSUCCESS = 23;
	
	/**
	 *	结算状态查询
	 */
	public static final int  DOADDT0APPLAYSUCCESS = 24;

	/**
	 *	限额变更申请成功
	 */
	public static final int  DOINSERTQUOTACHANGESUCCESS = 25;

	/**
	 *	验证验证码，加入白名单成功
	 */
	public static final int  DOCHKVAILDSUCCESS = 26;

	/**
	 *	验证验证码，加入白名单失败
	 */
	public static final int  DOCHKVAILDFAIL = 27;


	
	/**
	 *提现、推荐用户、保存银行卡或者支付宝信息成功--------------------------------------------------------------
	 */
	public static final int  VENDERSUCCESS = 11;
	
	/**
	 *提现、推荐用户、保存银行卡或者支付宝信息失败
	 */
	public static final int  VENDERERROR = 12;
	
	/**
	 * 推荐记录查询/提现记录查询/账户金额详情
	 */
	public static final int  RECOMMENDSUCCESS = 13;
	
	/**
	 * 收藏
	 */
	public static final int  FAVORITESUCCESS = 14;
	
	/**
	 * 取消收藏
	 */
	public static final int  UNFAVORITESUCCESS = 15;
	
	/**
	 * 车源查询成功
	 */
	public static final int  CALLSFRAGSUCCESS = 16;
	
	/**
	 * 订单查询成功
	 */
	public static final int  ORDERSUCCESS = 17;
	
	/**
	 * 订单详情查询成功
	 */
	public static final int  ORDERDETAILSUCCESS = 18;
	
	/**
	 * 品牌列表查询成功
	 */
	public static final int  BRANDLISTSUCCESS = 19;
	
	/**
	 * 预订成功
	 */
	public static final int  ORDERSAVESUCCESS = 20;
	
	/**
	 * 车源详情
	 */
	public static final int ITEMDETAILSUCCESS = 21;
	
	/**
	 * 车源无报价
	 */
	public static final int NOQUOTATION = 22;
	
	/**
	 * 登出
	 */
	public static final int LOGOUTSUCCESS = 23;
	
	/************************************************************************/
	/**
	 *下载图片完成
	 */
	public static final int  BITMAPOK= 7;
	
	
	
	/**
	 * 详情查询成功
	 */
	public static final int  CRMDETAILSUCCESS = 9;
	
	/**
	 * 反馈记录查询成功
	 */
	public static final int  CRMFEEDBACKSUCCESS = 10;
	
	/**
	 * 品牌查询成功
	 */
	public static final int  CRMBRANDLISTSUCCESS = 11;
	
	/**
	 * 车型查询成功
	 */
	public static final int  CRMCARLISTSUCCESS = 12;
	
	/**
	 * 车款查询成功
	 */
	public static final int  CRMITEMLISTSUCCESS = 13;
	
	/**
	 * 反馈记录添加成功
	 */
	public static final int  CRMADDFEEDBACKSUCCESS = 14;
	
	/**
	 * 金融方案添加成功
	 */
	public static final int  CRMADDFINANCIALSUCCESS = 15;
	
	/**
	 * 登出成功
	 */
	public static final int  CRMLOGUTSUCCESS = 16;
	
	/**
	 * 推送成功
	 */
	public static final int  CRMDEVICETOKENSUCCESS = 17;
	
	/**
	 * 兑换信息成功
	 */
	public static final int  CRMEXCHANGEINFOSUCCESS = 18;
	
	/**
	 * 积分记录成功
	 */
	public static final int  CRMSCORELISTSUCCESS = 19;
	
	/**
	 * 兑换记录成功
	 */
	public static final int  CRMEXCHANGELISTSUCCESS = 20;
	
	/**
	 * 保存兑换信息成功
	 */
	public static final int  CRMEXCHANGESAVESUCCESS = 21;
	
	/**
	 *获取门店列表
	 */
	public static final int SEARCHVENDERSUCCESS=22;
	
	/**
	 *保存申请门店数据
	 */
	public static final int SAVEVENDERAPPLYSUCCESS=23;
	
	/**
	 *获取城市列表
	 */
	public static final int CITYCODESUCCESS=24;
	
	/**
	 *拉取联系人列表成功
	 */
	public static final int  CHATRECENTSUCCESS = 25;
	
	/**
	 *最新聊天记录成功
	 */
	public static final int  RECENTSUCCESS = 26;
	
	/**
	 * 发送成功 用于handler消息识别
	 */
	public static final int   SENDSUCCESS= 27;
	
	/**
	 * 发送失败 用于handler消息识别
	 */
	public static final int   SENDFAIL= 28;
	
	/**
	 * 发送失败 用于handler消息识别
	 */
	public static final int   FINISHREFLASH= 29;
	
	/**
	 * 从数据库加载更多消息 用于handler消息识别
	 */
	public static final int   LOADMORE= 30;
	
	/**
	 * 刷新LIST 用于handler消息识别
	 */
	public static final int   DBREFLASHLIST= 31;
	
	/**
	 * 获取未读消息的数量
	 */
	public static final int CHATNEWSUCCESS=32;
	
	/**
	 *更新密码成功
	 */
	public static final int  UPDATEPASSWORDSUCCESS = 33;
	
	/**
	 *更新密码成功
	 */
	public static final int  UPDATEPASSWORDFAIL = 34;
	
	/**
	 *账户金额
	 */
	public static final int  ACCOUNTBALANCESUCCESS = 35;
	
	/**
	 *提现账号信息
	 */
	public static final int  APPLYNUMBERSUCCESS = 36;
	/**
	 *查询终端状态
	 */
	public static final int  DOSELECTSTUSUCCESS = 37;
	/**
	 *app更新
	 */
	public static final int  UPDATESUCCESS = 38;

	
	
	/**
	 * 推送失败
	 */
	public static final int  CRMDEVICETOKENFAILURE = 40;
	
	/**
	 *初始化TOKEN失效内容
	 */
	public static final int  TOKEN_DISABLE= 99;
	
	/**
	 *初始化TOKEN失效内容
	 */
	public static final int  COLLECTIONNEWSFAIL= 100;
}
