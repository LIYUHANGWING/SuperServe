package com.backofli.controller;

import GamePlayerTest.Test;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    @PostMapping(value = "/test", consumes = "application/x-protobuf", produces = "application/x-protobuf")
    public Test.TestMsg testProtobuf(@RequestBody Test.TestMsg request) {
        // 处理接收到的 Protobuf 消息
        System.out.println("Received: " + request);

        // 构建一个新的响应消息
        Test.TestMsg response = Test.TestMsg.newBuilder()
                .setTestF(request.getTestF())
                .setTestD(request.getTestD())
                .setTestInt32(request.getTestInt32())
                .setTestInt64(request.getTestInt64())
                .setTestSInt32(request.getTestSInt32())
                .setTestSInt64(request.getTestSInt64())
                .setTestUInt32(request.getTestUInt32())
                .setTestULong(request.getTestULong())
                .setTestFixed32(request.getTestFixed32())
                .setTestFixed64(request.getTestFixed64())
                .setTestSFixed32(request.getTestSFixed32())
                .setTestSFixed64(request.getTestSFixed64())
                .setTestBool(request.getTestBool())
                .setTestStr("Response: " + request.getTestStr())
                .addAllListInt(request.getListIntList())
                .putAllTestMap(request.getTestMapMap())
                // 正确传递枚举类型

                .build();

        return response;
    }
}