package com.ewallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.converter.DocumentConverter;
import com.ewallet.dto.DocumentDTO;
import com.ewallet.entity.DocumentEntity;
import com.ewallet.repository.DocumentRepository;
import com.ewallet.service.IDocumentService;

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
