package com.xcx.modular.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.xcx.modular.entity.Book;

public interface BookMapper{
    /**
     * 按照书的id来查询数据
     * @param bookId
     * @return
     */
    Book queryBookByBookId(@Param("bookId") Integer bookId);
}
