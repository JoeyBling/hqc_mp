package com.hqc.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hqc.dao.SysAdminDao;
import com.hqc.entity.MpAutoReplyEntity;
import com.hqc.entity.SysAdminEntity;
import com.hqc.service.SysAdminService;
import com.hqc.util.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-hqc.xml" })
public class SysAdminServiceTest {

	@Resource
	private SysAdminService sysAdminService;

	@Resource
	private SysAdminDao sysAdminDao;

	@Test
	public void testQueryList() {
		try {
			List<SysAdminEntity> userList = sysAdminService.queryList(null);
			for (SysAdminEntity sysUserEntity : userList) {
				System.out.println(sysUserEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectCount() {
		System.out.println(sysAdminDao.selectCount(null));
	}

	@Resource
	MpAutoReplyService MpAutoReplyService;

	@Test
	public void saveMpAutoReply() throws Exception{
		MpAutoReplyEntity mpAutoReply = new MpAutoReplyEntity();
		mpAutoReply.setCreateTime(DateUtils.getCurrentUnixTime());
		mpAutoReply.setKeywords("sadad");
		System.out.println(MpAutoReplyService.save(mpAutoReply));
	}

	@Test
	public void add() {
		SysAdminEntity user = new SysAdminEntity();
		user.setUsername("asdasdssssaaaaa");
		List<Long> roleIdList = new ArrayList<Long>();
		user.setPassword("asd");
		roleIdList.add(1L);
		user.setRoleIdList(roleIdList);
		try {
			sysAdminService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
