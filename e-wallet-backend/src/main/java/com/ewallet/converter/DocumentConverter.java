package com.ewallet.converter;

import org.springframework.stereotype.Component;

import com.ewallet.dto.DocumentDTO;
import com.ewallet.entity.DocumentEntity;

@Component
public class DocumentConverter {

    public DocumentEntity toEntity(DocumentDTO dto){
        DocumentEntity entity = new DocumentEntity();
        entity.setDocType(dto.getDocType());
        entity.setDocNumber(dto.getDocNumber());
        entity.setIssuingAuthority(dto.getIssuingAuthority());
        entity.setExpiryDate(dto.getExpiryDate());
        entity.setImg(dto.getImg());
        return entity;
    }

    public DocumentDTO toDTO(DocumentEntity entity){
        DocumentDTO dto = new DocumentDTO();
        if(entity.getId() != null){
            dto.setId(entity.getId());
        }
        dto.setDocType(entity.getDocType());
        dto.setDocNumber(entity.getDocNumber());
        dto.setIssuingAuthority(entity.getIssuingAuthority());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setImg(entity.getImg());
        return dto;
    }
}
