package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class TestController {

   @GetMapping("/test")
   public ModelAndView test() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("test.html");
      return modelAndView;
   }

   @PostMapping("/submit")
   public String getTokenFromUser(@ModelAttribute("user") User user) {

      System.out.println("Current token: " + user.getToken());
      return "redirect:test.html";
   }
}
