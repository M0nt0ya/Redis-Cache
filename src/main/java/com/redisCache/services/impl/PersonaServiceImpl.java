package com.redisCache.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.redisCache.models.Persona;
import com.redisCache.repositories.PersonaRepository;
import com.redisCache.services.PersonaService;

import javax.transaction.Transactional;


@Service//Se conecta a varios repositorios y agrupa su funcionalidad
@CacheConfig(cacheNames =  "personaCache")//Configuración común relacionada con la memoria caché
@Transactional//Son los metadatos que especifican la semántica de las transacciones en un método
public class PersonaServiceImpl extends PersonaService {

	private final PersonaRepository personaRepository;
	public PersonaServiceImpl(CacheManager cacheManager, PersonaRepository personaRepository) {
		super(cacheManager);
		this.personaRepository = personaRepository;}//@Autowired


	@Override
	@Transactional
	@Cacheable(cacheNames = "personas")	// Encontrar registro existente	por lista
	public List<Persona> getAll() {
		return this.personaRepository.findAll();
	}

	@Override
	@Transactional
	@CacheEvict(cacheNames = "personas", allEntries = true)//Create
	public Persona add(Persona persona) {
		return this.personaRepository.save(persona);
	}


	@Override
	@Transactional
	@CacheEvict(cacheNames =  "personas", allEntries = true)//Update
		public Persona update(Persona persona) {
		Optional<Persona> optPersona = this.personaRepository.findById( persona.getId());
		if (!optPersona.isPresent())
			return null;
		Persona repPersona = optPersona.get();
		repPersona.setNombre(persona.getNombre());
		repPersona.setApellidos(persona.getApellidos());
 		return this.personaRepository.save(repPersona);
	}

	@Override
	@Transactional
	@Caching(evict = { @CacheEvict(cacheNames = "persona", key = "#id"),
			@CacheEvict(cacheNames = "personas") })
	public void delete(long id) {
		this.personaRepository.deleteById(id);
	}


	@Override
	@Transactional
	@Cacheable(cacheNames = "personas", key = "#id")  // Encontrar registro existente	por ID
	public Persona getPersonaById(long id) {
		return this.personaRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	@Caching(evict = { @CacheEvict(cacheNames = "persona", key = "#id"),
			@CacheEvict(cacheNames = "personas") })// Eliminar CACHE de registro existente por ID
	public Persona CacheId(long id) {
		return this.personaRepository.findById(id).orElse(null);
	}


	@Caching(evict = { @CacheEvict(cacheNames = "persona"),
			@CacheEvict(cacheNames = "personas") })// Eliminar CACHE de registro existente por ID
	public List<Persona> Cache() {
		return this.personaRepository.findAll();
	}

}
