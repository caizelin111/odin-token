package com.czl.service.impl;

import com.czl.annotation.OdinToken;
import com.czl.constants.OdinConfig;

/**
 * @author CaiZelin
 * @date 2022/7/14 16:51
 */
@Service
public class DemoServiceImpl {

    public void test(@OdinToken(OdinConfig.TOKEN_HOUSE_ANALLYZE),  HttpServletRequest request) {

    }
}
