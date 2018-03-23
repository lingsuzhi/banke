package com.lsz.mapper;

import com.lsz.model.bo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User getUserById( int id);

    int updateUser( User user);

    int insertUser( User user);

    int deleteUserById( int id);
}
