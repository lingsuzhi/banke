package com.lsz.mapper;

import com.lsz.model.bo.User;


public interface UserDao {

    User getUserById( String id);

    int updateUser( User user);

    int insertUser( User user);

    int deleteUserById( int id);
}
