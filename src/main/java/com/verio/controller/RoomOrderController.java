package com.verio.controller;

import com.verio.dto.Resp;
import com.verio.dto.RespResult;
import com.verio.entity.Room;
import com.verio.entity.RoomDetail;
import com.verio.entity.RoomOrder;
import com.verio.utils.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 订单控制器
 */
@Controller
@RequestMapping(value = "roomOrder")
public class RoomOrderController extends BaseController<RoomOrder> {

    /**
     * 支付
     */
    @PostMapping("/pay")
    @ResponseBody
    public String pay(Integer id) {
        RoomOrder order = roomOrderService.get(id);
        List<RoomDetail> roomDetails = roomDetailService.query(RoomDetail.builder().roomId(order.getRoomId()).build());
        if (Assert.notEmpty(roomDetails)) {
            RoomDetail detail = roomDetails.get(0);
            if ("已支付".equals(detail.getStatus())) {
                order.setPayOrder(System.currentTimeMillis() + "UNPAYED");
                order.setStatus("已支付");
                order = roomOrderService.save(order);
                return Resp.resp(RespResult.fail("支付失败，房源被他人抢先租下～"));
            }
        } else {
            return Resp.resp(RespResult.fail("支付失败，房源信息有误～"));
        }
        order.setPayOrder(System.currentTimeMillis() + "PAYED");
        order.setStatus("已支付");
        order = roomOrderService.save(order);
        if (Assert.notEmpty(order)) {
            // 更新房源的状态
            RoomDetail detail = roomDetails.get(0);
            detail.setStatus("已租");
            roomDetailService.save(detail);
        }
        return Resp.resp(RespResult.success("支付成功"));
    }

    /**
     * 创建订单
     */
    @PostMapping("/saveOrder")
    @ResponseBody
    public String saveOrder(Integer roomId, Integer month, Integer person) {
        Room room = roomService.get(roomId);
        String num = "ORDER" + System.currentTimeMillis() + "NUM";
        Double allPrice = room.getMonthPrice() * month;
        Date start = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH, month);
        Date end = calendar.getTime();
        RoomOrder order = RoomOrder.builder()
                .userId(loginUser.getId())
                .ownerId(room.getOwnerId())
                .roomId(roomId)
                .orderNum(num)
                .title(room.getTitle() + " x " + month)
                .subTitle(room.getSubTitle())
                .content("租期为" + month + "个月，共" + person + "入住。")
                .payMoney(allPrice)
                .status("未支付")
                .fromTime(start).toTime(end)
                .build();
        RoomOrder save = roomOrderService.save(order);
        if (Assert.isEmpty(save)) {
            return Resp.resp(RespResult.fail("创建失败"));
        }
        return Resp.resp(RespResult.success("创建成功"));
    }

}
