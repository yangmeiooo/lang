package com.test.demo.dao.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BookDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.024905+08:00", comments="Source Table: book")
    public static final Book book = new Book();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.025395+08:00", comments="Source field: book.id")
    public static final SqlColumn<Integer> id = book.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026+08:00", comments="Source field: book.name")
    public static final SqlColumn<String> name = book.name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026138+08:00", comments="Source field: book.author")
    public static final SqlColumn<String> author = book.author;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026234+08:00", comments="Source field: book.publish")
    public static final SqlColumn<String> publish = book.publish;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026369+08:00", comments="Source field: book.pages")
    public static final SqlColumn<Integer> pages = book.pages;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026541+08:00", comments="Source field: book.price")
    public static final SqlColumn<Float> price = book.price;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026692+08:00", comments="Source field: book.bookcaseid")
    public static final SqlColumn<Integer> bookcaseid = book.bookcaseid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.026831+08:00", comments="Source field: book.abled")
    public static final SqlColumn<Integer> abled = book.abled;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.025153+08:00", comments="Source Table: book")
    public static final class Book extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<String> author = column("author", JDBCType.VARCHAR);

        public final SqlColumn<String> publish = column("publish", JDBCType.VARCHAR);

        public final SqlColumn<Integer> pages = column("pages", JDBCType.INTEGER);

        public final SqlColumn<Float> price = column("price", JDBCType.REAL);

        public final SqlColumn<Integer> bookcaseid = column("bookcaseid", JDBCType.INTEGER);

        public final SqlColumn<Integer> abled = column("abled", JDBCType.INTEGER);

        public Book() {
            super("book");
        }
    }
}