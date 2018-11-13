package cloud.classroom.app.ui.service.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.Privilege;

public interface PrivilegeService {
    int insert(Privilege record);

    List<Privilege> selectAll();

    List<Privilege> selectByRoleId(Integer id);

    boolean change(Integer id, List<String> list);

    boolean delete(List<Integer> list);
}