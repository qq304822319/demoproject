package com.yangk.demoproject.controller.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@Api(tags = "多线程测试接口")
@RequestMapping("/test")
public class FutureTestController {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/future")
    @ApiOperation(value = "多线程测试接口", notes = "测试future多线程")
    public String future(@RequestParam("code") String code) throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();
        Future<String> testFuture1 = testFuture1();
        Future<String> testFuture2 = testFuture2();
        // Future.get()方法实现了方法中的不同线程结果的同步获取
        String s1 = testFuture1.get() + testFuture2.get();
        String s2 = testFuture2.get() + testFuture1.get();
        long endTime = System.currentTimeMillis();

        return "耗时:" + (endTime - startTime) + "毫秒;  case1: " + s1 + ";  case2: " + s2;
    }


    private Future<String> testFuture1(){
        return threadPoolTaskExecutor.submit(() -> {
            sleep(10000);
            return "future1";
        });
    }

    private Future<String> testFuture2(){
        return threadPoolTaskExecutor.submit(() -> {
            sleep(15000);
            return "future2";
        });
    }

    private void sleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
