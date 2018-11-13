package cloud.entity.classroom.DTO.Privilege;

import java.io.Serializable;
import java.util.List;

public class ChangeVo implements Serializable {
    public Integer id = 0;
    public List<String> list = null;

    public ChangeVo(Integer id, List<String> list) {
        this.id = id;
        this.list = list;
    }

    public ChangeVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
