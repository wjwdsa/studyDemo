package com.dlyd.application.gbm.service.esb;

import org.noc.hccp.platform.service.BeanNameResolvingStrategy;

public class ESBBeanNameResolver implements BeanNameResolvingStrategy {

  @Override
  public String resolveBeanName(String code) {
    return code;
//    switch (code) {
//      case "5400-5400":
//        return "SI:Q0001";
//      case "5400-5401":
//        return "SI:Q0002";
//      case "5400-5402":
//        return "SI:Q0003";
//      case "5400-5403":
//        return "SI:ZS001";
//      case "5400-5404":
//        return "SI:ZS002";
//      case "5400-5405":
//        return "SI:ZS003";
//      case "5400-5406":
//        return "SI:ZS004";
//      case "5400-5407":
//        return "SI:ZS010";
//      case "5400-5408":
//        return "SI:DZ010";
//      case "5400-5409":
//        return "SI:QF010";
//      case "5400-5410":
//        return "SI:JF001";
//      case "5400-5411":
//        return "SI:JF002";
//      case "5400-5412":
//        return "SI:QF011";
//      case "0000-03JOB":
//        return "SI:ZS003Job";
//      case "5400-5415":
//        return "SI:QF001";
//      case "0000-QF002":
//        return "SI:QF002";
//      case "5400-5414":
//        return "SI:QF003";
//      case "0000-DZ000":
//        return "SI:DZ000";
//      case "0000-DZ001":
//        return "SI:DZ001";
//      case "0000-DZ002":
//        return "SI:DZ002";
//      case "0000-DZ003":
//        return "SI:DZ003";
//      case "5400-5413":
//        return "SI:DZ004";
//
//
//      case "0000-QY3200":
//        return "SI:QY3200";
//      case "5400-5450":
//        return "SI:QY3300";
//      case "0000-JG0800":
//        return "SI:JG0800";
//      case "0000-JG0900":
//        return "SI:JG0900";
//      case "0000-JG1000":
//        return "SI:JG1000";
//      case "0000-JG1001":
//        return "SI:JG1001";
//      case "0000-JG1002":
//        return "SI:JG1002";
//
//      case "0000-JG2000":
//        return "SI:JG2000";
//      case "0000-JG2001":
//        return "SI:JG2001";
//      case "5400-5451":
//        return "SI:QY2000";
//      case "5400-5452":
//        return "SI:QY2100";
//      case "5400-5453":
//        return "SI:QY2101";
//      case "5400-5454":
//        return "SI:QY2102";
//      case "0000-2102HZ":
//        return "SI:2102HZ";
//      case "5400-5455":
//        return "SI:QY2103";
//      case "5400-5456":
//        return "SI:QY2200";
//      case "5400-5457":
//        return "SI:QY2300";
//      case "0000-QY2400":
//        return "SI:QY2400";
//      case "0000-QY3000":
//        return "SI:QY3000";
//      case "1300-0001":
//        return "CBS:1300-0001";
//      case "1400-9001":
//        return "CBS:1400-9001";
//      case "3000-3001":
//        return "IBPS:3000-3001";
//      case "3000-3003":
//        return "IBPS:3000-3003";
//      case "1220-2013-1":
//        return "CBS:1220-2013-1";
//      case "1220-2013-2":
//        return "CBS:1220-2013-2";
//
//      case "1000-1001":
//        return "CBS:1000-1001";
//      default:
//        return "SI:ERROR";
//    }

  }
}
