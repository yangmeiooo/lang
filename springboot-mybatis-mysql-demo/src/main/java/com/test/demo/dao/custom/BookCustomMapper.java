package com.test.demo.dao.custom;

import com.test.demo.dao.mapper.BookMapper;
import com.test.demo.dao.model.Book;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;



@Mapper
public interface BookCustomMapper extends BookMapper {

//    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Select({
            "Select *",
            "from test.book",
            "LIMIT ${(page-1)*size},${size};"
            })
    @Results(id="bookDataResult", value={
            @Result(column="id", property="id",jdbcType = JdbcType.INTEGER ,id=true),
            @Result(column="name", property="name",jdbcType = JdbcType.VARCHAR),
            @Result(column="author", property="author",jdbcType = JdbcType.VARCHAR)
    })
    List<Book> selectWithPage(@Param("page") Integer page,
                              @Param("size") Integer size);
}
