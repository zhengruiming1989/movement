package com.side.movement.basketball.dao;

import com.side.movement.basketball.dao.base.IBaseDao;
import com.side.movement.basketball.entity.Site;

import java.util.List;

public interface ISiteDao extends IBaseDao<Site> {

    public List<Site> list(String name);
}
