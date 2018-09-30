package com.side.movement.basketball.mapperOne;


import com.side.movement.basketball.entity.Site;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface SiteMapper extends Mapper<Site> {

    List<Site> list(@Param("name") String name);

}