package com.vastio.mapper;

import com.vastio.bean.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> findAll();

    User findByName(@Param("username") String username);

}
