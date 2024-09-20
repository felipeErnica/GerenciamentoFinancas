package com.santacarolina.interfaces;

public interface DTOConversible<T,DTO> {
    T returnNew();
    T fromDTO(DTO dto);
    DTO toDTO();
}
