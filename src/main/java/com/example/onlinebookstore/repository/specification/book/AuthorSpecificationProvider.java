package com.example.onlinebookstore.repository.specification.book;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.specification.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "author";
    }

    @Override
    public Specification<Book> getSpecification(String[] parameters) {
        return (root, query, criteriaBuilder) ->
                root.get("author").in(Arrays.stream(parameters).toArray());
    }
}
