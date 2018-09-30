package com.side.movement.basketball.dao.impl;

import com.side.movement.basketball.dao.ISiteDao;
import com.side.movement.basketball.dao.base.impl.BaseDaoImpl;
import com.side.movement.basketball.entity.Site;
import com.side.movement.basketball.mapperOne.SiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SiteDaoImpl extends BaseDaoImpl<Site> implements ISiteDao {
    @Autowired
    private SiteMapper siteMapper;
    @Override
    public List<Site> list(String name) {
        return siteMapper.list(name);
    }
}
