package com.hqc.controller.wx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hqc.entity.MpMemberEntity;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpSignInService;
import com.hqc.util.Constant;
import com.hqc.util.DateUtils;
import com.hqc.util.FileUtil;
import com.hqc.util.R;
import com.hqc.util.Constant.UploadType;

@RestController
@RequestMapping("/wx/user/headPort")
public class MpUserHeadPortController extends WxAuthController {

	/**
	 * 上传文件保存的路径
	 */
	protected String uploadPath;

	/**
	 * 存放路径上下文
	 */
	protected String fileContextPath;

	/**
	 * 上传文件类型
	 */
	protected String fileType;

	/**
	 * 上传文件名称
	 */
	protected String fileName;
	@Resource
	private MpMemberService memberService;
	@Resource
	private MpSignInService mpSignInService;

	@RequestMapping("/queryMember")
	public R queryMember(HttpServletRequest request) {
		MpMemberEntity memberEntity = getMember(request);
		MpMemberEntity member=memberService.queryMpMemberInfoId(memberEntity.getId());
		if (memberEntity == null) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		return R.ok().put("member", member);
	}

	@RequestMapping(value = "/updateHeadPort", method = RequestMethod.POST)
	public R updateHeadPort(@RequestParam("name") String flag,
			Integer uploadType, HttpServletRequest request)
			throws IllegalStateException, IOException {
		MpMemberEntity memberEntity = getMember(request);
		if (memberEntity == null) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		uploadPath = Constant.getUploadPath(); // 上传文件保存的路径
		fileContextPath = Constant.getFileContextPath(); // 存放路径上下文
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile(flag);// 获取上传文件
		if (file == null)
			return R.error("上传文件为空"); // 判断上传的文件是否为空
		uploadPath = request.getServletContext().getRealPath(uploadPath)
				+ File.separator;
		fileName = file.getOriginalFilename();
		logger.info("上传的文件原名称:" + fileName);
		// 上传文件类型
		fileType = fileName.indexOf(".") != -1 ? fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length()) : null;

		logger.info("上传文件类型:" + fileType);
		// 自定义的文件名称
		String trueFileName = getTrueFileName(fileName, uploadType);
		fileContextPath += File.separator + trueFileName;
		// 防止火狐等浏览器不显示图片
		fileContextPath = fileContextPath.replace("\\", "/");
		uploadPath += trueFileName;
		File fileUpload = new File(uploadPath); // 上传文件后的保存路径
		// 创建父级目录
		FileUtil.createParentPath(fileUpload);
		logger.info("存放文件的路径:" + uploadPath);
		file.transferTo(fileUpload);// 进行文件处理
		fileHandle(fileUpload);
		MpMemberEntity entity = new MpMemberEntity();
		entity.setId(memberEntity.getId());
		entity.setAvatar(fileContextPath);
		mpSignInService.update(entity);
		return R.ok();
	}

	/**
	 * 进行文件处理
	 * 
	 * @param fileUpload
	 *            File
	 * @throws IOException
	 */
	private void fileHandle(File file) throws IOException {
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			if (width > 800)
				logger.info("进行图片压缩处理...");
			Thumbnails.of(file).height(800).toFile(file);
			logger.info("图片压缩处理完毕...");
		} catch (Exception e) {
			logger.info("上传的文件不是图片");
		}
	}

	private String getTrueFileName(String file_Name, Integer uploadType) {
		StringBuffer bf = new StringBuffer();
		if (null == uploadType) {
		} else if (uploadType == UploadType.avatar.getValue()) {
			bf.append("memberImg" + File.separator);
		}
		return bf.append(
				DateUtils.format(new Date(), Constant.uploadSavePathFormat)
						+ File.separator
						+ String.valueOf(System.currentTimeMillis()) + fileName
						+ ".jpg").toString();
		// 因为读取base64转换成图片时没有图片的格式，所以设置为jpg格式
	}
}
