package com.hqc.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hqc.entity.MpParkingcChargeEntity;
import com.hqc.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-hqc.xml" })
public class ParkingTest {
	
	@Resource
	private MpParkingcChargeService mpParkingcChargeService;
	
	@Test
	public void testAdd(){
		MpParkingcChargeEntity mpce = new MpParkingcChargeEntity();
		mpce.setPlate("测D23751");
		mpce.setOrderNo("cs2017519004");
		mpce.setBillNo("cs2017519003001");
		mpce.setMemberId((long) 2);
		mpce.setTotalFee(0.00);
		mpce.setStatus(6);
		mpce.setCreateTime(DateUtils.getCurrentUnixTime());
		mpce.setStartTime(DateUtils.getCurrentUnixTime());
		mpce.setEndTime(DateUtils.getCurrentUnixTime());
		mpce.setUpdateTime(DateUtils.getCurrentUnixTime());
		for (int i = 0; i < 8; i++) {
			this.mpParkingcChargeService.save(mpce);
		}
		
		
		
	}
}
