package com.bin.system.realm;

import com.bin.system.common.ActiveUser;
import com.bin.system.common.Constant;
import com.bin.system.domain.User;
import com.bin.system.service.MenuService;
import com.bin.system.service.RoleService;
import com.bin.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        User user = this.userService.queryUserByLoginName(userName);
        if (null != user) {
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);

            //根据用户ID查询角色名称的集合
            List<String> roles = this.roleService.queryRoleNamesByUserId(user.getId());
            //根据用户ID查询权限编码的集合
            List<String> permissions = this.menuService.queryPermissionCodeByUserId(user.getId());

            activeUser.setRoles(roles);
            activeUser.setPermissions(permissions);

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), ByteSource.Util.bytes(user.getSalt()), getName());
            return info;
        }
        return null;
    }


    /**
     * 授权
     *
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @params [principals]
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
        List<String> permissions = activeUser.getPermissions();
        List<String> roles = activeUser.getRoles();
        User user = activeUser.getUser();
        if (user.getType().equals(Constant.USER_TYPE_SUPER)) {
            info.addStringPermission("*:*");
        } else {
            if (null != roles && roles.size() > 0) {
                info.addRoles(roles);
            }
            if (null != permissions && permissions.size() > 0) {
                info.addStringPermissions(permissions);
            }
        }
        return info;
    }
}
