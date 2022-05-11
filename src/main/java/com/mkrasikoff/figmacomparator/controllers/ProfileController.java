package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.services.HttpService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {

   private static final String VIEW_PROFILE_PAGE = "profile/profilePage";
   private static final String VIEW_PROFILE_PAGE_NOT_LOGGED_IN = "profile/profilePageNotLoggedIn";

   @Autowired
   UserDao userDao;

   @Autowired
   HttpService httpService;

   @GetMapping("/me")
   public ModelAndView getUserInformation() {
      ModelAndView modelAndView = new ModelAndView();

      if(userDao.isLoggedIn()) {
         String jsonString = httpService.doGet(FigmaAPI.INFO_ABOUT_ME, userDao.getActualUser().getToken());
         JSONObject jsonObject = new JSONObject(jsonString);
         modelAndView.addObject("name", jsonObject.get("handle"));
         modelAndView.addObject("email", jsonObject.get("email"));
         modelAndView.addObject("img", jsonObject.get("img_url"));
         modelAndView.setViewName(VIEW_PROFILE_PAGE);
      }
      else {
         modelAndView.setViewName(VIEW_PROFILE_PAGE_NOT_LOGGED_IN);
      }

      return modelAndView;
   }
}
