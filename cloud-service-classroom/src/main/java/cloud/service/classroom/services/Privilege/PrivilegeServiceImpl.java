package cloud.service.classroom.services.Privilege;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.every.Privilege;
import cloud.entity.classroom.every.Role;
import cloud.service.classroom.annotation.TargetDataSource;
import cloud.service.classroom.dao.cluster.PrivilegeDao;
import cloud.service.classroom.dao.cluster.RoleDao;
import cloud.service.classroom.interfaces.PrivilegeService;
import cloud.service.classroom.interfaces.RoleService;
import cloud.service.classroom.services.Chapter.ChapterServiceImpl;
import cloud.service.classroom.services.Role.RoleServiceImpl;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private Logger log = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

    
    @Autowired
    PrivilegeDao privilegeDao;

    @Override
    public int insert(Privilege record) {
        try {
            return privilegeDao.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Role explore", e);
            return -1;
        }
    }


    @Override
    public boolean change(Integer id, List<String> list) {
        log.info("change:" + id);
        String sid = id.toString();
        privilegeDao.changeDelete(sid);
        if (list.size() > 0) {
            int r = privilegeDao.changeInsert(sid, list);
            if (r != list.size()) {
                // rollback the Transaction
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Privilege> selectByRoleId(Integer id) {
        try {
            return privilegeDao.selectByRoleId(id.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PrivilegeServiceImpl: 数据服务器异常 ", e);
            return null;
        }
    }


    @Override
    public List<Privilege> selectAll() {
        try {
            return privilegeDao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PrivilegeServiceImpl: 数据服务器异常 ", e);
            return null;
        }
    }

    @Override
    public boolean delete(List<Integer> list) {
        try {
            List<String> l = new ArrayList<>();
            for (Integer i : list) {
                l.add(i.toString());
            }
            privilegeDao.delete(l);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

