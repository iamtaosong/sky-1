package com.demo

import com.sky.detector.Detector
import org.apache.mina.util.ExpiringMap
import spock.lang.Specification



class DetectorSpec extends Specification {

    def "As a dev, I want to check  isFailurelog return correct value"() {


        expect:

        assert  Detector.instance.isFailurelog('80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning')==true
        assert  Detector.instance.isFailurelog('80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning')==false

    }



    def "As a dev, I want to check getIpFromLine return correct ip"() {


        expect:

        assert  Detector.instance.getIpFromLine('80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning')=='80.238.9.179'

    }


    def 'As a dev, I want to check parseLine do not return ip for the first time'() {
        expect:
        assert Detector.instance.parseLine('80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning') == ''
        assert Detector.instance.ipMap.get('80.238.9.179') == 1
    }

    def 'As a dev, I want to check parseLine do not return ip when the value is not 4'(){

        when:
        Detector.instance.ipMap = ['80.238.9.179':3]

        then:
        assert Detector.instance.parseLine('80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning')==''
        assert Detector.instance.ipMap.get('80.238.9.179') == 4

    }

    def ' As a dev, I want to check parseLine do return ip when the value is  4 within live time'(){

        when:
        Detector.instance.ipMap = ['80.238.9.179':4]

        then:
        assert Detector.instance.parseLine('80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning')=='80.238.9.179'
        assert Detector.instance.ipMap.get('80.238.9.179') == 4
    }



    def 'given 2 secs time to live, read a failure log, I expect not ip return  '(){

        when:
        Detector.instance.ipMap = new ExpiringMap(1)
        sleep(2000)

        then:
        assert Detector.instance.parseLine('80.238.9.179,133612947,SIGNIN_FAILURE,Dave.Branning')==''
        assert Detector.instance.ipMap.get('80.238.9.179') == 1
    }
}
