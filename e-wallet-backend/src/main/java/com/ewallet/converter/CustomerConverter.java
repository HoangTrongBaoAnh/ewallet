package com.ewallet.converter;

import org.springframework.stereotype.Component;

import com.ewallet.dto.CustomerDTO;
import com.ewallet.entity.CustomerEntity;

@Component
public class CustomerConverter {

    public CustomerEntity toEntity(CustomerDTO dto){
        CustomerEntity entity = new CustomerEntity();
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setEmail(dto.getEmail());
        entity.setNationality(dto.getNationality());
        entity.setAddress(dto.getAddress());
        return entity;
    }

    public CustomerDTO toDTO(CustomerEntity entity){
        CustomerDTO dto = new CustomerDTO();
        if(entity.getId() != null){
            dto.setId(entity.getId());
        }
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmail(entity.getEmail());
        dto.setNationality(entity.getNationality());
        dto.setAddress(entity.getAddress());
        dto.setPhonenumber(entity.getPhonenumber());
        dto.setBalance(entity.getBalance());
        dto.setUsername(entity.getUsername());
        dto.setRoles(entity.getRoles());
        return dto;
    }

    public CustomerEntity toEntity(CustomerDTO dto, CustomerEntity entity) {
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setEmail(dto.getEmail());
        entity.setPhonenumber(dto.getPhonenumber());
        entity.setNationality(dto.getNationality());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}
