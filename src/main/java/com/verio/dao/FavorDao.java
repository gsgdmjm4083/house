package com.verio.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verio.entity.Favor;
import org.springframework.stereotype.Repository;

/**
 * 收藏数据库访问
 */
@Repository
public interface FavorDao extends BaseMapper<Favor> {

}
