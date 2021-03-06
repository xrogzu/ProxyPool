package com.cv4j.proxy.web.controller;

import com.cv4j.proxy.domain.Proxy;
import com.cv4j.proxy.http.HttpManager;
import com.cv4j.proxy.web.dao.ProxyDao;
import com.cv4j.proxy.web.dao.ProxyResourceDao;
import com.cv4j.proxy.web.dto.ProxyResource;
import com.cv4j.proxy.web.dto.QueryProxyDTO;
import com.cv4j.proxy.web.dto.ResourcePlan;
import com.cv4j.proxy.web.dto.ResultProxy;
import com.cv4j.proxy.web.job.ScheduleJobs;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ProxyResourceController {

    @Autowired
    private ProxyResourceDao proxyResourceDao;

    @RequestMapping(value="/proxyResourceController/getAllResource")
    @ResponseBody
    public List<ProxyResource> getAllResource() {
        log.info("getAllResource");
        List<ProxyResource> result = proxyResourceDao.findAllProxyResource();
        if(result == null) {
            log.info("getAllResource, result = null");
            return null;
        } else{
            log.info("getAllResource, result = "+result.size());
            return result;
        }
    }

    @RequestMapping(value="/proxyResourceController/doSaveResource", method= RequestMethod.POST)
    @ResponseBody
    public int doSaveResource(@RequestBody ProxyResource proxyResource) {
        int result = 0;
        if(proxyResource != null) {
            log.info("doSaveResource getSuffix="+proxyResource.getSuffix());
            proxyResourceDao.saveProxyResource(proxyResource);
            result = 1;
        }

        return result;
    }

    @RequestMapping(value="/proxyResourceController/doSaveResourcePlan", method= RequestMethod.POST)
    @ResponseBody
    public int doSaveResourcePlan(@RequestBody ResourcePlan resourcePlan) {
        int result = 0;
        if(resourcePlan != null) {
            log.info("doSaveResourcePlan refResId="+resourcePlan.getProxyResource().getResId());
            proxyResourceDao.saveResourcePlan(resourcePlan);
            result = 1;
        }
        return result;
    }

    @RequestMapping(value="/proxyResourceController/getAllResourcePlan")
    @ResponseBody
    public List<ResourcePlan> getAllResourcePlan() {
        log.info("getAllResourcePlan");
        List<ResourcePlan> result = proxyResourceDao.findAllResourcePlan();
        if(result == null) {
            log.info("getAllResourcePlan, result = null");
            return null;
        } else{
            log.info("getAllResourcePlan, result = "+result.size());
            return result;
        }
    }

    @RequestMapping(value="/proxyResourceController/doDeleteResourcePlan", method= RequestMethod.POST)
    @ResponseBody
    public int doDeleteResourcePlan(@RequestBody ResourcePlan resourcePlan) {
        int result = 0;
        if(resourcePlan != null) {
            log.info("doDeleteResourcePlan id="+resourcePlan.getProxyResource().getId());
            proxyResourceDao.deleteResourcePlan(resourcePlan);
            result = 1;
        }
        return result;
    }

}