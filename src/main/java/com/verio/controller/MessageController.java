package com.verio.controller;

import com.verio.entity.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 消息控制器
 */
@Controller
@RequestMapping(value = "message")
public class MessageController extends BaseController<Message> {

}
