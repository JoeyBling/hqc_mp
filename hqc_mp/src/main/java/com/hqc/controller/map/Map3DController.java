package com.hqc.controller.map;


import com.hqc.entity.MpProjectCategoryEntity;
import com.hqc.entity.MpProjectEntity;
import com.hqc.service.ProjectCategoryService;
import com.hqc.service.ProjectMpService;
import com.hqc.util.R;
import me.chanjar.weixin.mp.api.WxMpService;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("wx/map")
public class Map3DController {
    @Resource
    private ProjectCategoryService projectCategoryService;
    @Resource
    private ProjectMpService projectMpService;
    @Resource
    protected WxMpService wxMpService;

    /**
     * 默认访问页面
     *
     * @return String
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "/wx/map/index.ftl";
    }

    @RequestMapping("/category")
    @ResponseBody
    public R category(Integer type, String phone, HttpServletRequest request,
                      HttpServletResponse response) {
        // 所有列表
        List<MpProjectCategoryEntity> list = projectCategoryService.queryList(null);
        return R.ok().put("list", list);
    }


    @RequestMapping("/queryProject")
    @ResponseBody
    public R queryProject(Long id, HttpServletRequest request) throws IOException, DocumentException {
        List<MpProjectEntity> list = projectMpService.queryListBywhere(id);
        return R.ok().put("list", list);
    }
}