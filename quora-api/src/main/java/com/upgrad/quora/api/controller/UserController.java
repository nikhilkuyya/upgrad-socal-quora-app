package com.upgrad.quora.api.controller;



import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class UserController {


    @RequestMapping(method = RequestMethod.POST, path = "/user/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> signup(@RequestBody Object user) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/user/signin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> signin(@RequestHeader("authorization") final String basicToken) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/user/signout",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> signout(@RequestHeader("authorization") final  String bearerToken){
        return null;
    }
}
