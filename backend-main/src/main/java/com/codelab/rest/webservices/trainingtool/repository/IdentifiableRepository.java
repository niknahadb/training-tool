package com.codelab.rest.webservices.trainingtool.repository;

import com.codelab.rest.webservices.trainingtool.model.Identifiable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentifiableRepository<T extends Identifiable> extends JpaRepository<T, Integer> {
}