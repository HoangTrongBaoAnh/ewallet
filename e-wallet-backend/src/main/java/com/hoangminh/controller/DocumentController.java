package com.hoangminh.controller;

import com.hoangminh.dto.DocumentDTO;
import com.hoangminh.exception.UnknownException;
import com.hoangminh.service.IDocumentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DocumentController {

    private static Logger logger = Logger.getLogger(DocumentController.class);

    @Autowired
    private IDocumentService documentService;

    @PostMapping("/api/document")
    public DocumentDTO addDocument(@RequestBody DocumentDTO model) {
        try {
            logger.info("Success!");
            return documentService.addDocumentById(model);
        } catch (Exception exc) {
            logger.error(exc);
            throw new UnknownException("Unknown Error");
        }
    }
}
