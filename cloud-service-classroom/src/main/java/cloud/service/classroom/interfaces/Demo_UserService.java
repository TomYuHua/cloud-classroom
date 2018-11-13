package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.user.Users;

public interface Demo_UserService
{

	int insert(Users record);

	List<Users> selectAll();

	Integer getcount();

	void insert();
}
