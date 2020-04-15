package com.xcx.modular.system.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xcx.modular.entity.Book;

/**
 * 接口类继承了JpaRepository之后就可以继承基础的增删改查和分页的功能
 * 其中的JpaRepository的第一个泛型对应的是哪一个实体类，第二个是主键的类型
 */
public interface BookRepository extends JpaRepository<Book,Integer> {
	
	Book findByBookId(@Param("bookId") Integer bookId);
	
	@Query(value = "SELECT * FROM `book` WHERE bookId = ?1 ", nativeQuery = true)
	Book findAllByBookId(@Param("bookId") Integer bookId);
}
