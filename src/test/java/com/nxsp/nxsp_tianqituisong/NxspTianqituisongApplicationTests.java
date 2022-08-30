package com.nxsp.nxsp_tianqituisong;

import com.nxsp.nxsp_tianqituisong.utils.PushUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NxspTianqituisongApplicationTests {

    @Test
    void contextLoads() {
        PushUtils.push();
    }

}
