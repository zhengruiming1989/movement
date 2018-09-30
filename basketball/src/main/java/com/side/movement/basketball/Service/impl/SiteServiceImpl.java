package com.side.movement.basketball.Service.impl;

import com.side.movement.basketball.Service.ISiteService;
import com.side.movement.basketball.Service.base.impl.BaseServiceImpl;
import com.side.movement.basketball.dao.ISiteDao;
import com.side.movement.basketball.entity.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl extends BaseServiceImpl<Site> implements ISiteService {
    @Autowired
    private ISiteDao siteDao;

    @Override
    public void create(Site site) {
        siteDao.insertSelective(site);
    }

    @Override
    public List<Site> list(String name) {
        return siteDao.list(name);
    }
}
