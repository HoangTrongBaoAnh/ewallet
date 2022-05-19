package com.hoangminh.service.impl;

import com.hoangminh.converter.DocumentConverter;
import com.hoangminh.dto.DocumentDTO;
import com.hoangminh.entity.DocumentEntity;
import com.hoangminh.repository.DocumentRepository;
import com.hoangminh.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService implements IDocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentConverter documentConverter;

    @Override
    public DocumentDTO addDocumentById(DocumentDTO documentDTO) {
        DocumentEntity documentEntity = documentConverter.toEntity(documentDTO);
        documentEntity = documentRepository.saveAndFlush(documentEntity);
        return documentConverter.toDTO(documentEntity);
    }
}
