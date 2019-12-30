package com.upgrad.quora.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {

    @RequestMapping(method = RequestMethod.GET,path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> userProfile(@RequestHeader("authorization") final String bearerToken, @PathVariable("userId") final String userUuid) {
        return  null;
    }
}
