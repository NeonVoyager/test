package com.oneblog.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oneblog.entity.User;
import com.oneblog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shiro Realm - 用户认证和授权
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        
        // 添加角色
        if (user.getRole() != null) {
            authorizationInfo.addRole(user.getRole());
        }
        
        // 可以根据角色添加权限
        if ("admin".equals(user.getRole())) {
            authorizationInfo.addStringPermission("admin:*");
        } else if ("editor".equals(user.getRole())) {
            authorizationInfo.addStringPermission("article:*");
            authorizationInfo.addStringPermission("comment:*");
        }
        
        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        
        // 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username)
               .eq(User::getDeleted, 0);
        User user = userService.getOne(wrapper);
        
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账户已被禁用");
        }
        
        // 返回认证信息（使用用户名作为盐）
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), getName());
    }
}

