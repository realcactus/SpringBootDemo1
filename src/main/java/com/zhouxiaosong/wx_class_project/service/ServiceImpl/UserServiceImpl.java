package com.zhouxiaosong.wx_class_project.service.ServiceImpl;

import com.zhouxiaosong.wx_class_project.dao.UserDAO;
import com.zhouxiaosong.wx_class_project.domain.User;
import com.zhouxiaosong.wx_class_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDao;


    @Override
    public User getUserByNickName(String nickName) {
        List users = userDao.findAllByNickName(nickName);
        if(users.size() == 1){
            return (User)users.get(0);
        }
        //no result
        User user = new User();
        user.setId(-1);
        return user;
    }

    @Override
    public User addUser(User user) {
        String nickName = user.getNickName();
        User existed = getUserByNickName(nickName);
        if(existed.getId()!=-1){
            return existed;
        }

        return userDao.save(user);
    }

    @Override
    public List<User> getUserList() {
        return userDao.findAll();
    }

    @Override
    public User updateUser(User user) {
        String nickName = user.getNickName();
        User existed = getUserByNickName(nickName);
        //存在，即修改
        if(existed.getId()!=-1){
            existed.setGender(user.getGender());
            existed.setNickName(user.getNickName());
            existed.setAvatarUrl(user.getAvatarUrl());
            return userDao.save(existed);
        }

        //不存在，即新建
        return userDao.save(user);
    }

    @Override
    public List<User> deleteUserByNickName(String nickName) {
        User user = getUserByNickName(nickName);
        userDao.deleteByNickName(nickName);
        return userDao.findAll();
    }
}
