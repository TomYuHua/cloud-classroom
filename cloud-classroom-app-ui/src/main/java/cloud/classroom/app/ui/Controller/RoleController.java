package cloud.classroom.app.ui.Controller;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import cloud.classroom.app.ui.service.interfaces.RoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;
import cloud.entity.classroom.every.Role;
import cloud.classroom.app.ui.service.interfaces.ChapterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/Role")
public class RoleController
{

	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@Value("${dfs-filesystem}")
	private String filesystem;

	private static final String userInfo = "userInfoId";

	@RequestMapping("/role")
	public String RoleInfo(Integer id)
	{

		return "/AdminBackend/role";
	}
	
	@RequestMapping("/delete")

	@ResponseBody
	public JSONObject delete(@RequestParam(required = false, name = "ids") String ids)
	{

		JSONObject jsonObj = new JSONObject();

		try
		{
			String[] item = ids.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < item.length; i++)
			{
				list.add(new Integer(item[i]));
			}

			if (roleService.delete(list))
			{
				jsonObj.put("string", "success");
			} else
			{
				jsonObj.put("string", "false");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return jsonObj;
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject change(@RequestBody(required=false)Role role) {
		JSONObject jsonObj = new JSONObject();
		if (roleService.change(role)) {
			jsonObj.put("string", "ok");
		} else {
			jsonObj.put("string", "false");
		}

	return jsonObj;
	}

	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject insert(@RequestBody(required = false)Role record)
	{

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("int", roleService.insert(record));

		return jsonObj;
	}

	@RequestMapping("/selectAll")
	@ResponseBody
	public JSONObject selectAll()
	{

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("list", roleService.selectAll());

		return jsonObj;
	}
}
