package top.flytop.studentsign.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.flytop.studentsign.mapper.UserMapper;
import top.flytop.studentsign.pojo.User;

/**
 * @ClassName GlobalRealm
 * @Description TODO 自定义realm
 * @Auther Wonder-yz
 * @Date 2019/2/9 21:08
 * @Version 1.0
 */
@Component
class GlobalRealm extends AuthorizingRealm {
    private UserMapper userMapper;
    private User user;

    @Autowired
    private void userMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * @param token
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Description TODO 认证
     * @Date 2019/2/9 21:22
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("------认证方法执行了......");
        String username = String.valueOf(token.getPrincipal());
        // 2,去数据库中查询用户信息
        user = userMapper.getUser(username);
        if (user == null) {
            // 3，用户不存在，返回null框架抛出异常，到controller进行统一处理
            return null;
        }
        // 4，对页面提交的密码和数据库中的密码进行校验
        String password = user.getPwd();
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, password, credentialsSalt, getName());
        return authenticationInfo;
    }

    /**
     * @param principals
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Description TODO 授权方法
     * @Date 2019/2/9 21:22
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("------授权方法执行了........");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (user == null) {
            return null;
        }
        // 用户授权
        switch (user.getTypeId()) {
            case 1:
                authorizationInfo.addRole("student");
                break;
            case 2:
                break;
            case 3:
                authorizationInfo.addRole("admin");
                break;
        }
        /*User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        User user2 = (User) principals.getPrimaryPrincipal();
        System.out.println(user1 == user2);*/
        return authorizationInfo;
    }
}
