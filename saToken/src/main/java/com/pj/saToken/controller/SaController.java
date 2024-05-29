package com.pj.saToken.controller;

import cn.dev33.satoken.annotation.*;
import cn.dev33.satoken.basic.SaBasicUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sa/")
public class SaController {

    @SaIgnore
    @PostMapping("doLogin")
    public SaResult doLogin(@RequestParam String name, @RequestParam String pwd) {
        // 第一步：比对前端提交的账号名称、密码
        if ("admin".equals(name)) {
            // 第二步：根据账号id，进行登录
            StpUtil.login(pwd);
            return SaResult.ok("登录成功");
        }
        return SaResult.error("登录失败");
    }

    // 提供密码进行二级认证
    @GetMapping("openSafe")
    public SaResult openSafe(String password) {
        // 比对密码（此处只是举例，真实项目时可拿其它参数进行校验）
        if("123456".equals(password)) {

            // 比对成功，为当前会话打开二级认证，有效期为120秒
            StpUtil.openSafe(120);
            return SaResult.ok("二级认证成功");
        }

        // 如果密码校验失败，则二级认证也会失败
        return SaResult.error("二级认证失败");
    }

    @GetMapping("untieDisable")
    public SaResult untieDisable() {
        StpUtil.untieDisable("10002");
        return SaResult.ok("解封成功");
    }

    @GetMapping("doDisable")
    public SaResult doDisable() {
        StpUtil.kickout("10002");
        StpUtil.disable("10002", -1);
        return SaResult.ok("封禁成功");
    }

    // 登录校验：只有登录之后才能进入该方法
    @SaCheckLogin
    @GetMapping("info")
    public String info() {
        return "查询用户信息";
    }

    // 角色校验：必须具有指定角色才能进入该方法
    @SaCheckRole("super-admin")
    @GetMapping("super-admin")
    public String superAdd() {
        return "用户增加";
    }

    // 权限校验：必须具有指定权限才能进入该方法
    @SaCheckPermission("permission-add")
    @GetMapping("permission-add")
    public String permissionAdd() {
        return "用户增加";
    }

    // 二级认证校验：必须二级认证之后才能进入该方法
    @SaCheckSafe()
    @GetMapping("safe-add")
    public String safeAdd() {
        return "用户增加";
    }

    // Http Basic 校验：只有通过 Basic 认证后才能进入该方法
    @SaCheckBasic(account = "admin:654321")
    @GetMapping("http-basic-add")
    public String httpBasicAdd() {
        return "用户增加";
    }

    // 校验当前账号是否被封禁 comment 服务，如果已被封禁会抛出异常，无法进入方法
    @SaCheckDisable()
    @GetMapping("disable")
    public String disable() {
        return "查询用户信息";
    }
}
