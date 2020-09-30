package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository repository;

    @PostMapping("/add")
    public String addUser(@RequestBody final User user) {
        System.out.println(user.toString());
        repository.save(user);
        return "add user with id " + user.getId();
    }

    @GetMapping("/list")
    @ResponseBody
    public List<User> getAllUser() {
        return repository.findAll();
    }

    @GetMapping("/detail")
    @ResponseBody
    public Optional<User> detail(@RequestParam(required = true) final String id) {
        return repository.findById(id);
    }

    @PutMapping("/edit/{id}")
    public String edit(@PathVariable final String id, @RequestBody User newUser) throws NameNotFoundException {
        repository.findById(id).map(user -> {
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setUsername(newUser.getUsername());
            user.setAddress(newUser.getAddress());
            return repository.save(user);
        })
        .orElseThrow(() -> new NameNotFoundException("user not found!"));
        return "edit user with id " + id + "success";

    }
}
