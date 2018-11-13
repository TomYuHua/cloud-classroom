package cloud.classroom.app.ui.Controller;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import cloud.classroom.app.ui.service.interfaces.UserRoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.UserRole;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/UserRole")
public class UserRoleController {

	private static Logger log = LoggerFactory.getLogger(UserRoleController.class);

	@Autowired
	private UserRoleService userRoleService;

	@Value("${dfs-filesystem}")
	private String filesystem;

	private static final String userInfo = "userInfoId";
	
	@RequestMapping("/userrole.html")
	public String RoleInfo(Integer id) {

		return "/AdminBackend/role";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public JSONObject insert(@RequestBody(required=false) UserRole record) {
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("int", userRoleService.insert(record));
		
		return jsonObj;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(@RequestParam(required=false,name="ids")String ids) {
		JSONObject jsonObj = new JSONObject();
		
		try{
			String[] item = ids.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < item.length; i++)
			{
				list.add(new Integer(item[i]));
			}
			
			if (userRoleService.delete(list)) {
				jsonObj.put("string", "success");
			} else {
			jsonObj.put("string", "false");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return jsonObj;
	}
	
	@RequestMapping("/selectAll")
	@ResponseBody
	public JSONObject selectAll() {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("list", userRoleService.selectAll());

		return jsonObj;
	}
}
