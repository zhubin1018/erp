package com.bin.system.common;

import com.bin.system.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable {

    private User user;

    /*
     * 角色
     * */

    private List<String> roles;

    /*
     * 权限
     * */

    private List<String> permissions;
}
