package com.ewallet.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ewallet.dto.DocumentDTO;
import com.ewallet.exception.UnknownException;
import com.ewallet.service.IDocumentService;

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
