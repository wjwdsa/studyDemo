package wds.mapper;

import org.apache.ibatis.annotations.Mapper;
import wds.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {
    //查询所有user方法
    public List<User> queryUserList();
}