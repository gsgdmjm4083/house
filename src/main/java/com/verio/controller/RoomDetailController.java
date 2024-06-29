package com.verio.controller;

import com.verio.entity.RoomDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 房屋明细控制器
 */
@Controller
@RequestMapping(value = "roomDetail")
public class RoomDetailController extends BaseController<RoomDetail> {

}
