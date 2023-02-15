package com.redisCache.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redisCache.models.Persona;
import com.redisCache.services.PersonaService;


@RestController
@Component
public class PersonaController {
	private final PersonaService personaService;

	public PersonaController(PersonaService personaService) {
		this.personaService = personaService;
	}//@Autowired

	@GetMapping(value = "/Personas")
	public ResponseEntity<Object> getAllPersonas() {
		List<Persona> personas = this.personaService.getAll();
		return ResponseEntity.ok(personas);
	}

	@GetMapping(value = "/Personas/{id}")
	public ResponseEntity<Object> getPersonaById(@PathVariable("id") String id) {
		long _id = Long.parseLong(id);
		Persona persona = this.personaService.getPersonaById(_id);
		return ResponseEntity.ok(persona);
	}
	@PostMapping(value = "/Personas")
	public ResponseEntity<Object> addPersona(@RequestBody Persona persona) {
		Persona created = this.personaService.add(persona);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping(value = "/Personas")
	public ResponseEntity<Object> updatePersona(@RequestBody Persona persona) {
		Persona updated = this.personaService.update(persona);
		return ResponseEntity.ok(updated);
	}
	@DeleteMapping(value = "/Personas/{id}")
	public ResponseEntity<Object> deletePersonaById(@PathVariable("id") String id) {
		long _id = Long.parseLong(id);
		this.personaService.delete(_id);
		return ResponseEntity.ok().build();
	}


	//CACHE
	@DeleteMapping(value = "/limpiar")//Limpiar lista Cache
	//@Scheduled(cron = "0 */1 * ? * *")//Limpiar Cache por lista
	public void Cache() {
		List<Persona> personas = this.personaService.Cache();
	}

	@DeleteMapping(value = "/limpiar/{id}")//Limpiar Cache por Id
	public void CacheId(@PathVariable("id") String id) {
		long _id = Long.parseLong(id);
		this.personaService.CacheId(_id);
	}

	@DeleteMapping(value = "/limpiartodo" )
	@Scheduled(cron = "0 */30 * ? * *")//Limpiar Cache
	public void clearAllCaches() {
		System.out.println("Cache Limpio");
		personaService.clearCaches();
	}
}

