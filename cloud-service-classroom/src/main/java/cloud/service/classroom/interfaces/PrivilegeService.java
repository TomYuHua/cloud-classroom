package cloud.service.classroom.interfaces;

import java.util.List;

import com.github.pagehelper.Page;

import cloud.entity.classroom.every.Privilege;
import cloud.entity.classroom.every.Role;

public interface PrivilegeService {
    int insert(Privilege record);

    List<Privilege> selectAll();

    boolean change(Integer id, List<String> list);

    List<Privilege> selectByRoleId(Integer id);

    boolean delete(List<Integer> list);
}