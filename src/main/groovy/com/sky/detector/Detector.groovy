package com.sky.detector

import org.apache.mina.util.ExpiringMap

class Detector  implements HackerDetector{


    private static final INSTANCE = new Detector()
    static getInstance(){ return INSTANCE }
    private Detector() {}

    final int DEFAULT_TIME_TO_LIVE = 3000

    def ipMap =new ExpiringMap(DEFAULT_TIME_TO_LIVE)

    @Override
    String parseLine(String logLine){

        if(isFailurelog(logLine)){
            if(ipMap.containsKey(getIpFromLine(logLine))){
                if(ipMap.get(getIpFromLine(logLine))==4){
                    return getIpFromLine(logLine)
                }
                ipMap["${getIpFromLine(logLine)}"] = ipMap.get(getIpFromLine(logLine)) + 1
            }
            else{
                ipMap.put(getIpFromLine(logLine),1)
            }
        }
        return ''
    }

    def isFailurelog(String logLing){
        if(logLing) {
            logLing.split('_')[1].contains('FAILURE')
        }
    }

    def getIpFromLine(String logLing){

        logLing.split(',')[0]

    }

}