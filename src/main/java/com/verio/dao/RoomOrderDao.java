package com.verio.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verio.entity.RoomOrder;
import org.springframework.stereotype.Repository;

/**
 * 订单数据库访问
 */
@Repository
public interface RoomOrderDao extends BaseMapper<RoomOrder> {

}
