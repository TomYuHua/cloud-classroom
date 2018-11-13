package cloud.service.classroom.controller;

import java.util.ArrayList;
import java.util.List;

import cloud.entity.classroom.DTO.Privilege.ChangeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.PageInfro;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.Privilege;
import cloud.service.classroom.interfaces.PrivilegeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/Privilege")

public class PrivilegeController {
    private Logger log = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;

    @ApiOperation(value = "插入权限", notes = "插入权限")
    @ApiImplicitParam(name = "record", value = "权限插入", required = true, dataType = "Privilege")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(@RequestBody Privilege record) {
        try {
            return privilegeService.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Role explore", e);
            return -1;
        }
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public boolean change(@RequestBody ChangeVo data) {
        return privilegeService.change(data.id, data.list);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody List<Integer> list) {
        if (privilegeService.delete(list)) {
            return "success";
        } else {
            return "fail";
        }
    }

    @ApiOperation(value = "获取权限", notes = "获取所有权限")
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<Privilege> selectAll() {
        try {
            return privilegeService.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PrivilegeController", e);
            return null;
        }
    }

    @RequestMapping(value = "/selectByRoleId", method = RequestMethod.POST)
    public List<Privilege> selectByRoleId(@RequestBody Integer id) {
        try {
            return privilegeService.selectByRoleId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PrivilegeController", e);
            return null;
        }
    }
}
