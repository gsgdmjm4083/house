package com.verio.controller;

import com.verio.dto.Resp;
import com.verio.dto.RespResult;
import com.verio.entity.User;
import com.verio.service.*;
import com.verio.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class BaseController<T> {

    @Autowired
    protected UserService userService;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected FavorService favorService;
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected RoomService roomService;
    @Autowired
    protected RoomDetailService roomDetailService;
    @Autowired
    protected RoomOrderService roomOrderService;

    @Autowired
    protected BaseService<T> service;

    protected User loginUser;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * 通用保存接口
     */
    @ResponseBody
    @PostMapping("save")
    public String save(T obj) {
        if (Assert.isEmpty(obj)) {
            return Resp.resp(RespResult.fail("保存对象不能为空"));
        }
        obj = service.save(obj);
        return Resp.resp(RespResult.success("保存成功", obj));
    }

    /**
     * 通用删除接口
     */
    @ResponseBody
    @PostMapping("/delete")
    public String delete(Integer id) {
        if (Assert.isEmpty(id)) {
            return Resp.resp(RespResult.fail("删除ID不能为空"));
        }
        if (service.delete(id) == 0) {
            T t = service.get(id);
            if (Assert.isEmpty(t)) {
                return Resp.resp(RespResult.notFound("数据不存在"));
            }
            return Resp.resp(RespResult.fail("删除失败"));
        }
        return Resp.resp(RespResult.success("删除成功"));
    }

    /**
     * 在每个子类方法调用之前先调用
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
        loginUser = (User) session.getAttribute("loginUser");
    }
}
