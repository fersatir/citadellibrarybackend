package com.library.service;

import com.library.domain.Book;
import com.library.domain.Category;
import com.library.domain.enums.RoleType;
import com.library.exception.BadRequestException;
import com.library.exception.ConflictException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    public Category saveCategory(Category category) {

        categoryRepository.save(category);

        return category;
    }


    public Page<Category> getAllWithPage(Pageable pageable) {

      return  categoryRepository.findAll(pageable);
    }

    public Category updateCategory(Long id, Category category) {

        Category foundCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));

        foundCategory.setName(category.getName());
        foundCategory.setSequence(category.getSequence());
        foundCategory.setBuiltIn(category.getBuiltIn());

        categoryRepository.save(foundCategory);

        category.setId(foundCategory.getId());
        return category;
    }

    public Category deleteCategory(Long id) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));

        if(!bookRepository.returnBooks(id).isEmpty()){
            throw new BadRequestException(String.format(ErrorMessage.CATEGORY_NOT_DELETE_MESSAGE,id));
        }else if(foundCategory.getBuiltIn() == true){
            throw new ConflictException(String.format(ErrorMessage.CATEGORY_NOT_DELETE_BUILTIN_MESSAGE,id));
        }else categoryRepository.delete(foundCategory);

        return foundCategory;
    }

    public Category getCategory(Long id) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));

        return foundCategory;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
