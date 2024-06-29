package com.verio.controller;

import com.verio.entity.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 评论控制器
 */
@Controller
@RequestMapping(value = "comment")
public class CommentController extends BaseController<Comment> {

}
