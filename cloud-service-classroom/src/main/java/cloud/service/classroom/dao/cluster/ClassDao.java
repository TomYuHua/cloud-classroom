package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.classinfos.ClassInfo;

public interface ClassDao
{
	int insert(ClassInfo c);

	int getcount();
}
