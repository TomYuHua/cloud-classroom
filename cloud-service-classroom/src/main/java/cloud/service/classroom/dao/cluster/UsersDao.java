package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.user.Users;
import java.util.List;

public interface UsersDao
{
	int insert(Users record);

	List<Users> selectAll();

	int getCount();
}