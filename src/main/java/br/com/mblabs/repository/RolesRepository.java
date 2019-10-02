package br.com.mblabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mblabs.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {}