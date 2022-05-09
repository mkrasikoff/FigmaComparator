package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import com.mkrasikoff.figmacomparator.models.User;
import com.mkrasikoff.figmacomparator.services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class TemporaryLoginController {

   @Autowired
   private UserDao userDao;

   @Autowired
   private HttpService httpService;

   public static final String MESSAGE_INVALID_TOKEN = "Invalid token, please check that your token is correct and available.";
   public static final String MESSAGE_ACTUAL_TOKEN = "Actual token: ";

   @GetMapping("/token")
   public ModelAndView getTemporaryLoginPage() {

      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("user", new User());
      modelAndView.setViewName("login/loginPage");

      return modelAndView;
   }

   @PostMapping("/token")
   public ModelAndView getTokenFromUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

      ModelAndView modelAndView = new ModelAndView();

      if(bindingResult.hasErrors()) {
         modelAndView.setViewName("login/loginPage");
         return modelAndView;
      }

      try {
         httpService.doGet(FigmaAPI.INFO_ABOUT_ME, user.getToken());
         userDao.saveActualToken(user);
         userDao.setLoggedIn(true);
         modelAndView.addObject("token", MESSAGE_ACTUAL_TOKEN + userDao.getActualToken());
      }
      catch (AuthenticationException exc) {
         userDao.saveActualToken(new User());
         userDao.setLoggedIn(false);
         modelAndView.addObject("token", MESSAGE_INVALID_TOKEN);
      }

      modelAndView.setViewName("login/loginSuccessPage");

      return modelAndView;
   }
}
