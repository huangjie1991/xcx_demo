package com.xcx.modular.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xcx.modular.entity.Book;
import com.xcx.modular.system.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Book> findAll(){
        return bookService.findAll();
    }
    @RequestMapping("/queryBookByBookId/{bookId}")
    @ResponseBody
    public Book queryBookByBookId(@PathVariable Integer bookId){
        return bookService.queryBookByBookId(bookId);
    }
}
