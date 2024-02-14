package com.example.onlinebookstore.repository.specification.book;

import com.example.onlinebookstore.dto.book.BookSearchParametersDto;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.specification.SpecificationBuilder;
import com.example.onlinebookstore.repository.specification.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.title()));
        }
        if (searchParameters.author() != null && searchParameters.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.author()));
        }
        if (searchParameters.isbn() != null && searchParameters.isbn().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbn()));
        }
        return spec;
    }
}
