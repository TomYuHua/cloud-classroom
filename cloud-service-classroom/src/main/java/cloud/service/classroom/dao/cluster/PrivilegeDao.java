package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.every.Privilege;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PrivilegeDao {
    Integer insert(Privilege record);

    int delete(List<String> list);

    int changeDelete(@Param("id") String id);

    int changeInsert(@Param("id") String id, @Param("list") List<String> list);

    List<Privilege> selectByRoleId(@Param("id") String id);

    List<Privilege> selectAll();
}
