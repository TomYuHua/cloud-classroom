package cloud.classroom.app.ui.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cloud.classroom.app.ui.Controller.IndexController;
import cloud.entity.classroom.DTO.Privilege.ChangeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.service.interfaces.PrivilegeService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.classroom.app.ui.service.interfaces.RoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.every.Privilege;
import cloud.entity.classroom.every.Role;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private static Logger log = LoggerFactory.getLogger(PrivilegeServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${cloud-user-service}")
    private String serviceName;

    @HystrixCommand(fallbackMethod = "fallbackprivilegeinsert")
    @Override
    public int insert(Privilege record) {
        int result = restTemplate.postForObject("http://" + serviceName + "/Privilege/insert", record, int.class);

        return result;
    }

    public int fallbackprivilegeinsert(Role record) {
        log.error("fallbackprivilegeinsert!");
        return 0;
    }

    @HystrixCommand(fallbackMethod = "fallbackprivilegeselectByRoleId")
    @Override
    public List<Privilege> selectByRoleId(Integer id) {
        return Arrays.asList(
                restTemplate.postForObject("http://" + serviceName + "/Privilege/selectByRoleId", id, Privilege[].class)
        );
    }

    public List<Privilege> fallbackprivilegeselectByRoleId(Integer id) {
        log.error("fallbackprivilegeselectByRoleId!");
        return null;
    }

    @HystrixCommand(fallbackMethod = "fallbackprivilegechange")
    @Override
    public boolean change(Integer id, List<String> list) {
        return restTemplate.postForObject(
                "http://" + serviceName + "/Privilege/change",
                new ChangeVo(id, list),
                Boolean.class
        );
    }

    public boolean fallbackprivilegechange(Integer id, List<String> list) {
        log.error("fallbackprivilegechange!");
        return false;
    }

    @HystrixCommand(fallbackMethod = "fallbackprivilegeselectAll")
    @Override
    public List<Privilege> selectAll() {
        List<Privilege> list = Arrays
                .asList(restTemplate.getForObject("http://" + serviceName + "/Privilege/selectAll", Privilege[].class));

        return list;
    }

    public List<Privilege> fallbackprivilegeselectAll() {
        log.error("fallbackprivilegeselectAll!");
        return null;
    }

    @HystrixCommand(fallbackMethod = "fallbackprivilegedelete")
    @Override
    public boolean delete(List<Integer> list) {
        String result = restTemplate.postForObject("http://" + serviceName + "/Privilege/delete", list,
                String.class);

        if (result.equals("success")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fallbackroledelete(List<Integer> list) {
        log.error("fallbackroledelete!");
        return false;
    }

}