package com.verio.controller;

import com.verio.entity.Favor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 收藏控制器
 */
@Controller
@RequestMapping(value = "favor")
public class FavorController extends BaseController<Favor> {

}
