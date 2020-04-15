package com.xcx.modular.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xcx.modular.entity.Book;
import com.xcx.modular.system.mapper.BookMapper;
import com.xcx.modular.system.repository.BookRepository;

public class BookService {
	@Autowired
    private BookRepository bookRepository;
	@Autowired
    private BookMapper bookMapper;

    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    
    public Book queryBookByBookId(Integer bookId) {
        return bookMapper.queryBookByBookId(bookId);
    }
}
