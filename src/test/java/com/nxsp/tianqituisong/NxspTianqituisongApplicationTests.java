package com.nxsp.tianqituisong;

import com.nxsp.tianqituisong.utils.PushUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NxspTianqituisongApplicationTests {

    @Autowired
    private PushUtils pushUtils;

    @Test
    void contextLoads() {
        pushUtils.push();
    }

}
