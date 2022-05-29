package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ewallet.entity.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
