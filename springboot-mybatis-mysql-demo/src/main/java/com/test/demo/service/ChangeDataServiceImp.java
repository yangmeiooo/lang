package com.test.demo.service;

import com.test.demo.dao.custom.BookCustomMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.demo.dao.model.Book;


import java.util.List;
import java.util.Optional;


@Service
public class ChangeDataServiceImp implements ChangeDataService {

    @Autowired
    private BookCustomMapper bookCustomMapper;
    
    @Override
    public int deleteByUserId(Integer userId){
        return bookCustomMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(Book record){
        return bookCustomMapper.insert(record);
    }

    @Override
    public int insertSelective(Book record){
        return bookCustomMapper.insertSelective(record);
    }

    @Override
    public Optional<Book> selectUserById(Integer id_){
        return bookCustomMapper.selectByPrimaryKey(id_);
    }

    @Override
    public int updateUserNameById(Book record){
        int user = bookCustomMapper.updateByPrimaryKey(record);
        return user;
    }

    @Override
    public int updateByPrimaryKeySelective(Book record){
        return bookCustomMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public List<Book> selectWithPage(Integer page, Integer size){
        List<Book> books = bookCustomMapper.selectWithPage(page,size);
        return books;
    }

    @Override
    public Optional<Book> selectByPrimaryKey(Integer id){
        System.out.println("id:" + id);
        Optional<Book> user = bookCustomMapper.selectByPrimaryKey(id);
        System.out.println(user.toString());
        return user;
    }

}


