package com.cv4j.proxy.web.controller;

import com.cv4j.proxy.domain.Proxy;
import com.cv4j.proxy.http.HttpManager;
import com.cv4j.proxy.web.dao.ProxyDao;
import com.cv4j.proxy.web.dto.QueryProxyDTO;
import com.cv4j.proxy.web.dto.ResultProxy;
import com.cv4j.proxy.web.job.ScheduleJobs;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CommonController {

    @Autowired
    private ScheduleJobs scheduleJobs;

    @RequestMapping(value="/load")
    public String load(String pagename) {
        log.info("load, pagename="+pagename);
        return pagename;
    }

    @RequestMapping(value="/startJob")
    @ResponseBody
    public void startJob(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        log.info("manual startJob");
        try {
            httpServletResponse.setContentType("text/plain; charset=utf-8");
            ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
            if(ScheduleJobs.IS_JOB_RUNNING) {
                responseOutputStream.write("Job正在运行。。。".getBytes("utf-8"));
                responseOutputStream.flush();
                responseOutputStream.close();
            } else {
                log.info("scheduleJobs.cronJob() start by controller...");
                scheduleJobs.cronJob();
            }
        } catch (Exception e) {
            log.info("startJob exception e="+e.getMessage());
        }
    }

}