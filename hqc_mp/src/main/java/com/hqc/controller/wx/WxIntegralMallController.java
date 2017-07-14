package com.hqc.controller.wx;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import org.springframework.aop.IntroductionInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hqc.entity.MpCashCouponEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.entity.MpGoodsExchangeRecordEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpOrderRecordsEntity;
import com.hqc.service.IntegralMallWxService;
import com.hqc.service.MemberGoodsIntegralService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.OrderNoUtils;
import com.hqc.util.R;

import freemarker.template.utility.DateUtil;


/**
 * 积分商城控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/wx/integralMall")
public class WxIntegralMallController extends WxAuthController {
	@Resource
	private IntegralMallWxService integralMallWxService;
	@Resource
	private MemberGoodsIntegralService memberGoodsIntegralService;

	/**
	 * 获取会员相关信息
	 */
	@RequestMapping("getMemberInfo")
	@ResponseBody
	public R getMemberInfo(HttpServletRequest request){
		MpMemberEntity tempMember=getMember(request);
		if(tempMember==null){
			return R.error("您尚未登录，请重新登录");
		}else{
		MpMemberEntity memberEntity=integralMallWxService.getMemberEntity(tempMember.getId());
		return R.ok().put("memberEntity", memberEntity);
		}		
	}
	/**
	 * 获取商品列表
	 */
	@RequestMapping("/getGoods")
	@ResponseBody
	public R getGoods(){
		List<Map<String, Object>> goodsList=integralMallWxService.getAllGoods();
		return R.ok().put("goodsList", goodsList);
	}
	/**
	 * 获取商品
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/getGoodInfo/{id}")
	@ResponseBody
	public R getGoodInfo(@PathVariable("id") Long id) throws NumberFormatException, ParseException{
		MpGoodsEntity entity=integralMallWxService.queryObject(id);
		R r = R.ok().put("entity", entity);
		if(entity.getDaysLimit() !=null && entity.getDaysLimit() !=0 ){
			//可选择的最大时间
			long limitDate = JoeyUtil.stampDate(new Date(),DateUtils.DATE_TIME_PATTERN)
					+(entity.getDaysLimit()*86400);
			r.put("limitDate", limitDate);
		}
		
		return r;
	}
	/**
	 * 查询客户兑换数量
	 */
	@RequestMapping("/getTotalExchangeGoods")
	@ResponseBody
	public R getTotalExchangeGoods(Integer goodsId,Integer goodstype,HttpServletRequest request)throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		Long createTime=JoeyUtil.stampDate(new Date(), "yyyy-MM-dd");
		MpMemberEntity memberEntity=getMember(request);
		map.put("createTime", createTime);
		map.put("goodsId", goodsId);
		map.put("memberId", memberEntity.getId());
		map.put("goodsType", goodstype);
		int totalExchangeGoods=integralMallWxService.getTotalGoodsExchangeByNow(map);
		return R.ok().put("totalExchangeGoods", totalExchangeGoods);
		
	}
	/**
	 * 得到兑换码
	 */
	public String phoneCode(){
		String chars = "abcdefghijklmnopqrstuvwxyz";
		String charCode=chars.charAt((int)(Math.random() * 26))+"";
		String charCode2=chars.charAt((int)(Math.random() * 26))+"";
	    int intCode=(int) (Math.random()*(9999-1000+1)+1000);
	    String exchangeCode=charCode+charCode2+intCode;
		return exchangeCode;
	}
	/**
	 * 添加积分消费记录_门票
	 */
	@RequestMapping("addExchangeGoodsRecordIntegral")
	@ResponseBody
	@Transactional
	public R addExchangeRecordIntegral(MpGoodsExchangeRecordEntity entity,String tempDate,HttpServletRequest request)throws Exception{
		
		//使用时间转化
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		Date secondDate=sdf.parse(tempDate);
		Long useTime=JoeyUtil.stampDate(secondDate, "yyyy-MM-dd");
		entity.setUseTime(useTime);
		MpMemberEntity tempMember=getMember(request);
		if(tempMember==null){
			return R.error("您尚未登录，请重新登录");
		}else {
		//应当从数据库里重新查找会员实体 不能直接用session中的实体
		MpMemberEntity memberEntity=integralMallWxService.getMemberEntity(tempMember.getId());
		MpGoodsEntity goodsEntity=integralMallWxService.queryObject( entity.getGoodsId());
		entity.setMemberId(memberEntity.getId());
		if(entity.getIntegral()>memberEntity.getIntegral()){
			return R.error("您的积分不足，无法兑换");
		}else if(!validExchangeTotal(entity,goodsEntity.getMaxExchange())){
			return R.error("您当日兑换数量已超标");
		}else if(!validExchangeCount(goodsEntity.getRepertory(),entity.getExchangeCount())){
			return R.error("库存不足,无法兑换足够数量");
		}else{
		
			//得到兑换码
		    String  exchangeCode=phoneCode();
		    entity.setExchangeCode(exchangeCode);
			Long createTime=JoeyUtil.stampDate(new Date(), "yyyy-MM-dd");
			//添加积分商品记录
			memberGoodsIntegralService.addMemberIntegralRecord(memberEntity, Integer.valueOf(entity.getIntegral()+""), 2);
			//修改积分
			memberGoodsIntegralService.miniuteMemberIntegral(memberEntity, Integer.valueOf(entity.getIntegral()+""));
			for(Long i=0L;i<entity.getExchangeCount();i++){
				//生成订单
				String orderNo=getOrderNo();
				MpGoodsExchangeRecordEntity recordEntity=new MpGoodsExchangeRecordEntity();
				//添加积分商品兑换记录
				recordEntity.setExchangeCode(exchangeCode);
				recordEntity.setExchangeCount(1L);
				recordEntity.setGoodsId(entity.getGoodsId());
				recordEntity.setGoodsType(entity.getGoodsType());
				recordEntity.setUseStatus(0);
				recordEntity.setIntegral(entity.getIntegral()/entity.getExchangeCount());
				recordEntity.setCreateTime(createTime);
				recordEntity.setOrderNo(orderNo);
				recordEntity.setUseTime(entity.getUseTime());
				recordEntity.setPersonPhone(entity.getPersonPhone());
				recordEntity.setMemberId(tempMember.getId());
				memberGoodsIntegralService.insert(recordEntity);
				
				Long tempIntegral=entity.getIntegral()/entity.getExchangeCount();
				//添加进订单表
				MpOrderRecordsEntity orderEntity=new MpOrderRecordsEntity();
				orderEntity.setMemberId(memberEntity.getId());
				orderEntity.setGoodsId(entity.getGoodsId());
				orderEntity.setItemCode(exchangeCode);
				orderEntity.setIntegral(tempIntegral.doubleValue());
				orderEntity.setItemCount(1L);
				orderEntity.setStatus(0);
				orderEntity.setType(2);
				orderEntity.setOrderNo(orderNo);
				orderEntity.setCreateTime(createTime);
				orderEntity.setPhone(entity.getPersonPhone());
				memberGoodsIntegralService.insertMpOrderRecord(orderEntity);
			}
			
			//修改门票库存
			goodsEntity.setRepertory(goodsEntity.getRepertory()-entity.getExchangeCount());
			memberGoodsIntegralService.updateGoodsReporty(goodsEntity);
			sendMessage(memberEntity, entity, goodsEntity.getGoodsName(), entity.getUseTime());
			return R.ok();
			
		}
		}
		
	}
	/**
	 * 添加积分消费记录_代金卷
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("addExchangeCashRecordIntegral")
	@ResponseBody
	@Transactional
	public R addExchangeCashRecordIntegral(MpGoodsExchangeRecordEntity entity,HttpServletRequest request) throws Exception{
		MpMemberEntity tempMember=getMember(request);
		if(tempMember==null){
			return R.error("您尚未登录，请重新登录");
		}else{
		//应当从数据库里重新查找会员实体 不能直接用session中的实体
		MpMemberEntity memberEntity=integralMallWxService.getMemberEntity(tempMember.getId());
		MpCashCouponEntity cashEntity=integralMallWxService.queryCashCouponEntity(entity.getGoodsId());
		entity.setMemberId(memberEntity.getId());
		if(entity.getIntegral()>memberEntity.getIntegral()){
			return R.error("您的积分不足，无法兑换");
		}else if(!validExchangeTotal(entity,cashEntity.getMaxExchange().longValue())){
			return R.error("您当日兑换数量已超标");
		}else if(!validExchangeCount(cashEntity.getRepertory().longValue(),entity.getExchangeCount())){
			return R.error("库存不足,无法兑换足够数量");
		}else{
			
			 String  exchangeCode=phoneCode();
			 entity.setExchangeCode(exchangeCode);
			Long createTime=JoeyUtil.stampDate(new Date(), "yyyy-MM-dd");
			//得到30天 后的日期
		
			Calendar calend = Calendar.getInstance();
			calend.add(calend.DATE, 30);
			Date endDate = calend.getTime();
			Long endTiem = JoeyUtil.stampDate(endDate, "yyyy-MM-dd");
			
			//添加积分记录
			memberGoodsIntegralService.addMemberIntegralRecord(memberEntity, Integer.valueOf(entity.getIntegral()+""), 2);
			//修改积分
			memberGoodsIntegralService.miniuteMemberIntegral(memberEntity, Integer.valueOf(entity.getIntegral()+""));
			//添加积分兑换记录
			Long countTag=entity.getExchangeCount();
			for(Long i=0L;i<countTag;i++){
				//生成订单
				String orderNo=getOrderNo();
				//添加积分兑换记录
				MpGoodsExchangeRecordEntity recordEntity=new MpGoodsExchangeRecordEntity();
				recordEntity.setGoodsId(entity.getGoodsId());
				recordEntity.setExchangeCode(exchangeCode);
				recordEntity.setMemberId(memberEntity.getId());
				recordEntity.setPersonPhone(entity.getPersonPhone());
				recordEntity.setIntegral(entity.getIntegral()/entity.getExchangeCount());
				recordEntity.setExchangeCount(1L);
				recordEntity.setUseStatus(0);
				recordEntity.setCreateTime(createTime);
				recordEntity.setOrderNo(orderNo);
				recordEntity.setGoodsType(entity.getGoodsType());
				recordEntity.setUseTime(endTiem);
				memberGoodsIntegralService.insert(recordEntity);
				
				//添加进订单表
				Long tempIntegral=entity.getIntegral()/entity.getExchangeCount();
				MpOrderRecordsEntity orderEntity=new MpOrderRecordsEntity();
				orderEntity.setMemberId(memberEntity.getId());
				orderEntity.setCouponId(entity.getGoodsId());
				orderEntity.setItemCode(exchangeCode);
				orderEntity.setIntegral(tempIntegral.doubleValue());
				orderEntity.setItemCount(1L);
				orderEntity.setStatus(0);
				orderEntity.setType(3);
				orderEntity.setOrderNo(orderNo);
				orderEntity.setCreateTime(createTime);
				orderEntity.setPhone(entity.getPersonPhone());
				memberGoodsIntegralService.insertMpOrderRecord(orderEntity);
				
			}
		
			//修改代金卷库存
			int reporty=(int) (cashEntity.getRepertory().intValue()-entity.getExchangeCount());
			cashEntity.setRepertory(reporty);
			memberGoodsIntegralService.updateCashReporty(cashEntity);
			sendMessage(memberEntity, entity, cashEntity.getCashCouponName(), endTiem);
			return R.ok();
		}
		}
	}
	/**
	 * 添加微信提醒
	 */
	public void sendMessage(MpMemberEntity memberEntity,MpGoodsExchangeRecordEntity recordEntity,String goodsName,Long useTime){
		try {
		 String endtime=JoeyUtil. stampToDateString(useTime*1000+"","yyyy-MM-dd");
			WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
			templateMessage.setToUser(memberEntity.getOpenId());
			templateMessage
					.setTemplateId("HqGyZ5EQmyl3hEwA4Z-VWmdCp6zCP3IRWSAoa57afzo");
			templateMessage.setUrl("https://www.baidu.com/");
			templateMessage.getData().add(new WxMpTemplateData("first", "尊敬的"+recordEntity.getPersonPhone()+""
					+ "用户： 您兑换的【"+goodsName+"("+recordEntity.getExchangeCount()+"张)"+"】兑换成功", "#173177"));
			templateMessage.getData().add(new WxMpTemplateData("keyword1", goodsName, "#173177"));
			templateMessage.getData().add(new WxMpTemplateData("keyword2", endtime, "#173177"));
			templateMessage.getData().add(new WxMpTemplateData("keyword3", recordEntity.getExchangeCode(), "#173177"));
			templateMessage.getData().add(new WxMpTemplateData("remark", "请到东部华侨城出示此消息进行使用", "#173177"));
			WxMpTemplateMsgService templateMsgService = wxMpService
					.getTemplateMsgService();
		
	
				templateMsgService.sendTemplateMsg(templateMessage);
			} catch (WxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 

	}
	/**
	 * 生成订单号
	 */
	public String getOrderNo(){
		String orderNo=OrderNoUtils.getOrder_no();
		int count=memberGoodsIntegralService.getOrderNoByNewNo(orderNo);
		if(count>0){
			getOrderNo();
		}
		return orderNo;
	}
	/**
	 * 验证商品库存是否大于要兑换的数量
	 */
	private boolean validExchangeCount(Long reporty,Long exchangeCount){
		if(reporty>=exchangeCount){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 验证兑换商品总数
	 * @param entity
	 * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public boolean validExchangeTotal(MpGoodsExchangeRecordEntity entity,Long maxExchange) throws NumberFormatException, ParseException{
		Map<String, Object> map=new HashMap<String, Object>();
		Long createTime=JoeyUtil.stampDate(new Date(), "yyyy-MM-dd");
	
		map.put("createTime", createTime);
		map.put("goodsId", entity.getGoodsId());
		map.put("memberId", entity.getMemberId());
		map.put("goodsType", entity.getGoodsType());
		int totalExchangeGoods=integralMallWxService.getTotalGoodsExchangeByNow(map);
		//MpGoodsEntity goodsEntity=integralMallWxService.queryObject( entity.getGoodsId());
		//Long maxExchange=goodsEntity.getMaxExchange();
		Long alTotal=entity.getExchangeCount()+totalExchangeGoods;
		if(maxExchange!=null&&maxExchange!=0L){
		   if(alTotal>maxExchange){
			   return false;
		   }else{
			   return true;
		   }
		}else{
			return true;
		}
		
	}
	/**
	 * 查询代金卷
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCashInfo/{id}")
	@ResponseBody
	public R getCashinfo(@PathVariable("id") Long id ){
		MpCashCouponEntity entity=integralMallWxService.queryCashCouponEntity(id);
		return R.ok().put("entity", entity);
	}
	/**
	 * 跳转到兑换页面
	 */
	@RequestMapping("/toIntegralMallExchange")
	public ModelAndView getView(HttpServletRequest request,Long goodsId,Integer type) {
		ModelAndView view = new ModelAndView();
	
		if (!isLogin(request)) {
			view.setViewName("/wx/user/login.ftl");
			return view;
		} else {
			view.addObject("goodsId",goodsId);
			view.addObject("type", type);
			view.setViewName("/wx/user/integralMallExchange.ftl");
			return view;
		}
		
	} 

}
