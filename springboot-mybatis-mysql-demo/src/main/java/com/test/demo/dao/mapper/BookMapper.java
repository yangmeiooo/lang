package com.test.demo.dao.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.test.demo.dao.model.Book;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface BookMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.038739+08:00", comments="Source Table: book")
    BasicColumn[] selectList = BasicColumn.columnList(BookDynamicSqlSupport.id, BookDynamicSqlSupport.name, BookDynamicSqlSupport.author, BookDynamicSqlSupport.publish, BookDynamicSqlSupport.pages, BookDynamicSqlSupport.price, BookDynamicSqlSupport.bookcaseid, BookDynamicSqlSupport.abled);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.027978+08:00", comments="Source Table: book")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.029468+08:00", comments="Source Table: book")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.029881+08:00", comments="Source Table: book")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Book> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.030472+08:00", comments="Source Table: book")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Book> multipleInsertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.03095+08:00", comments="Source Table: book")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("BookResult")
    Optional<Book> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.031407+08:00", comments="Source Table: book")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="BookResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publish", property="publish", jdbcType=JdbcType.VARCHAR),
        @Result(column="pages", property="pages", jdbcType=JdbcType.INTEGER),
        @Result(column="price", property="price", jdbcType=JdbcType.REAL),
        @Result(column="bookcaseid", property="bookcaseid", jdbcType=JdbcType.INTEGER),
        @Result(column="abled", property="abled", jdbcType=JdbcType.INTEGER)
    })
    List<Book> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.033135+08:00", comments="Source Table: book")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.034014+08:00", comments="Source Table: book")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, BookDynamicSqlSupport.book, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.034376+08:00", comments="Source Table: book")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, BookDynamicSqlSupport.book, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.034757+08:00", comments="Source Table: book")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(BookDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.035157+08:00", comments="Source Table: book")
    default int insert(Book record) {
        return MyBatis3Utils.insert(this::insert, record, BookDynamicSqlSupport.book, c ->
            c.map(BookDynamicSqlSupport.id).toProperty("id")
            .map(BookDynamicSqlSupport.name).toProperty("name")
            .map(BookDynamicSqlSupport.author).toProperty("author")
            .map(BookDynamicSqlSupport.publish).toProperty("publish")
            .map(BookDynamicSqlSupport.pages).toProperty("pages")
            .map(BookDynamicSqlSupport.price).toProperty("price")
            .map(BookDynamicSqlSupport.bookcaseid).toProperty("bookcaseid")
            .map(BookDynamicSqlSupport.abled).toProperty("abled")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.036758+08:00", comments="Source Table: book")
    default int insertMultiple(Collection<Book> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, BookDynamicSqlSupport.book, c ->
            c.map(BookDynamicSqlSupport.id).toProperty("id")
            .map(BookDynamicSqlSupport.name).toProperty("name")
            .map(BookDynamicSqlSupport.author).toProperty("author")
            .map(BookDynamicSqlSupport.publish).toProperty("publish")
            .map(BookDynamicSqlSupport.pages).toProperty("pages")
            .map(BookDynamicSqlSupport.price).toProperty("price")
            .map(BookDynamicSqlSupport.bookcaseid).toProperty("bookcaseid")
            .map(BookDynamicSqlSupport.abled).toProperty("abled")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.037286+08:00", comments="Source Table: book")
    default int insertSelective(Book record) {
        return MyBatis3Utils.insert(this::insert, record, BookDynamicSqlSupport.book, c ->
            c.map(BookDynamicSqlSupport.id).toPropertyWhenPresent("id", record::getId)
            .map(BookDynamicSqlSupport.name).toPropertyWhenPresent("name", record::getName)
            .map(BookDynamicSqlSupport.author).toPropertyWhenPresent("author", record::getAuthor)
            .map(BookDynamicSqlSupport.publish).toPropertyWhenPresent("publish", record::getPublish)
            .map(BookDynamicSqlSupport.pages).toPropertyWhenPresent("pages", record::getPages)
            .map(BookDynamicSqlSupport.price).toPropertyWhenPresent("price", record::getPrice)
            .map(BookDynamicSqlSupport.bookcaseid).toPropertyWhenPresent("bookcaseid", record::getBookcaseid)
            .map(BookDynamicSqlSupport.abled).toPropertyWhenPresent("abled", record::getAbled)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.039983+08:00", comments="Source Table: book")
    default Optional<Book> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, BookDynamicSqlSupport.book, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.040492+08:00", comments="Source Table: book")
    default List<Book> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, BookDynamicSqlSupport.book, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.040904+08:00", comments="Source Table: book")
    default List<Book> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, BookDynamicSqlSupport.book, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.041432+08:00", comments="Source Table: book")
    default Optional<Book> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(BookDynamicSqlSupport.id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.041863+08:00", comments="Source Table: book")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, BookDynamicSqlSupport.book, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.042243+08:00", comments="Source Table: book")
    static UpdateDSL<UpdateModel> updateAllColumns(Book record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(BookDynamicSqlSupport.id).equalTo(record::getId)
                .set(BookDynamicSqlSupport.name).equalTo(record::getName)
                .set(BookDynamicSqlSupport.author).equalTo(record::getAuthor)
                .set(BookDynamicSqlSupport.publish).equalTo(record::getPublish)
                .set(BookDynamicSqlSupport.pages).equalTo(record::getPages)
                .set(BookDynamicSqlSupport.price).equalTo(record::getPrice)
                .set(BookDynamicSqlSupport.bookcaseid).equalTo(record::getBookcaseid)
                .set(BookDynamicSqlSupport.abled).equalTo(record::getAbled);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.042724+08:00", comments="Source Table: book")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Book record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(BookDynamicSqlSupport.id).equalToWhenPresent(record::getId)
                .set(BookDynamicSqlSupport.name).equalToWhenPresent(record::getName)
                .set(BookDynamicSqlSupport.author).equalToWhenPresent(record::getAuthor)
                .set(BookDynamicSqlSupport.publish).equalToWhenPresent(record::getPublish)
                .set(BookDynamicSqlSupport.pages).equalToWhenPresent(record::getPages)
                .set(BookDynamicSqlSupport.price).equalToWhenPresent(record::getPrice)
                .set(BookDynamicSqlSupport.bookcaseid).equalToWhenPresent(record::getBookcaseid)
                .set(BookDynamicSqlSupport.abled).equalToWhenPresent(record::getAbled);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.043449+08:00", comments="Source Table: book")
    default int updateByPrimaryKey(Book record) {
        return update(c ->
            c.set(BookDynamicSqlSupport.name).equalTo(record::getName)
            .set(BookDynamicSqlSupport.author).equalTo(record::getAuthor)
            .set(BookDynamicSqlSupport.publish).equalTo(record::getPublish)
            .set(BookDynamicSqlSupport.pages).equalTo(record::getPages)
            .set(BookDynamicSqlSupport.price).equalTo(record::getPrice)
            .set(BookDynamicSqlSupport.bookcaseid).equalTo(record::getBookcaseid)
            .set(BookDynamicSqlSupport.abled).equalTo(record::getAbled)
            .where(BookDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2020-08-10T14:34:16.043935+08:00", comments="Source Table: book")
    default int updateByPrimaryKeySelective(Book record) {
        return update(c ->
            c.set(BookDynamicSqlSupport.name).equalToWhenPresent(record::getName)
            .set(BookDynamicSqlSupport.author).equalToWhenPresent(record::getAuthor)
            .set(BookDynamicSqlSupport.publish).equalToWhenPresent(record::getPublish)
            .set(BookDynamicSqlSupport.pages).equalToWhenPresent(record::getPages)
            .set(BookDynamicSqlSupport.price).equalToWhenPresent(record::getPrice)
            .set(BookDynamicSqlSupport.bookcaseid).equalToWhenPresent(record::getBookcaseid)
            .set(BookDynamicSqlSupport.abled).equalToWhenPresent(record::getAbled)
            .where(BookDynamicSqlSupport.id, isEqualTo(record::getId))
        );
    }
}