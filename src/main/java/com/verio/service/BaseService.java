package com.verio.service;

import com.verio.dao.*;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected CommentDao commentDao;

    @Autowired
    protected FavorDao favorDao;

    @Autowired
    protected MessageDao messageDao;

    @Autowired
    protected RoomDao roomDao;

    @Autowired
    protected RoomDetailDao roomDetailDao;

    @Autowired
    protected RoomOrderDao roomOrderDao;

    @Autowired
    protected UserDao userDao;
}
