package com.tpadsz.after.service.impl;

import com.tpadsz.after.dao.SysUserDao;
import com.tpadsz.after.entity.SysUser;
import com.tpadsz.after.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by after on 2018/8/5.
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    public List<SysUser> getAll() {
        return sysUserDao.getAll();
    }
}
