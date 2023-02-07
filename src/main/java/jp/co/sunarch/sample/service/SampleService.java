package jp.co.sunarch.sample.service;

import org.springframework.stereotype.Service;

import jp.sample.SampleService2;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SampleService {

	public void methodA() {
		log.info("SampleService.methodA start");
		
		for(int i=0;i<5;i++) {
			log.debug("loop count:{}", i);
		}
		
		SampleService2 s2 = new SampleService2();
		s2.methodA();
		
		log.info("SampleService.methodA end");
	}
}
