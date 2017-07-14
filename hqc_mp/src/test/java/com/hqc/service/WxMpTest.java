package com.hqc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpStoreService;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfInfo;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfOnlineList;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.store.WxMpStoreBaseInfo;
import me.chanjar.weixin.mp.bean.store.WxMpStoreInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-hqc.xml" })
public class WxMpTest {

	@Resource
	protected WxMpService wxMpService;

	/**
	 * 添加菜单
	 */
	@Test
	public void test98Menu() {
		try {
			WxMenu wxMenu = new WxMenu();
			List<WxMenuButton> listButton = new ArrayList<WxMenuButton>();
			WxMenuButton button1 = new WxMenuButton();
			button1.setName("98生活网");
			button1.setType(WxConsts.BUTTON_VIEW);
			button1.setUrl("http://www.98yew.cn");

			WxMenuButton button2 = new WxMenuButton();
			button2.setName("夜场招聘");
			button2.setType(WxConsts.BUTTON_VIEW);
			button2.setUrl("http://www.98yew.com/recruit/recruit/query/employquery.jhtml");

			WxMenuButton button3 = new WxMenuButton();
			button3.setName("个人中心");

			WxMenuButton button3_1 = new WxMenuButton();
			button3_1.setKey("signIn");
			button3_1.setName("每日签到");
			button3_1.setType(WxConsts.BUTTON_CLICK);

			WxMenuButton button3_2 = new WxMenuButton();
			button3_2.setKey("customerService");
			button3_2.setName("人工客服");
			button3_2.setType(WxConsts.BUTTON_CLICK);

			WxMenuButton button3_3 = new WxMenuButton();
			button3_3.setKey("pairParty");
			button3_3.setName("配对活动");
			button3_3.setType(WxConsts.BUTTON_CLICK);

			List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button3_1);
			subButtons.add(button3_2);
			subButtons.add(button3_3);
			button3.setSubButtons(subButtons);

			listButton.add(button1);
			listButton.add(button2);
			listButton.add(button3);
			wxMenu.setButtons(listButton);
			// 设置菜单
			System.out.println(wxMpService.getMenuService().menuCreate(wxMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询菜单
	 */
	@Test
	public void menuGet() {
		// 自定义菜单查询接口
		try {
			System.out.println(wxMpService.getMenuService().menuGet());
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除菜单
	 */
	@Test
	public void menuDelete() {
		// 自定义菜单删除接口
		try {
			wxMpService.getMenuService().menuDelete();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void kfAccountAdd() {
		try {

			// WxMpKfList kfList = wxMpService.getKefuService().kfList();
			// List<WxMpKfInfo> kfList2 = kfList.getKfList();
			// for (WxMpKfInfo wxMpKfInfo : kfList2) {
			// System.out.println(wxMpKfInfo);
			// }
			WxMpKfOnlineList kfOnlineList = wxMpService.getKefuService()
					.kfOnlineList();
			List<WxMpKfInfo> kfOnlineList2 = kfOnlineList.getKfOnlineList();

			for (WxMpKfInfo wxMpKfInfo : kfOnlineList2) {
				System.out.println(wxMpKfInfo);
			}
			// WxMpKefuService kefuService = wxMpService.getKefuService();
			// WxMpKfAccountRequest request = new WxMpKfAccountRequest();
			// request.setKfAccount("siwei@wx_98yew");
			// request.setNickName("思伟");
			// request.setInviteWx("17607930232");
			// System.out.println(kefuService.kfAccountInviteWorker(request));
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void store() throws WxErrorException {
		WxMpStoreService storeService = wxMpService.getStoreService();
		List<String> listCategories = storeService.listCategories();
		for (String string : listCategories) {
			System.out.println(string);
		}
		List<WxMpStoreInfo> listAll = storeService.listAll();
		for (WxMpStoreInfo wxMpStoreInfo : listAll) {
			System.out.println(wxMpStoreInfo);
		}
		WxMpStoreBaseInfo request = new WxMpStoreBaseInfo();
		request.setAddress("湖滨东路55号");
		request.setAvgPrice(55);
		request.setSid("2434387555");
		request.setBranchName("分店名称(江西益强微盈)");
		request.setBusinessName("门店名称(益强微盈)");
		String[] cateType = new String[1];
		cateType[0] = "美食,小吃快餐";
		request.setCategories(cateType);
		request.setCity("南昌市");
		request.setDistrict("青山湖区");
		request.setIntroduction("江西益强微盈是全球大型跨国连锁餐厅，1940 年创立于美国，在世界上 大约拥有3 万间分店。主要售卖汉堡包，以及薯条、炸鸡、汽水、冰品、沙拉、 水果等快餐食品");
		request.setLatitude(new BigDecimal(28.683755)); // 门店所在地理位置的经度
		request.setLongitude(new BigDecimal(115.930529)); // 门店所在地理位置的纬度
		request.setOffsetType(1);
		request.setOpenTime("9:30-18:00");
		request.setProvince("江西省");
		request.setRecommend("川菜，香辣鸡腿堡");
		request.setSpecial("免费WIFI，免费停车库");
		request.setTelephone("13647910412");
		storeService.add(request);
	}

	/**
	 * 通知所有用户华侨城520活动
	 * 
	 * @throws WxErrorException
	 */
	@Test
	public void noticeLoveActive() throws WxErrorException {
		WxMpMassTagMessage massMessage = new WxMpMassTagMessage();
		massMessage.setMsgType(WxConsts.MASS_MSG_TEXT);
		massMessage
				.setContent("520是虐狗日？单身狗无处可去？\n不用担心，东部华侨城为单身狗准备了一个意想不到的大Party\n从即日起，在东部华侨城服务号对话框输入\"【性别+单身狗】\"即可得到一个数字，而这个数字是成对的，另外一个异性的单身狗也会抽到\n520当天，如果两位单身狗在东部华侨城园区能找到对方，就能凭数字各自获得大礼包一份!");
		massMessage.setSendAll(true);

		WxMpMassSendResult massResult = wxMpService
				.massGroupMessageSend(massMessage);
		System.out.println(massResult);
	}

	/**
	 * 素材管理
	 * 
	 * @throws WxErrorException
	 */
	@Test
	public void materialService() throws WxErrorException {
		WxMpMaterialFileBatchGetResult materialFileBatchGet = wxMpService
				.getMaterialService().materialFileBatchGet(
						WxConsts.MEDIA_IMAGE, 0, 20);
		System.out.println(materialFileBatchGet);
	}

	/**
	 * 添加华侨城菜单
	 */
	@Test
	public void testHqcMenu() {
		try {
			WxMenu wxMenu = new WxMenu();
			List<WxMenuButton> listButton = new ArrayList<WxMenuButton>();
			WxMenuButton button1 = new WxMenuButton();
			button1.setName("在线预定");

			WxMenuButton button1_1 = new WxMenuButton();
			button1_1.setName("在线购票");
			button1_1.setType(WxConsts.BUTTON_VIEW);
			button1_1.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/");
			
			WxMenuButton button1_2 = new WxMenuButton();
			button1_2.setName("积分商城");
			button1_2.setType(WxConsts.BUTTON_VIEW);
			button1_2.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/user/integralMall.html");
			
			WxMenuButton button1_3 = new WxMenuButton();
			button1_3.setName("会员中心");
			button1_3.setType(WxConsts.BUTTON_VIEW);
			button1_3.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/user/userCenter.html");
			
			WxMenuButton button1_4 = new WxMenuButton();
			button1_4.setName("停车付费");
			button1_4.setType(WxConsts.BUTTON_VIEW);
			button1_4.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/");
			// 待定
			// WxMenuButton button1_5 = new WxMenuButton();
			// button1_5.setName("美食预定");
			// button1_5.setType(WxConsts.BUTTON_VIEW);
			// button1_5.setUrl("");
			
			List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button1_4);
			subButtons.add(button1_3);
			subButtons.add(button1_2);
			subButtons.add(button1_1);
			button1.setSubButtons(subButtons);

			WxMenuButton button2 = new WxMenuButton();
			button2.setName("游玩攻略");

			WxMenuButton button2_1 = new WxMenuButton();
			button2_1.setName("园区介绍");
			button2_1.setType(WxConsts.BUTTON_VIEW);
			button2_1.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/");
			
			WxMenuButton button2_2 = new WxMenuButton();
			button2_2.setName("最新动态");
			button2_2.setType(WxConsts.BUTTON_VIEW);
			button2_2
					.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/play/news.html");
			
			WxMenuButton button2_3 = new WxMenuButton();
			button2_3.setName("酒店餐饮");
			button2_3.setType(WxConsts.BUTTON_VIEW);
			button2_3
					.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/play/hotel.html");
			
			WxMenuButton button2_4 = new WxMenuButton();
			button2_4.setName("交通路线");
			button2_4.setType(WxConsts.BUTTON_VIEW);
			button2_4.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/");
			
			subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button2_4);
			subButtons.add(button2_3);
			subButtons.add(button2_2);
			subButtons.add(button2_1);
			button2.setSubButtons(subButtons);

			WxMenuButton button3 = new WxMenuButton();
			button3.setName("智能导游");

			WxMenuButton button3_1 = new WxMenuButton();
			button3_1.setName("3D地图");
			button3_1.setType(WxConsts.BUTTON_VIEW);
			button3_1.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/");

			WxMenuButton button3_2 = new WxMenuButton();
			button3_2.setName("天气查询");
			button3_2.setType(WxConsts.BUTTON_VIEW);
			button3_2
					.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/guide/weather.html");

			WxMenuButton button3_3 = new WxMenuButton();
			button3_3.setName("WIFI连接");
			button3_3.setType(WxConsts.BUTTON_VIEW);
			button3_3.setUrl("http://42fc3e3f.ngrok.io/hqc_mp/wx/");

			subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button3_3);
			subButtons.add(button3_2);
			subButtons.add(button3_1);
			button3.setSubButtons(subButtons);

			listButton.add(button1);
			listButton.add(button2);
			listButton.add(button3);
			wxMenu.setButtons(listButton);
			// 设置菜单
			System.out.println(wxMpService.getMenuService().menuCreate(wxMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 添加n127菜单
	 */
	@Test
	public void testn127Menu() {
		try {
			WxMenu wxMenu = new WxMenu();
			List<WxMenuButton> listButton = new ArrayList<WxMenuButton>();
			WxMenuButton button1 = new WxMenuButton();
			button1.setName("在线预定");

			WxMenuButton button1_1 = new WxMenuButton();
			button1_1.setName("在线购票");
			button1_1.setType(WxConsts.BUTTON_VIEW);
			button1_1.setUrl("http://dev2.66666684.cn/wx/ticket/ticketList.html");
			
			WxMenuButton button1_2 = new WxMenuButton();
			button1_2.setName("积分商城");
			button1_2.setType(WxConsts.BUTTON_VIEW);
			button1_2.setUrl("http://dev2.66666684.cn/wx/user/integralMall.html");
			
			WxMenuButton button1_3 = new WxMenuButton();
			button1_3.setName("会员中心");
			button1_3.setType(WxConsts.BUTTON_VIEW);
			button1_3.setUrl("http://dev2.66666684.cn/wx/user/userCenter.html");
			
			WxMenuButton button1_4 = new WxMenuButton();
			button1_4.setName("停车付费");
			button1_4.setType(WxConsts.BUTTON_VIEW);
			button1_4.setUrl("http://2016.quanyouvip.com/");
			//button1_4.setUrl("http://http://dev2.66666684.cn/wx/car/toPay");
			// 待定
			// WxMenuButton button1_5 = new WxMenuButton();
			// button1_5.setName("美食预定");
			// button1_5.setType(WxConsts.BUTTON_VIEW);
			// button1_5.setUrl("");
			
			List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button1_4);
			subButtons.add(button1_3);
			subButtons.add(button1_2);
			subButtons.add(button1_1);
			button1.setSubButtons(subButtons);

			WxMenuButton button2 = new WxMenuButton();
			button2.setName("游玩攻略");

			WxMenuButton button2_1 = new WxMenuButton();
			button2_1.setName("园区介绍");
			button2_1.setType(WxConsts.BUTTON_VIEW);
			button2_1.setUrl("http://dev2.66666684.cn/wx/list/detail?type=2");
			
			WxMenuButton button2_2 = new WxMenuButton();
			button2_2.setName("最新动态");
			button2_2.setType(WxConsts.BUTTON_VIEW);
			button2_2
					.setUrl("http://dev2.66666684.cn/wx/play/news.html");
			
			WxMenuButton button2_3 = new WxMenuButton();
			button2_3.setName("酒店餐饮");
			button2_3.setType(WxConsts.BUTTON_VIEW);
			button2_3
					.setUrl("http://dev2.66666684.cn/wx/play/hotel.html");
			
			WxMenuButton button2_4 = new WxMenuButton();
			button2_4.setName("交通路线");
			button2_4.setType(WxConsts.BUTTON_VIEW);
			button2_4.setUrl("http://dev2.66666684.cn/wx/list/detail?type=1");
			
			subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button2_4);
			subButtons.add(button2_3);
			subButtons.add(button2_2);
			subButtons.add(button2_1);
			button2.setSubButtons(subButtons);

			WxMenuButton button3 = new WxMenuButton();
			button3.setName("智能导游");

			WxMenuButton button3_1 = new WxMenuButton();
			button3_1.setName("3D地图");
			button3_1.setType(WxConsts.BUTTON_VIEW);
			button3_1.setUrl("http://dev2.66666684.cn/wx/map/index.html");

			WxMenuButton button3_2 = new WxMenuButton();
			button3_2.setName("天气查询");
			button3_2.setType(WxConsts.BUTTON_VIEW);
			button3_2
					.setUrl("http://dev2.66666684.cn/wx/guide/weather.html");

			WxMenuButton button3_3 = new WxMenuButton();
			button3_3.setName("WiFi连接");
			button3_3.setType(WxConsts.BUTTON_VIEW);
			button3_3.setUrl("http://dev2.66666684.cn/wx/wifi/link");

			subButtons = new ArrayList<WxMenuButton>();
			subButtons.add(button3_3);
			subButtons.add(button3_2);
			subButtons.add(button3_1);
			button3.setSubButtons(subButtons);

			listButton.add(button1);
			listButton.add(button2);
			listButton.add(button3);
			wxMenu.setButtons(listButton);
			// 设置菜单
			System.out.println(wxMpService.getMenuService().menuCreate(wxMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
