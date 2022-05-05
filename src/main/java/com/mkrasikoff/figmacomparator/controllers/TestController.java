package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/login")
public class TestController {

   @Autowired
   private UserDao userDao;

   @GetMapping("/token")
   public ModelAndView test() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("user", new User());
      modelAndView.setViewName("loginPage");
      return modelAndView;
   }

   @PostMapping("/token")
   public RedirectView getTokenFromUser(@ModelAttribute("user") User user) {

      userDao.saveActualToken(user);
      return new RedirectView("/login/token");
   }
}
