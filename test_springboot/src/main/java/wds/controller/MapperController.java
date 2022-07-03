package wds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wds.domain.User;
import wds.mapper.UserMapper;

import java.util.List;

@Controller
public class MapperController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/query")
    @ResponseBody
    public List<User> queryUserList() {
        List<User> users = userMapper.queryUserList();
        return users;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public void insertUser() {
        userMapper.insertUser();
    }
}
