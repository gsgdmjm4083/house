package com.verio.controller;

import com.verio.entity.Room;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "room")
public class RoomController extends BaseController<Room> {

}
