package com.hqc.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.dao.MemberGoodsIntegralDao;
import com.hqc.entity.MpCashCouponEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.entity.MpGoodsExchangeRecordEntity;
import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpOrderRecordsEntity;
import com.hqc.entity.MpVipLevelEntity;
import com.hqc.service.MemberGoodsIntegralService;
import com.hqc.util.JoeyUtil;
/**
 * 用户积分
 * @author Administrator
 *
 */
@Service("memberGoodsIntegralService")
@Transactional
public class MemberGoodsIntegralServiceImpl implements MemberGoodsIntegralService {
	@Resource
	private MemberGoodsIntegralDao memberGoodsIntegralDao;
    /**
     * 判断是否为特殊日
     */
	@Override
	public boolean specialDay(MpMemberEntity entity) throws Exception{
	    // 会员生日为特殊日
		Long today =JoeyUtil.stampDate(new Date(), "yyyy-MM-dd");
		if(today==entity.getBirthday()){
			return true;
		}else{
			//判断是否存在特殊日
			int specialTotalDay=memberGoodsIntegralDao.getSpecialDay(today);
			if(specialTotalDay>0){
				return true;
			}else{
				return false;
			}
		}
		
	}
	/**
	 *消费获得 积分
	 * @param entity 会员
	 * @param money 消费金额
	 * @throws Exception
	 */
	public void UpdateMemberIntegral(MpMemberEntity tempEntity,Double money)throws Exception{
		// 查找会员
		MpMemberEntity entity=memberGoodsIntegralDao.findMpMemberEntity(tempEntity);
		//查找会员级别
		MpVipLevelEntity levelEntity=memberGoodsIntegralDao.getLevelByMember(entity);
		//判断是否特殊日
		boolean specialTag=specialDay(entity);
		int tempIntegral=0;
		if(specialTag==true){
			//如果是特殊日
			 tempIntegral=(int) (money*levelEntity.getSpecialIntegralRule()*levelEntity.getIntegralCoefficient());
		}else{
			//不是特别日
			tempIntegral=(int) (money*levelEntity.getNormalIntegralRule()*levelEntity.getIntegralCoefficient());
		}
		//添加积分记录
		addMemberIntegralRecord(entity,tempIntegral,1);
		//修改会员积分
		addMemberIntegral(entity, tempIntegral);
		
	}
	/**
	 * 添加会员积分记录 
	 * @param entity 
	 * @param tempIntegral 积分
	 * @param integralType 积分类型 1 进账 2 出账
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	 public void addMemberIntegralRecord(MpMemberEntity entity,int tempIntegral,int integralType) throws Exception{
		MpIntegralRecordEntity recordEntity=new MpIntegralRecordEntity();
		recordEntity.setMemberId(entity.getId());
		recordEntity.setIntegral(Long.valueOf(tempIntegral));
		if(integralType==1){
			recordEntity.setAbout("得到积分");
		}else{
			recordEntity.setAbout("积分兑换礼品");
		}
		recordEntity.setIntegralType(integralType);
		//创建时间
		Long createTime =JoeyUtil.stampDate(new Date(), "yyyy-MM-dd");
		recordEntity.setCreateTime(createTime);
		memberGoodsIntegralDao.addIntegralRecord(recordEntity);
	}
	/**
	 * 修改会员积分
	 * @param entity
	 * @param tempIntegral
	 * @throws Exception
	 */
	/*public void addOfMiniuteMemberIntegral(MpMemberEntity entity,int tempIntegral,int integralType)throws Exception{
		if(integralType==1){
			// 进账 积分相加
			addMemberIntegral(entity,tempIntegral);
		}else{
			//如果是出账  先减去上年度积分 
			miniuteMemberIntegral(entity, tempIntegral);	
	  } 
	}*/
	/**
	 * 增加积分
	 */
	public void addMemberIntegral(MpMemberEntity entity,int tempIntegral){
		entity.setCurrentYearIntegral(entity.getCurrentYearIntegral()+tempIntegral);
		Long tempTotalIntegral=entity.getIntegral()+tempIntegral;
		entity.setIntegral(tempTotalIntegral);
		List<MpVipLevelEntity> levelList=memberGoodsIntegralDao.getAllVipLevelList();
		if(levelList!=null&&levelList.size()>0){
		for(int i=0;i<levelList.size();i++){
			//会员等级最小积分
			Long minIntegral=levelList.get(i).getMinIntegral();
			//会员等级最大积分
			Long maxIntegral=levelList.get(i).getMaxIntegral();
			if(tempTotalIntegral>=minIntegral&&tempTotalIntegral<=maxIntegral){
			int levelid=levelList.get(i).getId().intValue();
			entity.setVipLevel(levelid);	
			}
			memberGoodsIntegralDao.updateMemberIntegral(entity);
		}
		}
	}
	/**
	 * 积分相减
	 */
    public  void miniuteMemberIntegral(MpMemberEntity entity,int tempIntegral){
    	Long lastYearIntegeral=entity.getLastYearIntegral();
		
		if(lastYearIntegeral>0L){
			//如果去年还有积分
			Long tempLastYearIntegr=lastYearIntegeral-tempIntegral;
			if(tempLastYearIntegr<0L){
				//去年积分相减 如果小于0
				Long currentYearIntegral=entity.getCurrentYearIntegral()+tempLastYearIntegr;
				entity.setCurrentYearIntegral(currentYearIntegral);
				entity.setLastYearIntegral(0L);
			}else{
				//如果大于0
				entity.setLastYearIntegral(tempLastYearIntegr);
			}
		}else{
			//去年没有积分
			Long tempCurrentIntegral=entity.getCurrentYearIntegral()-tempIntegral;
			entity.setCurrentYearIntegral(tempCurrentIntegral);
			
		}
		Long tempTotalIntegral=entity.getIntegral()-tempIntegral;
		entity.setIntegral(tempTotalIntegral);
		memberGoodsIntegralDao.updateMemberIntegral(entity);
		
	
    }
    /**
	  * 添加积分商品兑换记录
	  */
	public void insert(MpGoodsExchangeRecordEntity entity){
		memberGoodsIntegralDao.insert(entity);
	}
	/**
	 * 修改门票库存
	 */
	public void updateGoodsReporty(MpGoodsEntity entity){
		memberGoodsIntegralDao.updateGoodsReporty(entity);
	}
	/**
	 * 修改代金卷库存
	 */
	public void updateCashReporty(MpCashCouponEntity entity){
		memberGoodsIntegralDao.updateCashReporty(entity);
	}
	 /**
	  * 添加进订单表
	  */
	public void insertMpOrderRecord(MpOrderRecordsEntity entity){
		memberGoodsIntegralDao.insertMpOrderRecord(entity);
	}
	 /**
	   * 查询订单号是否重复
	   */
	public int getOrderNoByNewNo(String orderNo){
		return memberGoodsIntegralDao.getOrderNoByNewNo(orderNo);
	}
}
