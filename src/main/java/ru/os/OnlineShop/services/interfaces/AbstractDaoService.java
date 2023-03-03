package ru.os.OnlineShop.services.interfaces;


import ru.os.OnlineShop.dto.AbstractDto;
import ru.os.OnlineShop.entities.AbstractEntity;
import ru.os.OnlineShop.exceptions.ResourceAlreadyExistsException;

import java.util.List;

/*
* @author ZQR0
* @since 25.02.2023
* Abstract interface for all Services, which
* work with repositories or DAO's
*
* T - entity for repository (DB entity)
* D - DTO for data transfers
*/
public interface AbstractDaoService<T extends AbstractEntity, D extends AbstractDto> {
    T findById(Long id) throws Exception;
    List<T> getAll();
    void deleteById(Long id) throws Exception;
    void createOrRegister(D dto) throws Exception;
}
