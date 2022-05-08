package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import com.mkrasikoff.figmacomparator.models.User;
import com.mkrasikoff.figmacomparator.services.HttpService;
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

   @Autowired
   private HttpService httpService;

   public static final String MESSAGE_INVALID_TOKEN = "Invalid token, please check that your token is correct and available.";
   public static final String MESSAGE_ACTUAL_TOKEN = "Actual token: ";

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

      try {
         httpService.doGet(FigmaAPI.INFO_ABOUT_ME, user.getToken());
         userDao.saveActualToken(user);
         modelAndView.addObject("token", MESSAGE_ACTUAL_TOKEN + userDao.getActualToken());
      }
      catch (AuthenticationException exc) {
         userDao.saveActualToken(new User());
         modelAndView.addObject("token", MESSAGE_INVALID_TOKEN);
      }

      modelAndView.setViewName("loginSuccessPage");

      return modelAndView;
   }
}
