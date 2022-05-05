package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class TemporaryLoginController {

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
   public ModelAndView getTokenFromUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

      ModelAndView modelAndView = new ModelAndView();

      if(bindingResult.hasErrors()) {
         modelAndView.setViewName("loginPage");
         return modelAndView;
      }

      userDao.saveActualToken(user);
      modelAndView.addObject("token", userDao.getActualToken());
      modelAndView.setViewName("loginSuccess");

      return modelAndView;
   }

   @GetMapping("/test")
   public ModelAndView testNormalPage() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("index");

      return modelAndView;
   }
}
