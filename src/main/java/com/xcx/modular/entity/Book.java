package com.xcx.modular.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Entity说明是实体类
 * @Table指定映射哪一张表，默认为开头首字母小写
 * @Id注明是主键
 * @GenerationValue(strategy = GenerationType.IDENTIFY)规定了主键策略
 * @Column注明了对应的列属性，名字默认都是驼峰规则，length是varchar的长度
 */
@Entity
@Table(name="book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;
    @Column
    private String bookName;
    @Column
    private Double bookPrice;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                '}';
    }
}