package com.hqc.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hqc.activity.LiveActivity;
import com.hqc.entity.MpAutoReplyEntity;
import com.hqc.service.MpAutoReplyService;
import com.hqc.service.WxService;
import com.hqc.util.Constant;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.wx.WXMpSupport;
import com.hqc.util.wx.WxUtil;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信服务实现类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月11日
 * 
 */
@Service
public class WxServiceImpl extends WXMpSupport implements WxService {

	@Resource
	protected WxMpService wxMpService;
	@Resource
	protected LiveActivity liveActivity;
	@Resource
	private MpAutoReplyService mpAutoReplyService;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 关键词自动回复响应类型 */
	protected final Integer keyWordsReply = 2;
	/** 关注公众号自动回复响应类型 */
	protected final Integer subscribeReply = 1;

	@Override
	public WxMpUser getUserInfo(String openid, String lang)
			throws WxErrorException {
		WxMpUser wxMpUser = null;
		try {
			wxMpUser = wxMpService.getUserService().userInfo(openid, lang);
		} catch (WxErrorException e) {
			logger.error(e.getError().toString(), e);
			throw e;
		}
		return wxMpUser;
	}

	@Override
	protected WxMpXmlOutMessage onText() throws Exception {

		// 关键词回复
		MpAutoReplyEntity queryByKewords = mpAutoReplyService.queryByKewords(
				keyWordsReply, inMessage.getContent());
		if (null != queryByKewords) {
			if (queryByKewords.getReplyType().equals(1)) { // 回复文字
				return WxMpXmlOutMessage.TEXT()
						.content(queryByKewords.getReplyText())
						.fromUser(inMessage.getToUser())
						.toUser(inMessage.getFromUser()).build();
			}
		}
		// 520活动
		if (new Date().before(DateUtils.parse(Constant.getLoveEndDate()))
				&& JoeyUtil.regex(Constant.getLoveActivity(),
						inMessage.getContent())) {
			return liveActivity.execute(inMessage);
		}
		return WxMpXmlOutMessage.TEXT().content(inMessage.getContent())
				.fromUser(inMessage.getToUser())
				.toUser(inMessage.getFromUser()).build();
	}

	@Override
	protected WxMpXmlOutMessage onImage() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage onVoice() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage onVideo() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage onShortVideo() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage onLocation() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage onLink() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage onUnknown() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage click() {
		if ("pairParty".equals(inMessage.getEventKey())) { // 520配对活动
			return WxMpXmlOutMessage
					.TEXT()
					.content(
							"520是虐狗日？单身狗无处可去？\n不用担心，98生活网为单身狗准备了一个意想不到的大Party\n从即日起，在98生活网订阅号对话框输入\"【性别+单身狗】\"即可得到一个数字，而这个数字是成对的，另外一个异性的单身狗也会抽到\n520当天，如果两位单身狗能找到对方，就能凭数字各自获得大礼包一份!")
					.fromUser(inMessage.getToUser())
					.toUser(inMessage.getFromUser()).build();

		}
		if (WxUtil.menuKeyCustomerService.equals(inMessage.getEventKey())) { // 人工客服
			// try {
			// WxMpKfOnlineList kfOnlineList = wxMpService.getKefuService()
			// .kfOnlineList();
			// List<WxMpKfInfo> kfOnlineList2 = kfOnlineList.getKfOnlineList();
			// Collections.sort(kfOnlineList2, new Comparator<WxMpKfInfo>() {
			// public int compare(WxMpKfInfo kefu1, WxMpKfInfo kefu2) {
			// // 客服当前正在接待的会话数
			// Integer p1 = kefu1.getAcceptedCase();
			// Integer p2 = kefu2.getAcceptedCase();
			// // 升序
			// return p1.compareTo(p2);
			//
			// // 降序
			// // return p2.compareTo(p1);
			// }
			// });
			// for (WxMpKfInfo flag : kfOnlineList.getKfOnlineList()) {
			// boolean kfSessionCreate = wxMpService.getKefuService()
			// .kfSessionCreate(inMessage.getFromUser(),
			// flag.getAccount());
			// if (kfSessionCreate && flag.getAcceptedCase() > 0) {
			// return WxMpXmlOutMessage
			// .TEXT()
			// .content(
			// "正在接入人工客服，请稍后...当前队列("
			// + flag.getAcceptedCase() + ")")
			// .fromUser(inMessage.getToUser())
			// .toUser(inMessage.getFromUser()).build();
			// }
			// }
			// } catch (WxErrorException e) {
			// e.printStackTrace();
			// }
			return WxMpXmlOutMessage.TEXT().content("正在接入人工客服，请稍后...")
					.fromUser(inMessage.getToUser())
					.toUser(inMessage.getFromUser()).build();
		} else if (WxUtil.menuKeySignIn.equals(inMessage.getEventKey())) { // 每日签到
			String oauth2buildAuthorizationUrl = wxMpService
					.oauth2buildAuthorizationUrl(
							"http://0dc41230.ngrok.io/hqc_mp/wx/oauth",
							WxConsts.OAUTH2_SCOPE_USER_INFO, "52");
			System.out.println(oauth2buildAuthorizationUrl);
			return WxMpXmlOutMessage.TEXT()
					.content(oauth2buildAuthorizationUrl)
					.fromUser(inMessage.getToUser())
					.toUser(inMessage.getFromUser()).build();
		}
		return null;
	}

	@Override
	protected WxMpXmlOutMessage subscribe() {
		// 关注回复
		MpAutoReplyEntity queryByResType = mpAutoReplyService.queryByKewords(
				subscribeReply, null);
		if (null != queryByResType) {
			if (queryByResType.getReplyType().equals(1)) { // 回复文字
				return WxMpXmlOutMessage.TEXT()
						.content(queryByResType.getReplyText())
						.fromUser(inMessage.getToUser())
						.toUser(inMessage.getFromUser()).build();
			}
		}
		return null;
	}

	@Override
	protected WxMpXmlOutMessage unSubscribe() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage scan() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage location() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage view() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage templateMsgCallback() {
		if (!inMessage.getStatus().equalsIgnoreCase("success")) { // 模版消息未推送成功
			logger.info("模版消息未推送成功:openId:" + inMessage.getOpenId()
					+ ",status:" + inMessage.getStatus());
		}
		return null;
	}

	@Override
	protected WxMpXmlOutMessage scanCodePush() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage scanCodeWaitMsg() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage picSysPhoto() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage picPhotoOrAlbum() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage picWeixin() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage locationSelect() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage kfCreateSession() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage kfCloseSession() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage kfSwitchSession() {
		return null;
	}

	@Override
	protected WxMpXmlOutMessage WifiConnected() {
		return WxMpXmlOutMessage.TEXT().content("欢迎光临98生活服务网!")
				.fromUser(inMessage.getToUser())
				.toUser(inMessage.getFromUser()).build();
	}

}
