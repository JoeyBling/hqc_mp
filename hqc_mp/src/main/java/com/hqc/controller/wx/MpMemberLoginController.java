package com.hqc.controller.wx;

import com.google.code.kaptcha.Producer;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpSmsCodeEntity;
import com.hqc.service.MpIntegralRecordService;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpSmsCodeService;
import com.hqc.service.MpVipLevelService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.R;
import com.hqc.util.RRException;
import com.hqc.util.wx.RandomCardNumberUtil;
import com.hqc.util.wx.WxUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 用户登录
 *
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月26日
 */
@Controller
@RequestMapping("/wx")
public class MpMemberLoginController extends BaseController {

    @Resource
    private MpSmsCodeService mpSmsCodeService;
    @Resource
    private MpMemberService mpMemberService;
    @Resource
    private MpVipLevelService mpVipLevelService;
    @Resource
    private MpIntegralRecordService mpIntegralRecordService;
    @Resource
    private Producer producer;

    /**
     * 默认访问页面
     *
     * @return String
     */
    @RequestMapping(value = {"", "/"})
    public String redirect(HttpServletRequest request) {
        if (isLogin(request))
            return "/wx/user/userCenter.ftl";
        else
            return "/wx/user/login.ftl";
    }

    /**
     * 用户图片验证码
     *
     * @param type     验证码类型 1登录 2注册 (默认登录)
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("captcha.jpg")
    public void captcha(Integer type, HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成文字验证码
        String text = producer.createText();
        logger.info("\n图片验证码:" + text);
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到session
        if (null == type || type == 1) // 会员登录
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_LOGIN_IMAGE_KAPTCHA_SESSION_KEY, text);
        else if (type == 2) // 会员注册
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_REG_IMAGE_KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 用户手机验证码
     *
     * @param type     验证码类型 1注册 2找回密码 (默认注册) 3更换新手机号 4更新新手机验证原手机
     * @param phone    手机号
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("phoneCode")
    @ResponseBody
    public R phoneCode(Integer type, String phone, HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        if (!JoeyUtil.regex("^\\d{11}$", phone)) {
            return R.error("请输入正确的手机号码");
        }
        String redisFlag = "MpMember_PHONE_Num_" + phone;
        MpSmsCodeEntity smsCode = new MpSmsCodeEntity();
        // 生成手机验证码
        String phoneCode = producer.createText();
        smsCode.setCode(phoneCode);
        smsCode.setCreateTime(DateUtils.getCurrentUnixTime());
        smsCode.setTel(phone);
        smsCode.setExpireTime(DateUtils.getCurrentUnixTime() + 1200000L);
        // 保存到session
        if (null == type || type == 1) {// 会员注册
            if (mpMemberService.queryMpMemberInfoPhone(phone).size() > 0) {
                return R.error("该手机号已注册，请直接登录");
            }
            logger.info("\n会员注册手机号:" + phone + ",下发验证码:" + phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_REG_PHONE_SESSION_KEY, phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_REG_PHONECODE_SESSION_KEY, phone);
        } else if (type == 2) {// 找回密码
            if (mpMemberService.queryMpMemberInfoPhone(phone).size() <= 0) {
                return R.error("该手机号未注册，请先注册");
            }
            logger.info("\n会员找回密码手机号:" + phone + ",下发验证码:" + phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_FINDPWD_PHONE_SESSION_KEY, phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_REPWD_PHONECODE_SESSION_KEY, phone);
        } else if (type == 3) {// 更换新手机号
            if (mpMemberService.queryMpMemberInfoPhone(phone).size() > 0) {
                return R.error("该手机号已注册，请检查");
            }
            logger.info("\n会员更换新手机号:" + phone + ",下发验证码:" + phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_NEW_PHONE_CODE_SESSION_KEY, phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_NEW_PHONE_SESSION_KEY, phone);
        } else if (type == 4) {// 验证原手机
            logger.info("\n会员验证手机号:" + phone + ",下发验证码:" + phoneCode);
            request.getSession().setAttribute(
                    WxUtil.MP_MEMBER_OLD_PHONE_CODE_SESSION_KEY, phoneCode);
        }
        // 实现一个手机号只能一天发发送10次
        Object redisPhoneNum = redisUtil.get(redisFlag);
        if (null != redisPhoneNum) {
            int newRedisPhoneNum = (Integer) redisPhoneNum;
            if (newRedisPhoneNum >= 10) {
                return R.error("同一个手机号码一天只能接收10次验证码");
            } else {
                newRedisPhoneNum++; // 次数+1
                redisUtil.evict(redisFlag);
                redisUtil.put(redisFlag, newRedisPhoneNum, 86400L, false);
            }
        } else {
            redisUtil.put(redisFlag, 1, 86400L, false);
        }
        mpSmsCodeService.save(smsCode); // 保存一个手机短信验证码
        return R.ok().put("phoneCode", phoneCode);
    }

    /**
     * 会员登录
     *
     * @param username     用户名
     * @param password     用户密码
     * @param validateCode 验证码
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @return Map
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public R login(String username, String password, String validateCode,
                   HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Object openId = getSessionOpenId(request); // OPENID
        Object unionid = getSessionUnionid(request); // UNIONID
        if ((null != openId && openId instanceof String)
                && (null != unionid && unionid instanceof String)) {
        } else {
            // return R.error(100, "请先进行微信授权登录"); // 暂时测试注释掉
        }
        if (StringUtils.isBlank(username)) {
            return R.error("请输入用户名");
        }
        if (StringUtils.isBlank(password)) {
            return R.error("请输入密码");
        }
        if (StringUtils.isBlank(validateCode)) {
            return R.error(4, "请输入验证码");
        }
        Object memberKey = request.getSession().getAttribute(
                WxUtil.MP_MEMBER_LOGIN_IMAGE_KAPTCHA_SESSION_KEY);
        if (null == memberKey
                || !memberKey.toString().equalsIgnoreCase(validateCode)) {
            request.getSession().removeAttribute(
                    WxUtil.MP_MEMBER_LOGIN_IMAGE_KAPTCHA_SESSION_KEY); // 移除Key
            return R.error(4, "请输入正确的验证码");
        }
        // sha256加密
        password = new Sha256Hash(password).toHex();
        MpMemberEntity member = mpMemberService.login(username, password);
        if (null == member)
            return R.error(4, "用户名或密码错误，请重试");

        // member.setOpenId(openId.toString());
        // member.setUnionid(unionid.toString());
        mpMemberService.updateOpenId(member); // 更新OPENID
        logger.info("会员:" + member.getPhone() + "登录，OpenID更新!");
        updateMember(request, member); // 存入session

        return R.ok();
    }

    /**
     * 会员注册
     *
     * @param phone           注册手机号
     * @param phoneCode       手机验证码
     * @param password        登录密码
     * @param password_PwdTwo 确认登录密码
     * @param validateCode    验证码
     * @param request         HttpServletRequest
     * @param response        HttpServletResponse
     * @return Map
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/user/reg", method = RequestMethod.POST)
    public R reg(String phone, String phoneCode, String password,
                 String password_PwdTwo, String validateCode,
                 HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Object openId = getSessionOpenId(request); // OPENID
        Object unionid = getSessionUnionid(request); // UNIONID
        if ((null != openId && openId instanceof String)
                && (null != unionid && unionid instanceof String)) {
        } else {
            // return R.error(100, "请先进行微信授权登录"); // 暂时测试注释掉
        }
        validate(phone, password, password_PwdTwo);
        if (mpMemberService.queryMpMemberInfoPhone(phone).size() > 0) {
            return R.error("该手机已注册，请直接登录");
        }
        Object phoneKey = request.getSession().getAttribute(
                WxUtil.MP_MEMBER_REG_PHONECODE_SESSION_KEY); // 防止获取验证码后更改手机号恶意注册
        if (null == phoneKey || !phoneKey.toString().equals(phone)) {
            return R.error("请先获取验证码");
        }
        if (StringUtils.isBlank(validateCode)) {
            return R.error("请输入验证码");
        }
        Object memberKey = request.getSession().getAttribute(
                WxUtil.MP_MEMBER_REG_IMAGE_KAPTCHA_SESSION_KEY);
        if (null == memberKey
                || !memberKey.toString().equalsIgnoreCase(validateCode)) {
            request.getSession().removeAttribute(
                    WxUtil.MP_MEMBER_REG_IMAGE_KAPTCHA_SESSION_KEY); // 移除Key
            return R.error(4, "请输入正确的验证码");
        }
        if (StringUtils.isBlank(phoneCode)) {
            return R.error("请输入手机验证码");
        }
        Object phoneCodeKey = request.getSession().getAttribute(
                WxUtil.MP_MEMBER_REG_PHONE_SESSION_KEY);
        if (null == phoneCodeKey || !phoneCodeKey.toString().equals(phoneCode)) {
            return R.error("请输入正确手机验证码");
        }
        MpMemberEntity mpMember = new MpMemberEntity();
        mpMember.setPassword(password); // 登录密码
        mpMember.setPhone(phone); // 注册手机号
        // mpMember.setOpenId(String.valueOf(openId));
        // mpMember.setUnionid(String.valueOf(unionid));
        String generate = RandomCardNumberUtil.generateCardNum();
        boolean flag = true;
        while (flag) {
            if (mpMemberService.queryByCardNo(generate) != null) {
                generate = RandomCardNumberUtil.generateCardNum(); // 已存在此会员卡号，重新生成
            } else {
                flag = false;
            }
        }
        mpMember.setCardNo(generate); // 会员卡号
        mpMember.setStatus(1); // 1、启用 2、禁用
        mpMember.setVipLevel(Integer.valueOf(mpVipLevelService.getMin().getId()
                .toString())); // 会员级别，关联会员级别表ID
        mpMember.setIntegral(100L); // 用户当前总积分
        mpMember.setLastYearIntegral(0L); // 上一年度积分
        mpMember.setCurrentYearIntegral(100L); // 本年度积分
        mpMemberService.save(mpMember);
        return R.ok();
    }

    /**
     * 找回密码
     *
     * @param phone     手机号
     * @param phoneCode 手机验证码
     * @param password  新密码
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @return Map
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/user/rePwd", method = RequestMethod.POST)
    public R rePwd(String phone, String phoneCode, String password,
                   HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        validate(phone, password, password);
        Object memberKey = request.getSession().getAttribute(
                WxUtil.MP_MEMBER_FINDPWD_PHONE_SESSION_KEY);
        if (StringUtils.isBlank(phoneCode)) {
            return R.error("请输入手机验证码");
        }
        if (null == memberKey || !memberKey.toString().equals(phoneCode)) {
            request.getSession().removeAttribute(
                    WxUtil.MP_MEMBER_REG_IMAGE_KAPTCHA_SESSION_KEY); // 移除Key
            return R.error("请输入正确手机验证码");
        }
        Object phoneKey = request.getSession().getAttribute(
                WxUtil.MP_MEMBER_REG_PHONECODE_SESSION_KEY); // 防止获取验证码后更改手机号恶意操作
        if (null == phoneKey || !phoneKey.toString().equals(phone)) {
            return R.error("请先获取验证码");
        }
        MpMemberEntity mpMember = mpMemberService.queryMpMemberInfoPhone(phone)
                .get(0);
        if (mpMember == null)
            return R.error("该手机号未注册，请先注册");
        mpMember.setPassword(password); // 登录密码
        mpMember.setUpdateTime(DateUtils.getCurrentUnixTime()); // 信息更新时间
        mpMemberService.update(mpMember);
        return R.ok();
    }

    /**
     * 用户退出
     *
     * @param request HttpServletRequest
     * @return FTL
     * @throws Exception
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception {
        if (isLogin(request)) {
            MpMemberEntity member = getMember(request);
            request.getSession().removeAttribute(
                    WxUtil.MP_MEMBER_LOGIN_SESSION_KEY);
            member.setOpenId(null);
            member.setUnionid(null);
            mpMemberService.updateOpenId(member); // 更新OPENID
            logger.info("会员:" + member.getPhone() + "退出，OpenID和UNIONID清空!");
            request.getSession().removeAttribute(
                    WxUtil.MP_MEMBER_LOGIN_SESSION_OPENID_KEY);
            request.getSession().removeAttribute(
                    WxUtil.MP_MEMBER_LOGIN_SESSION_UNIONID_KEY);
        }
        return "redirect:/wx/user/login.html";
    }

    /**
     * 验证参数
     *
     * @param phone           登录手机号
     * @param password        登录密码
     * @param password_PwdTwo 登录确认密码
     */
    private void validate(String phone, String password, String password_PwdTwo) {
        if (StringUtils.isBlank(phone)) {
            throw new RRException("未输入手机号或手机号格式有误");
        }
        if (!JoeyUtil.regex("^\\d{11}$", phone)) {
            throw new RRException("未输入手机号或手机号格式有误");
        }
        if (StringUtils.isBlank(password)) {
            throw new RRException("请输入登录密码");
        }
        if (StringUtils.isBlank(password_PwdTwo)) {
            throw new RRException("请确认登录密码");
        }
        if (!password.equals(password_PwdTwo)) {
            throw new RRException("两次密码输入不一致");
        }
    }
}
