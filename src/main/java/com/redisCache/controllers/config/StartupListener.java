package com.redisCache.controllers.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component//Detecta automáticamente nuestros beans personalizados
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {


	private final CacheManager cacheManager;
	public StartupListener(CacheManager cacheManager) {
		this.cacheManager = cacheManager;}//@Autowired
	@SuppressWarnings("NullableProblems")//Es para suprimir o ignorar las advertencias que provienen del compilador
	@Override
	public final void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("Cache listo");
		cacheManager.getCacheNames().parallelStream().forEach(System.out::println);
	}

}