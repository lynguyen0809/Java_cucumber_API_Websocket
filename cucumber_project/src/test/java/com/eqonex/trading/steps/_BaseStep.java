package com.eqonex.trading.steps;

import com.eqonex.trading.ApiTestRunner;
import com.eqonex.trading._support.GlobalResource;

public class _BaseStep {
    public GlobalResource getGlobalResource(){
        return ApiTestRunner.globalResource;
    }
}
