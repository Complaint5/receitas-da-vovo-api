package com.receitas_da_vovo.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.receitas_da_vovo.dtos.UserDto;
import com.receitas_da_vovo.services.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    //TODO: javadoc

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        UserDto user = this.userService.saveUser(userDto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser (@PathVariable UUID id, @RequestBody UserDto userDto){
        return ResponseEntity.ok(this.userService.updataUser(id, userDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable UUID id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable UUID id){
        return ResponseEntity.ok(this.userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(){
        return ResponseEntity.ok(this.userService.findAllUsers());
    }
}
