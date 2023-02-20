package com.eoemusic.eoebackend.controller;

import com.eoemusic.eoebackend.entity.User;
import com.eoemusic.eoebackend.repository.UserRepository;
import com.eoemusic.eoebackend.service.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 14/01/23 4:02 AM
 */
@RestController
@Slf4j
@RequestMapping("/eoebackend")
public class TestController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/test")
  public String test() {
    log.info("hit test");
    return "Successfully get backend!";
  }

  @PostMapping("/insertUser")
  public void insertUser() {
    User user = new User();
    user.setUsername("test");
    user.setPhoneCountry("086");
    user.setPhoneNumber("111");
    user.setPlaylistIds("1");
    userRepository.save(user);
  }

  @Autowired
  private MusicService musicService;

  public static void main(String[] args) {
    System.out.println("米诺 柚恩".replaceAll("\\s+", ""));
    ;
  }

}
