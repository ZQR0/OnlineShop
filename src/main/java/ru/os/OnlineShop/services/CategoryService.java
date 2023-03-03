package ru.os.OnlineShop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.os.OnlineShop.dto.CategoryDto;
import ru.os.OnlineShop.dto.CategoryUpdateDto;
import ru.os.OnlineShop.entities.CategoryEntity;
import ru.os.OnlineShop.exceptions.CustomResourceNotFoundException;
import ru.os.OnlineShop.exceptions.ResourceAlreadyExistsException;
import ru.os.OnlineShop.models.response.CategoryResponseModel;
import ru.os.OnlineShop.repositories.CategoryRepository;
import ru.os.OnlineShop.services.interfaces.AbstractDaoService;
import ru.os.OnlineShop.utils.DateProvider;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService implements AbstractDaoService<CategoryEntity, CategoryDto> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DateProvider dateProvider;


    @Override
    public CategoryEntity findById(Long id) throws CustomResourceNotFoundException {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public List<CategoryEntity> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) throws CustomResourceNotFoundException {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public void createOrRegister(CategoryDto dto) throws ResourceAlreadyExistsException {
        try {
            CategoryEntity categoryByName = this.getByName(dto.getName());
            final boolean isCategoryExists = Optional.ofNullable(categoryByName).isPresent();

            if (isCategoryExists) throw new ResourceAlreadyExistsException("Category with this name already exists");

            CategoryEntity category = CategoryEntity.builder()
                    .name(dto.getName())
                    .updated_at(this.dateProvider.now())
                    .build();

            this.categoryRepository.save(category);
        } catch (CustomResourceNotFoundException ex) {
            log.info("Category not found");
        }
    }

    public CategoryEntity getByName(String name) throws CustomResourceNotFoundException {
        return this.categoryRepository.findByName(name)
                .orElseThrow(() -> new CustomResourceNotFoundException("Category with name " + name + " not found"));
    }

    public void updateById(Long id, CategoryUpdateDto dto) throws CustomResourceNotFoundException {
        CategoryEntity category = this.findById(id);

        category.setUpdated_at(this.dateProvider.now());
        category.setName(dto.getName());
    }

}
