package cloud.classroom.app.ui.AdminBackend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cloud.classroom.app.ui.annotation.Permission;

@Permission
@Controller
@RequestMapping(value = "permissions")
public class PermissionsManageController
{
	@RequestMapping("/")
	public String index()
	{
		return "/AdminBackend/permissions";
	}
}
