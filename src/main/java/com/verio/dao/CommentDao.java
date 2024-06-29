package com.verio.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verio.entity.Comment;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentDao extends BaseMapper<Comment> {

}
