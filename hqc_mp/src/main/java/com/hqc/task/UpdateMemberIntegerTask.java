package com.hqc.task;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.service.MpUpdateIntegralTaskService;
/**
 * 会员积分上年积分自动清零定时器
 * @author Administrator
 *
 */
@Component
public class UpdateMemberIntegerTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private MpUpdateIntegralTaskService autoUpdateIntegraltask;
	/**
	 * 修改会员积分
	 */
	@Transactional
	public void updateIntegerTask(){
		List<Map<String, Object>> list=autoUpdateIntegraltask.autoUpdateIntegral();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				String tel=list.get(i).get("tet")!=null?list.get(i).get("tet")+"":"";
				String lasterIntegralTemp=list.get(i).get("lasterIntegralTemp")!=null?list.get(i).get("lasterIntegralTemp")+"":"";
				String IntegralTemp=list.get(i).get("IntegralTemp")!=null?list.get(i).get("IntegralTemp")+"":"";
				String vipName=list.get(i).get("vipName")!=null?list.get(i).get("vipName")+"":"";
				logger.info("会员："+tel+"上年度："+lasterIntegralTemp+"积分已被清零。总积分相应调整为："+IntegralTemp+",会员级别变更为："+vipName);
				
			}
		}
	}

}
