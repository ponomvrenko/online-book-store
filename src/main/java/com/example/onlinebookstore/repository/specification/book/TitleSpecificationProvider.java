package com.example.onlinebookstore.repository.specification.book;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.specification.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "title";
    }

    @Override
    public Specification<Book> getSpecification(String[] parameters) {
        return (root, query, criteriaBuilder) -> 
                root.get("title").in(Arrays.stream(parameters).toArray());
    }
}
