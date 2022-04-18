package com.dlyd.application.gbm.test;

import com.alibaba.druid.support.json.JSONUtils;

import java.util.HashMap;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String json = "{\n" +
                "\t\"msg\": [{\n" +
                "\t\t\"codeName\": \"jy_200\",\n" +
                "\t\t\"prompt\": \"新增0条,删除0条。\"\n" +
                "\t}, {\n" +
                "\t\t\"codeName\": \"jy_203\",\n" +
                "\t\t\"ktkjInfos\": [{\n" +
                "\t\t\t\"balance\": \"1.00\",\n" +
                "\t\t\t\"bankId\": \"1\",\n" +
                "\t\t\t\"counterBankId\": \"2\",\n" +
                "\t\t\t\"counterBankName\": \"1\",\n" +
                "\t\t\t\"currency\": \"CNY\",\n" +
                "\t\t\t\"handFee\": \"0.00\",\n" +
                "\t\t\t\"loanSign\": \"1\",\n" +
                "\t\t\t\"mark\": \"1\",\n" +
                "\t\t\t\"payeeName\": \"ceshi\",\n" +
                "\t\t\t\"pzNumber\": \"1\",\n" +
                "\t\t\t\"remarks\": \"2\",\n" +
                "\t\t\t\"total\": \"1.00\",\n" +
                "\t\t\t\"transAmount\": \"1.00\",\n" +
                "\t\t\t\"transHour\": \"2021-05-07 17:09:00\",\n" +
                "\t\t\t\"transType\": \"转账\",\n" +
                "\t\t\t\"useFunds\": \"sheji\",\n" +
                "\t\t\t\"virtualBankId\": \"0\",\n" +
                "\t\t\t\"voucherNumber\": \"00001\"\n" +
                "\t\t}],\n" +
                "\t\t\"prompt\": \"有数据的账号不存在系统中或者账号所对应银行的不一致\"\n" +
                "\t}],\n" +
                "\t\"status\": 200\n" +
                "}";

        HashMap<String, Object> parse = (HashMap<String, Object>) JSONUtils.parse(json);
        int status = (int) parse.get("status");
        System.out.println(status);
        List<HashMap> msg = (List<HashMap>) parse.get("msg");
        for (int i = 0; i < msg.size(); i++) {
            System.out.println("----------------");
            HashMap hashMap = msg.get(i);
            System.out.println(hashMap.get("codeName"));
            System.out.println(hashMap.get("prompt"));
            List<HashMap> ktkjInfoList = (List<HashMap>) hashMap.get("ktkjInfos");
            if (ktkjInfoList != null) {
                for (HashMap ktkjInfo : ktkjInfoList) {
                    System.out.println(ktkjInfo.get("bankId"));
                }
            }
        }
    }
}
