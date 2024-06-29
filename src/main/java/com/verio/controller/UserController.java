package com.verio.controller;

import com.verio.dto.Resp;
import com.verio.dto.RespResult;
import com.verio.entity.User;
import com.verio.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Slf4j
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController<User> {

    @ResponseBody
    @PostMapping("/register")
    public String register(User user) {
        List<User> queryName = userService.query(User.builder().name(user.getName()).build());
        if (Assert.notEmpty(queryName)) {
            return Resp.resp(RespResult.builder().code("EXIST_NAME").message("用户名已占用").build());
        }
        List<User> queryEmail = userService.query(User.builder().email(user.getEmail()).build());
        if (Assert.notEmpty(queryEmail)) {
            return Resp.resp(RespResult.builder().code("EXIST_EMAIL").message("邮箱已占用").build());
        }
        List<User> queryPhone = userService.query(User.builder().phone(user.getPhone()).build());
        if (Assert.notEmpty(queryPhone)) {
            return Resp.resp(RespResult.builder().code("EXIST_PHONE").message("手机号已占用").build());
        }
        user = userService.save(user);
        session.setAttribute("loginUser", user);
        log.info("注册成功 -> {}", user);
        return Resp.resp(RespResult.success("注册成功", user));
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(User user) {
        List<User> queryUser = userService.query(user);
        if (Assert.isEmpty(queryUser)) {
            List<User> queryEmail = userService.query(User.builder().email(user.getEmail()).build());
            if (Assert.isEmpty(queryEmail)) {
                return Resp.resp(RespResult.notFound("邮箱不存在"));
            }
            return Resp.resp(RespResult.fail("登录密码错误"));
        }
        user = queryUser.get(0);
        session.setAttribute("loginUser", user);
        log.info("登录成功 -> {}", user);
        return Resp.resp(RespResult.success("登录成功", user));
    }

    /**
     * 修改资料
     */
    @PostMapping("/updateProfile")
    @ResponseBody
    public String updateProfile(User user) {
        List<User> queryName = userService.query(User.builder().name(user.getName()).build());
        if (Assert.notEmpty(queryName) && queryName.get(0).getId() != loginUser.getId().intValue()) {
            return Resp.resp(RespResult.builder().code("EXIST_NAME").message("用户名已占用").build());
        }
        List<User> queryEmail = userService.query(User.builder().email(user.getEmail()).build());
        if (Assert.notEmpty(queryEmail) && queryEmail.get(0).getId() != loginUser.getId().intValue()) {
            return Resp.resp(RespResult.builder().code("EXIST_EMAIL").message("邮箱已占用").build());
        }
        List<User> queryPhone = userService.query(User.builder().phone(user.getPhone()).build());
        if (Assert.notEmpty(queryPhone) && queryPhone.get(0).getId() != loginUser.getId().intValue()) {
            return Resp.resp(RespResult.builder().code("EXIST_PHONE").message("手机号已占用").build());
        }
        user.setId(loginUser.getId());
        loginUser = userService.save(user);
        if (Assert.isEmpty(loginUser)) {
            return Resp.resp(RespResult.fail("修改失败"));
        }
        session.setAttribute("loginUser", loginUser);
        return Resp.resp(RespResult.success("修改成功"));
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestParam("old") String old, @RequestParam("password") String password) {
        if (loginUser.getPassword().equals(old)) {
            loginUser.setPassword(password);
            loginUser = userService.save(loginUser);
            if (Assert.isEmpty(loginUser)) {
                return Resp.resp(RespResult.fail("修改失败"));
            } else {
                session.setAttribute("loginUser", loginUser);
                return Resp.resp(RespResult.success("修改成功"));
            }
        } else {
            return Resp.resp(RespResult.fail("原密码错误"));
        }
    }
}
