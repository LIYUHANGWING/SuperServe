package com.backofli.proto;


import GamePlayerTest.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class ProtobufBinaryFileGenerator {
    public static void main(String[] args) {
        try {
            // 创建 Test.TestMsg 消息
            Test.TestMsg testMsg = Test.TestMsg.newBuilder()
                    .setTestF(1.23f)
                    .setTestD(2.34)
                    .setTestInt32(123)
                    .setTestInt64(12345L)
                    .setTestSInt32(-123)
                    .setTestSInt64(-12345L)
                    .setTestUInt32(123)
                    .setTestULong(123456789L)
                    .setTestFixed32(456)
                    .setTestFixed64(789L)
                    .setTestSFixed32(-456)
                    .setTestSFixed64(-789L)
                    .setTestBool(true)
                    .setTestStr("Hello World")
                    .addListInt(1)
                    .addListInt(2)
                    .putTestMap(1, "One")
                    .putTestMap(2, "Two")

                    .build();

            // 将消息写入二进制文件
            try (FileOutputStream output = new FileOutputStream("request.bin")) {
                testMsg.writeTo(output);
            }

            System.out.println("Protobuf message written to request.bin");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
