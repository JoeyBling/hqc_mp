package com.hqc.activity;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hqc.entity.Mp520Entity;
import com.hqc.service.Mp520Service;
import com.hqc.util.wx.Mp520Util;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 华侨城520活动
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Component
public class LiveActivity {

	protected Logger logger = Logger.getLogger(getClass());

	@Resource
	protected WxMpService wxMpService;
	@Resource
	protected Mp520Service mp520Service;
	@Resource
	protected Mp520Util mp520Util;

	public WxMpXmlOutMessage execute(WxMpXmlMessage inMessage)
			throws WxErrorException {
		String sex = inMessage.getContent().substring(0, 1); // 性别

		// 520活动记录
		Mp520Entity queryByOpenId = mp520Service.queryByOpenIdAny(inMessage
				.getFromUser());
		if (null != queryByOpenId) { // 此用户已参加活动 直接返回数字
			if (inMessage.getFromUser().equals(queryByOpenId.getManOpenId())) { // 已经以男的身份参加
				return WxMpXmlOutMessage
						.TEXT()
						.content(
								"您已经以【男单身狗】的身份成功参加华侨城520活动\n请牢记您的数字:\n"
										+ queryByOpenId.getRandomNumber())
						.fromUser(inMessage.getToUser())
						.toUser(inMessage.getFromUser()).build();
			} else if (inMessage.getFromUser().equals(
					queryByOpenId.getWomanOpenId())) {// 已经以女的身份参加
				return WxMpXmlOutMessage
						.TEXT()
						.content(
								"您已经以【女单身狗】的身份成功参加华侨城520活动\n请牢记您的数字:\n"
										+ queryByOpenId.getRandomNumber())
						.fromUser(inMessage.getToUser())
						.toUser(inMessage.getFromUser()).build();
			}
			return WxMpXmlOutMessage
					.TEXT()
					.content(
							"恭喜\"" + inMessage.getContent()
									+ "\"，您已成功参加东部华侨城520活动！您的活动号码为：\n"
									+ queryByOpenId.getRandomNumber()
									+ "\n请在5月20日当天前往东部华侨城园区寻找你的有缘人吧！")
					.fromUser(inMessage.getToUser())
					.toUser(inMessage.getFromUser()).build();
		}

		// 查询未匹配成功的随机数总数
		int queryTotal = mp520Service.queryMatchTotal(0);
		if (queryTotal < 10) { // 随机数可用小于10再次生成50个
			mp520Util.createRandom(50);
		}

		// 获取用户信息
		WxMpUser userInfo = wxMpService.getUserService().userInfo(
				inMessage.getFromUser());
		logger.info(userInfo.toString());

		int i = 0; // 随机数
		boolean isWinning = false; // 是否中奖
		Mp520Entity byNum = null; // 东部华侨城520活动记录表

		if ("男".equals(sex)) {
			// 查询待匹配男的随机数总数
			int manTotal = mp520Service.queryMatchTotal(1);
			if (manTotal > 0) { // 进行随机匹配
				i = Integer.valueOf(mp520Util.getRandom(1).getRandomNumber()
						.toString());
				isWinning = true;
				byNum = mp520Service.getByNum(i);
				// 已匹配女性
			} else {
				i = Integer.valueOf(mp520Util.getRandom(0).getRandomNumber()
						.toString());
				isWinning = false;
				byNum = null;
			}
		} else if ("女".equals(sex)) {
			// 查询待匹配女的随机数总数
			int womanTotal = mp520Service.queryMatchTotal(2);
			if (womanTotal > 0) { // 进行随机匹配
				i = Integer.valueOf(mp520Util.getRandom(2).getRandomNumber()
						.toString());
				isWinning = true;
				byNum = mp520Service.getByNum(i);
				// 已匹配男性
			} else {
				i = Integer.valueOf(mp520Util.getRandom(0).getRandomNumber()
						.toString());
				isWinning = false;
				byNum = null;
			}
		}
		Mp520Entity mp520 = mp520Service.getByNum(i);
		if ("男".equals(sex)) {
			// 公众号标识openid
			mp520.setManOpenId(inMessage.getFromUser());
			mp520.setManNickName(userInfo.getNickname()); // 昵称
		} else if ("女".equals(sex)) {
			// 公众号标识openid
			mp520.setWomanOpenId(inMessage.getFromUser());
			mp520.setWomanNickName(userInfo.getNickname()); // 昵称
		}
		mp520Service.updateByPrimaryKey(mp520);
		String content = null;

		if (isWinning) { // 匹配成功
			logger.info("520有人匹配成功!号码:" + i);
			// 通知另外的一个人
			if (null != byNum) {
				WxMpKefuMessage message = null;
				if ("男".equals(sex)) { // 通知女方
					message = WxMpKefuMessage
							.TEXT()
							.toUser(byNum.getWomanOpenId())
							.content(
									"恭喜您，有人与你匹配成功\n请牢记您的数字:\n" + i
											+ "\n请在5月20日当天前往东部华侨城园区寻找你的有缘人吧！")
							.build();
				} else if ("女".equals(sex)) { // 通知男方
					message = WxMpKefuMessage
							.TEXT()
							.toUser(byNum.getManOpenId())
							.content(
									"恭喜您，有人与你匹配成功\n请牢记您的数字:\n" + i
											+ "\n请在5月20日当天前往东部华侨城园区寻找你的有缘人吧！")
							.build();
				}

				// 设置消息的内容等信息
				boolean sendKefuMessage = wxMpService.getKefuService()
						.sendKefuMessage(message);
				System.out.println(sendKefuMessage);
			}
			content = "恭喜\"" + inMessage.getContent()
					+ "\"，您已与其他人匹配成功！您的活动号码为：\n" + i
					+ "\n请在5月20日当天前往东部华侨城园区寻找你的有缘人吧！";
		} else {
			content = "恭喜\"" + inMessage.getContent()
					+ "\"，您已成功参加东部华侨城520活动！您的活动号码为：\n" + i
					+ "\n请在5月20日当天前往东部华侨城园区寻找你的有缘人吧！";
		}
		return WxMpXmlOutMessage.TEXT().content(content)
				.fromUser(inMessage.getToUser())
				.toUser(inMessage.getFromUser()).build();
	}
}
