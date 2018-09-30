package com.side.movement.basketball.Service;

import com.side.movement.basketball.Service.base.IBaseService;
import com.side.movement.basketball.entity.Site;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ISiteService extends IBaseService<Site>{

    public void create(Site site);

    public List<Site> list(String name);

}
