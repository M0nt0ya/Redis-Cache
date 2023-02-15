package com.redisCache.services;

import java.util.List;

import com.redisCache.models.Persona;
import org.springframework.cache.CacheManager;

public abstract class PersonaService {

	public abstract List<Persona> getAll();
	public abstract Persona add(Persona persona);

	public abstract Persona update(Persona persona);

	public abstract void delete(long id);


	public abstract Persona getPersonaById(long id);



    //CACHE
	protected PersonaService(CacheManager cacheManager) {
		this.cacheManager = cacheManager;}
	private final CacheManager cacheManager;
	public void clearCaches() {
		cacheManager.getCacheNames().stream()
				.forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

	public abstract List<Persona> Cache();

	public abstract Persona CacheId(long id);

}
