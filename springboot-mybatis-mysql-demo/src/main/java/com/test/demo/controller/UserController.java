package com.test.demo.controller;

import com.test.demo.dao.mapper.BookMapper;
import com.test.demo.service.ChangeDataService;
import com.test.demo.dao.model.Book;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class UserController {

    @Autowired
    ChangeDataService services;

    @RequestMapping("/findAll/{page}/{size}")
    List<Book> selectWithPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        List<Book> books = services.selectWithPage(page, size);
        return books;
    }

    @GetMapping("/findById/{id}")
    public Optional<Book> findById(@PathVariable("id") Integer id){
        Optional<Book> user = services.selectByPrimaryKey(id);
        return user;
    }

    //delete user by Id
    @DeleteMapping("/deleteById/{id}")
    public void deleteUserId(@PathVariable("id") Integer id) {
        services.deleteByUserId(id);
        System.out.println("The user id: "+ id +"has been deleted");
    }
    //save user
    @PostMapping("/save")
    public int save(@RequestBody Book book){
        int user = services.insert(book);
        return user;
    }

    //update user
    @PutMapping("/update")
    public int update(@RequestBody Book book){
        int user = services.updateUserNameById(book);
        System.out.println(user);
        return user;
    }

}