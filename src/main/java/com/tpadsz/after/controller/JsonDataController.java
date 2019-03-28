package com.tpadsz.after.controller;

import com.tpadsz.after.entity.Role;
import com.tpadsz.after.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hongjian.chen on 2019/3/28.
 */


@RestController
@RequestMapping("/rest")
public class JsonDataController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/roleList")
    public List<Role> roleList() {
        return roleService.getAll();
    }
}
