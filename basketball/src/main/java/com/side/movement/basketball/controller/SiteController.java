package com.side.movement.basketball.controller;

import com.side.movement.basketball.Service.ISiteService;
import com.side.movement.basketball.entity.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private ISiteService siteService;

    @RequestMapping(value = "/create" ,method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody Site site) {
        siteService.create(site);
        return "操作结束";
    }
    @ResponseBody
    @RequestMapping(value = "/list" ,method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public List<Site> list(String name) {

        return siteService.list(name);
    }
}
