package com.hqc.util.wx;

import javax.annotation.Resource;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 微信公众号支持入口Util
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月11日
 * 
 */
public abstract class WXMpSupport {

	protected Logger logger = Logger.getLogger(WXMpSupport.class);

	/**
	 * 微信推送过来的消息，xml格式
	 */
	protected WxMpXmlMessage inMessage;

	/**
	 * 微信API的Service
	 */
	@Resource
	protected WxMpService wxMpService;

	/**
	 * WX调用入口，进行数据接收，事件分发
	 * 
	 * @param inMessage
	 *            微信推送过来的消息，xml格式
	 * @return WxMpXmlOutMessage
	 * @throws Exception
	 */
	public WxMpXmlOutMessage execute(WxMpXmlMessage inMessage) throws Exception {
		this.inMessage = inMessage;
		// 消息分发处理
		return dispatchMessage();
	}

	/**
	 * 消息事件分发
	 * 
	 * @return WxMpXmlOutMessage
	 * @throws Exception
	 */
	private WxMpXmlOutMessage dispatchMessage() throws Exception {
		logger.info("消息事件分发");
		if (StringUtils.isBlank(inMessage.getMsgType())) {
			logger.info("msgType is null");
		}
		String msgType = inMessage.getMsgType();
		logger.info("msgType is " + msgType);
		switch (msgType) {
		case WxConsts.XML_MSG_EVENT: // event事件分发
			return dispatchEvent();
		case WxConsts.XML_MSG_TEXT: // 文本消息处理Msgtype=text
			return onText();
		case WxConsts.XML_MSG_IMAGE: // 图像消息Msgtype=image
			return onImage();
		case WxConsts.XML_MSG_VOICE: // 语音消息 Msgtype=voice
			return onVoice();
		case WxConsts.XML_MSG_VIDEO: // 视频 消息Msgtype=video
			return onVideo();
		case WxConsts.XML_MSG_SHORTVIDEO: // 小视频 消息Msgtype=shortvideo
			return onShortVideo();
		case WxConsts.XML_MSG_LOCATION: // 地理位置消息Msgtype=location
			return onLocation();
		case WxConsts.XML_MSG_LINK: // 链接消息Msgtype=link
			return onLink();
		default:
			return onUnknown(); // 未知消息类型的错误处理逻辑，不需要处理则空方法即可
		}
	}

	/**
	 * event事件分发
	 * 
	 * @param msgType
	 *            消息类型
	 * @return WxMpXmlOutMessage
	 */
	private WxMpXmlOutMessage dispatchEvent() {
		logger.info("dispatch event,Event is:" + inMessage.getEvent()
				+ ",EventKey is:" + inMessage.getEventKey());
		String event = inMessage.getEvent();
		switch (event) {
		case WxConsts.EVT_CLICK:
			return click(); // click点击事件处理event=CLICK
		case WxConsts.EVT_SUBSCRIBE: // subscribe关注事件处理
			return subscribe();
		case WxConsts.EVT_UNSUBSCRIBE: // unSubscribe取消关注事件处理
			return unSubscribe();
		case WxConsts.EVT_SCAN: // scan事件处理
			return scan();
		case WxConsts.EVT_LOCATION: // location事件处理event=location
			return location();
		case WxConsts.EVT_VIEW: // view 事件处理event=view
			return view();
		case WxConsts.EVT_TEMPLATESENDJOBFINISH:// 模板消息发送回调
			return templateMsgCallback();
		case WxConsts.EVT_SCANCODE_PUSH: // 扫码推事件
			return scanCodePush();
		case WxConsts.EVT_SCANCODE_WAITMSG: // 扫码推事件且弹出“消息接收中”提示框的事件
			return scanCodeWaitMsg();
		case WxConsts.EVT_PIC_SYSPHOTO: // 弹出系统拍照发图的事件
			return picSysPhoto();
		case WxConsts.EVT_PIC_PHOTO_OR_ALBUM: // 弹出拍照或者相册发图的事件
			return picPhotoOrAlbum();
		case WxConsts.EVT_PIC_WEIXIN: // 扫码推事件且弹出“消息接收中”提示框的事件
			return picWeixin();
		case WxConsts.EVT_LOCATION_SELECT: // 弹出地理位置选择器的事件
			return locationSelect();
		case WxConsts.EVT_KF_CREATE_SESSION: // 客服人员有接入会话
			return kfCreateSession();
		case WxConsts.EVT_KF_CLOSE_SESSION: // 客服人员有关闭会话
			return kfCloseSession();
		case WxConsts.EVT_KF_SWITCH_SESSION: // 客服人员有转接会话
			return kfSwitchSession();
		case "WifiConnected": // (Wi-Fi连网成功)
			return WifiConnected();
		default:
			return null;
		}
	}

	/**
	 * 文本消息处理Msgtype=text
	 * 
	 * @return WxMpXmlOutMessage
	 * @throws Exception
	 */
	protected abstract WxMpXmlOutMessage onText() throws Exception;

	/**
	 * 图像消息Msgtype=image
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onImage();

	/**
	 * 语音消息 Msgtype=voice
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onVoice();

	/**
	 * 视频 消息Msgtype=video
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onVideo();

	/**
	 * 小视频 消息Msgtype=shortvideo
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onShortVideo();

	/**
	 * 地理位置消息Msgtype=location
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onLocation();

	/**
	 * 链接消息Msgtype=link
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onLink();

	/**
	 * 未知消息类型的错误处理逻辑，不需要处理则空方法即可
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage onUnknown();

	/**
	 * click点击事件处理event=CLICK
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage click();

	/**
	 * subscribe关注事件处理
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage subscribe();

	/**
	 * unSubscribe取消关注事件处理
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage unSubscribe();

	/**
	 * scan事件处理
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage scan();

	/**
	 * location事件处理event=location(上报地理位置)
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage location();

	/**
	 * view 事件处理event=view(菜单跳转链接)
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage view();

	/**
	 * 模板消息发送回调
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage templateMsgCallback();

	/**
	 * 扫码推事件
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage scanCodePush();

	/**
	 * 扫码推事件且弹出“消息接收中”提示框的事件
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage scanCodeWaitMsg();

	/**
	 * 弹出系统拍照发图的事件
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage picSysPhoto();

	/**
	 * 弹出拍照或者相册发图的事件
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage picPhotoOrAlbum();

	/**
	 * 弹出微信相册发图器
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage picWeixin();

	/**
	 * 弹出地理位置选择器的事件
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage locationSelect();

	/**
	 * 客服人员有接入会话
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage kfCreateSession();

	/**
	 * 客服人员有关闭会话
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage kfCloseSession();

	/**
	 * 客服人员有转接会话
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage kfSwitchSession();

	/**
	 * (Wi-Fi连网成功)
	 * 
	 * @return WxMpXmlOutMessage
	 */
	protected abstract WxMpXmlOutMessage WifiConnected();

}
