package com.redisCache.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redisCache.models.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

}

