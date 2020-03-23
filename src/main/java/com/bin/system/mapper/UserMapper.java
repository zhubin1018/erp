package com.bin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.system.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    Integer queryDeptMaxOrderNum();

    void saveUserRole(@Param("uid") Integer uid, @Param("rid") Integer rid);

}