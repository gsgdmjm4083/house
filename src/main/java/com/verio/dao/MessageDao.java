package com.verio.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verio.entity.Message;
import org.springframework.stereotype.Repository;

/**
 * 消息数据库访问
 */
@Repository
public interface MessageDao extends BaseMapper<Message> {

}
