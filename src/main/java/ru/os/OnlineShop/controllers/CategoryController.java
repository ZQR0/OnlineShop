package ru.os.OnlineShop.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.os.OnlineShop.controllers.handlers.HttpErrorHandler;
import ru.os.OnlineShop.controllers.handlers.HttpResponseHandler;
import ru.os.OnlineShop.dto.CategoryDto;
import ru.os.OnlineShop.dto.CategoryUpdateDto;
import ru.os.OnlineShop.entities.CategoryEntity;
import ru.os.OnlineShop.exceptions.CustomResourceNotFoundException;
import ru.os.OnlineShop.exceptions.ResourceAlreadyExistsException;
import ru.os.OnlineShop.models.request.CategoryCreateRequestModel;
import ru.os.OnlineShop.models.request.CategoryUpdateRequestModel;
import ru.os.OnlineShop.services.CategoryService;

import java.util.List;

@RestController("api/category/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(
            path = "get-by-id",
            params = "id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getById(@RequestParam(name = "id") Long id) {
        try {

            CategoryEntity category = this.categoryService.findById(id);

            return new ResponseEntity<>(
                    new HttpResponseHandler(HttpStatus.OK.value(), category),
                    HttpStatus.OK
            );

        } catch (CustomResourceNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(HttpStatus.NOT_FOUND.value(), "Resource not found"
                    ),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(
            path = "get-by-name",
            params = "name",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getByName(@RequestParam(name = "name") String name) {

        try {
            CategoryEntity result = this.categoryService.getByName(name);

            return new ResponseEntity<>(
                    new HttpResponseHandler(HttpStatus.OK.value(), result),
                    HttpStatus.OK
            );

        } catch (CustomResourceNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(HttpStatus.NOT_FOUND.value(), "Resource not found"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(
            path = "get-all-categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAll() {
        List<CategoryEntity> categories = this.categoryService.getAll();

        return new ResponseEntity<>(
                new HttpResponseHandler(HttpStatus.OK.value(), categories),
                HttpStatus.OK
        );
    }

    @DeleteMapping(
            path = "delete-by-id",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteCategoryById(@RequestBody Long id) {
        try {
            this.categoryService.deleteById(id);

            return new ResponseEntity<>(
                    new HttpResponseHandler(HttpStatus.OK.value(), "Successfully deleted"),
                    HttpStatus.OK
            );
        } catch (CustomResourceNotFoundException ex) {
            return new ResponseEntity<>(
                    new HttpErrorHandler(HttpStatus.NOT_FOUND.value(), "No category with this id"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping(
            path = "update-by-id",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateById(@RequestBody CategoryUpdateRequestModel model, @RequestBody Long id) {
        try {
            CategoryUpdateDto dto = this.modelMapper.map(model, CategoryUpdateDto.class);

            this.categoryService.updateById(id, dto);

            return new ResponseEntity<>(
                    new HttpResponseHandler(HttpStatus.OK.value(), "Successfully updated"),
                    HttpStatus.OK
            );
        } catch (CustomResourceNotFoundException ex) {

            return new ResponseEntity<>(
                    new HttpErrorHandler(HttpStatus.NOT_FOUND.value(), "Resource not found"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping(
            path = "create-catogory",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createCategory(@RequestBody CategoryCreateRequestModel model) {
        try {
            CategoryDto dto = this.modelMapper.map(model, CategoryDto.class);

            this.categoryService.createOrRegister(dto);

            return new ResponseEntity<>(
                    new HttpResponseHandler(HttpStatus.OK.value(), "Successfully created"),
                    HttpStatus.OK
            );
        } catch (ResourceAlreadyExistsException ex) {

            return new ResponseEntity<>(
                    new HttpErrorHandler(HttpStatus.BAD_REQUEST.value(), "This category already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
