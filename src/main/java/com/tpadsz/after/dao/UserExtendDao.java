package com.tpadsz.after.dao;


import com.tpadsz.after.entity.User;

import java.util.List;
import java.util.Map;

public interface UserExtendDao {

    User selectByUsername(Map map);

    User selectByUsername(String uname);

    List<String> getRoles(String uname);

    List<String> getPermissions(String uname);

}
