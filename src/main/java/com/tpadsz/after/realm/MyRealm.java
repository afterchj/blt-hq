package com.tpadsz.after.realm;

import com.tpadsz.after.dao.UserExtendDao;
import com.tpadsz.after.entity.User;
import com.tpadsz.after.utils.Encodes;
import org.apache.log4j.Logger;
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

import java.util.HashSet;


public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserExtendDao userExtendDao;

    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 登录之后用于授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(new HashSet<>(userExtendDao.getRoles(username)));
        authorizationInfo.setStringPermissions(new HashSet<>(userExtendDao.getPermissions(username)));
        return authorizationInfo;
    }

    /**
     * 用于验证身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userExtendDao.selectByUsername(username);
        logger.info("user:" + user.toString());
        if (null != user) {
//            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            byte[] salt = Encodes.decodeHex(user.getSalt());
            logger.info("ByteSource:" + salt.toString());
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(salt), getName());
//            return info;
        }
        return null;
    }
}