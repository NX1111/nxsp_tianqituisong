package com.nxsp.nxsp_tianqituisong.controller;

import com.nxsp.nxsp_tianqituisong.utils.PushUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WechatPushController {

    @RequestMapping("/test")
    public String test(){
      return PushUtils.push();
    }


}
