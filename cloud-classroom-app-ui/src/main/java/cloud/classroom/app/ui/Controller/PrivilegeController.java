package cloud.classroom.app.ui.Controller;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import cloud.classroom.app.ui.service.interfaces.PrivilegeService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.Privilege;
import cloud.entity.classroom.every.Role;
import cloud.classroom.app.ui.service.interfaces.ChapterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/privilege")
public class PrivilegeController {
    private static Logger log = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeservice;

    @Value("${dfs-filesystem}")
    private String filesystem;

    private static final String userInfo = "userInfoId";

    @RequestMapping("/privilege.html")
    public String RoleInfo(Integer id) {

        return "/AdminBackend/permissions";
    }

    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject insert(Privilege record) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("int", privilegeservice.insert(record));

        return jsonObj;
    }

    @RequestMapping(value = "/change", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject change(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject jo = JSONObject.parseObject(request.getReader().lines().collect(Collectors.joining()));

            List<String> list = new ArrayList<String>();
            JSONArray ja = jo.getJSONArray("list");
            for (int i = 0; i != ja.size(); ++i) {
                list.add(ja.getString(i));
            }
            Integer id = jo.getInteger("id");

            JSONObject jor = new JSONObject();
            jor.put("result", privilegeservice.change(id, list));
            return jor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/selectByRoleId", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject selectByRoleId(@RequestParam(required = false, name = "id") String id) {
        JSONObject jo = new JSONObject();
        jo.put("list", privilegeservice.selectByRoleId(Integer.parseInt(id)));
        return jo;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject delete(@RequestParam(required = false, name = "ids") String ids) {
        JSONObject jsonObj = new JSONObject();

        try {
            String[] item = ids.split(",");
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < item.length; i++) {
                list.add(new Integer(item[i]));
            }

            if (privilegeservice.delete(list)) {
                jsonObj.put("string", "success");
            } else {
                jsonObj.put("string", "false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

    @RequestMapping("/selectAll")
    @ResponseBody
    public JSONObject selectAll() {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("list", privilegeservice.selectAll());

        return jsonObj;
    }
}
