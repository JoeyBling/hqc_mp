package com.hqc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
@Component
public class Constant {

	/** 上传保存路径文件夹名称(格式化日期格式) */
	public static String uploadSavePathFormat = "yyyyMM";

	/** 门店类目信息Redis值 */
	public static final String CATEGORIES_REDIS_NAME = "CATEGORIES_REDIS_LIST";

	/** Spring Redis缓存名称 */
	public static final String REDIS_CACHE_NAME = "hqc";

	/** 登录的用户标记 */
	public static final String loginSessionAttr = "loginedUser";

	/** 文件上传保存路径 */
	private static String uploadPath;

	/** 存放路径上下文 */
	private static String fileContextPath;

	/** 管理员ID */
	private static Long adminId;

	/** 公网项目地址 */
	private static String netAddress;

	/** 华侨城520活动 */
	private static String loveActivity;

	/** 华侨城520活动结束时间 */
	private static String loveEndDate;

	/** 调用WebService商户密钥 */
	private static String mchKey;

	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private int value;

		private MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 上传文件类型
	 * 
	 * @author Joey
	 * @project:wstro-patent
	 * @date：2017年3月24日
	 * 
	 */
	public enum UploadType {
		/**
		 * 会员头像
		 */
		avatar(0),
		/**
		 * 景区缩略图
		 */
		scenicSpotThumb(1),
		/**
		 * 图文信息內容的图片
		 */
		articleImg(2),
		/**
		 * 资讯缩略图
		 */
		articleThumb(3),

		/**
		 * 会员等级图标
		 */
		LevelIcon(4),

		/**
		 * 商品缩略图
		 */
		goodsThumb(5),
		/**
		 * 商品缩略图
		 */
		ticketThumb(6),
		/**
		 * 代金卷缩略图
		 */
		cashThumb(8),
		/**
		 * 横幅图
		 */
		bannerThumb(7),
		/**
		 * 其他
		 */
		other(-1);

		private int value;

		private UploadType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 定时任务状态
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年12月3日 上午12:07:22
	 */
	public enum ScheduleStatus {
		/**
		 * 正常
		 */
		NORMAL(0),
		/**
		 * 暂停
		 */
		PAUSE(1);

		private int value;

		private ScheduleStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 只提供读
	 * 
	 * @return String
	 */
	public static String getUploadPath() {
		return uploadPath;
	}

	@Value(value = "${file.UploadPath}")
	private void setUploadPath(String uploadPath) {
		Constant.uploadPath = uploadPath;
	}

	/**
	 * 只提供读
	 * 
	 * @return String
	 */
	public static String getFileContextPath() {
		return fileContextPath;
	}

	@Value(value = "${file.UploadPath}")
	private void setFileContextPath(String fileContextPath) {
		Constant.fileContextPath = fileContextPath;
	}

	/**
	 * 只提供读
	 * 
	 * @return String
	 */
	public static Long getAdminId() {
		return adminId;
	}

	@Value(value = "${database.adminId}")
	private void setAdminId(Long adminId) {
		Constant.adminId = adminId;
	}

	/**
	 * 只提供读
	 * 
	 * @return String
	 */
	public static String getNetAddress() {
		return netAddress;
	}

	@Value(value = "${net.address}")
	private void setNetAddress(String netAddress) {
		Constant.netAddress = netAddress;
	}

	/**
	 * 只提供读
	 * 
	 * @return String 华侨城520活动正则表达式
	 */
	public static String getLoveActivity() {
		return loveActivity;
	}

	@Value(value = "${520}")
	private void setLoveActivity(String loveActivity) {
		Constant.loveActivity = loveActivity;
	}

	/**
	 * 只提供读
	 * 
	 * @return String 华侨城520活动过期时间
	 */
	public static String getLoveEndDate() {
		return loveEndDate;
	}

	@Value(value = "${520endDate}")
	private void setLoveEndDate(String loveEndDate) {
		Constant.loveEndDate = loveEndDate;
	}

	/**
	 * 只提供读
	 * 
	 * @return String 调用WebService商户密钥
	 */
	public static String getMchKey() {
		return mchKey;
	}

	@Value(value = "${ws.mchkey}")
	private void setMchKey(String mchKey) {
		Constant.mchKey = mchKey;
	}

}
