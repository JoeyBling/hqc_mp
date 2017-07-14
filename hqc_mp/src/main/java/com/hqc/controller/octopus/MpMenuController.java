package com.hqc.controller.octopus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.util.R;

@Controller
@RequestMapping("/octopus/mpMenu")
public class MpMenuController {
	@Resource
	protected WxMpService wxMpService;

	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions("mp:menu:list")
	public R menuSelect() throws WxErrorException {
		// 自定义菜单查询接口
		WxMpMenu menu = new WxMpMenu();
		menu = wxMpService.getMenuService().menuGet();
		return R.ok().put("menu", menu);
	}

	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("mp:menu:delete")
	public R menuDelete() throws WxErrorException {
		// 自定义菜单删除接口
		wxMpService.getMenuService().menuDelete();
		return R.ok();
	}

	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions("mp:menu:save")
	public R saveMenu(String mpMenuName, String type, String url,
			String menu0Name, String menu0Type, String menu0Url,
			String menu1Name, String menu1Type, String menu1Url,
			String menu2Name, String menu2Type, String menu2Url)
			throws WxErrorException {
		if (mpMenuName == null) {
			mpMenuName = "";
		}
		String[] MenuName = mpMenuName.split(",");// 将数据根据","进行分割
		if (type == null) {
			type = "";
		}
		if (url == null) {
			url = "";
		}
		String[] MenuType = type.split(",");
		String[] MenuUrl = url.split(",");
		if (menu0Name == null) {// 防止值为null报null指针错误
			menu0Name = "";
			menu0Type = "";
			menu0Url = "";
		}
		if (menu1Name == null) {
			menu1Name = "";
			menu1Type = "";
			menu1Url = "";
		}
		if (menu2Name == null) {
			menu2Name = "";
			menu2Type = "";
			menu2Url = "";
		}
		String[] Menu0Name = menu0Name.split(",");
		String[] Menu0Type = menu0Type.split(",");
		String[] Menu0Url = menu0Url.split(",");
		String[] Menu1Name = menu1Name.split(",");
		String[] Menu1Type = menu1Type.split(",");
		String[] Menu1Url = menu1Url.split(",");
		String[] Menu2Name = menu2Name.split(",");
		String[] Menu2Type = menu2Type.split(",");
		String[] Menu2Url = menu2Url.split(",");
		WxMenu wxMenu = new WxMenu();
		List<WxMenuButton> listButton = new ArrayList<WxMenuButton>();
		int menu1 = 0;// 设置值，用来判断该子菜单组的值是否被使用
		int menu2 = 0;
		int menu3 = 0;
		for (int i = 0; i < MenuName.length; i++) {// 根据菜单名称数组的长度进行循环
			WxMenuButton button = new WxMenuButton();
			if ("".equals(MenuName[i].trim())
					|| "undefined".equals(MenuName[i].trim())) {
				return R.error("菜单名称不能为空");
			}
			button.setName(MenuName[i]);
			if (MenuType.length != 0) {// 如果一级菜单没有子菜单，设置其值
				if (!"".equals(MenuType[i].trim())
						&& !"undefined".equals(MenuType[i].trim())) {// 如果type值不为空，不为undefined，不为“”，则为一级菜单，不添加子菜单
					if ("".equals(MenuUrl[i].trim())
							|| "undefined".equals(MenuUrl[i].trim())) {
						return R.error("url地址不能为空");
					}
					if (MenuType[i].equals("0")) {
						button.setType(WxConsts.BUTTON_CLICK);
					}
					if (MenuType[i].equals("1")) {// 如果type为1，则添加url地址
						button.setType(WxConsts.BUTTON_VIEW);
						button.setUrl(MenuUrl[i]);
					}
					if (MenuType[i].equals("2")) {
						button.setType(WxConsts.BUTTON_MINIPROGRAM);
					}
					listButton.add(button);
					continue;// 跳出本次循环
				}
			}
			if (MenuType.length == 0 || "".equals(MenuType[i].trim())
					|| "undefined".equals(MenuType[i])) {// 如果一级菜单type值为空，则进行子菜单的塞值

				if (Menu0Name[0].trim() != "" && menu1 == 0) {// 如果menu0的值不为空，且menu1值为0则进入方法
					List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
					for (int j = 0; j < Menu0Name.length; j++) {// 根据子菜单的菜单名称数组长度进行循环
						WxMenuButton childbutton = new WxMenuButton();
						if ("".equals(Menu0Name[j].trim())
								|| "undefined".equals(Menu0Name[j].trim())) {
							return R.error("子菜单名称不能为空");
						}
						if ("".equals(Menu0Url[j].trim())
								|| "undefined".equals(Menu0Url[j].trim())) {
							return R.error("子菜单地址不能为空");
						}
						childbutton.setName(Menu0Name[j]);
						if (Menu0Type[j].equals("0")) {
							childbutton.setType(WxConsts.BUTTON_CLICK);
						}
						if (Menu0Type[j].equals("1")) {
							childbutton.setType(WxConsts.BUTTON_VIEW);
							childbutton.setUrl(Menu0Url[j]);
						}
						if (Menu0Type[j].equals("2")) {
							childbutton.setType(WxConsts.BUTTON_MINIPROGRAM);
						}
						subButtons.add(childbutton);
					}
					button.setSubButtons(subButtons);
					menu1++;// 因为已经使用这个数组，则进行+1，防止再次进入
					listButton.add(button);
					continue;
				} else if (Menu1Name[0].trim() != "" && menu2 == 0) {
					List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
					for (int j = 0; j < Menu1Name.length; j++) {
						WxMenuButton childbutton = new WxMenuButton();
						if ("".equals(Menu1Name[j].trim())
								|| "undefined".equals(Menu1Name[j].trim())) {
							return R.error("子菜单名称不能为空");
						}
						if ("".equals(Menu1Url[j].trim())
								|| "undefined".equals(Menu1Url[j].trim())) {
							return R.error("子菜单地址不能为空");
						}
						childbutton.setName(Menu1Name[j]);
						if (Menu1Type[j].equals("0")) {
							childbutton.setType(WxConsts.BUTTON_CLICK);
						}
						if (Menu1Type[j].equals("1")) {
							childbutton.setType(WxConsts.BUTTON_VIEW);
							childbutton.setUrl(Menu1Url[j]);
						}
						if (Menu1Type[j].equals("2")) {
							childbutton.setType(WxConsts.BUTTON_MINIPROGRAM);
						}
						subButtons.add(childbutton);
					}
					button.setSubButtons(subButtons);
					menu2++;
					listButton.add(button);
					continue;
				} else if (Menu2Name[0].trim() != "" && menu3 == 0) {
					List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
					for (int j = 0; j < Menu2Name.length; j++) {
						WxMenuButton childbutton = new WxMenuButton();
						if ("".equals(Menu2Name[j].trim())
								|| "undefined".equals(Menu2Name[j].trim())) {
							return R.error("子菜单名称不能为空");
						}
						if ("".equals(Menu2Url[j].trim())
								|| "undefined".equals(Menu2Url[j].trim())) {
							return R.error("子菜单地址不能为空");
						}
						childbutton.setName(Menu2Name[j]);
						if (Menu2Type[j].equals("0")) {
							childbutton.setType(WxConsts.BUTTON_CLICK);
						}
						if (Menu2Type[j].equals("1")) {
							childbutton.setType(WxConsts.BUTTON_VIEW);
							childbutton.setUrl(Menu2Url[j]);
						}
						if (Menu2Type[j].equals("2")) {
							childbutton.setType(WxConsts.BUTTON_MINIPROGRAM);
						}
						subButtons.add(childbutton);
					}
					button.setSubButtons(subButtons);
					menu3++;
					listButton.add(button);
					continue;
				} else {
					return R.error("子菜单名称不能为空");
				}
			}
		}
		wxMenu.setButtons(listButton);
		wxMpService.getMenuService().menuCreate(wxMenu);
		return R.ok();
	}
}
