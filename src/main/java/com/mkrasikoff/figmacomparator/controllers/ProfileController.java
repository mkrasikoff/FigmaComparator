package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {

   @Autowired
   UserDao userDao;

   @Autowired
   HttpService httpService;

   @GetMapping("/me")
   public ModelAndView getUserInformation() {
      ModelAndView modelAndView = new ModelAndView();

      if(userDao.isLoggedIn()) {
         // if true
         modelAndView.setViewName("profile/profilePageNotLoggedIn");
      }
      else {
         modelAndView.setViewName("profile/profilePageNotLoggedIn");
      }

      return modelAndView;
   }
}
