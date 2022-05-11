package com.mkrasikoff.figmacomparator.controllers;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import com.mkrasikoff.figmacomparator.daos.ProjectsDao;
import com.mkrasikoff.figmacomparator.daos.UserDao;
import com.mkrasikoff.figmacomparator.exceptions.ApiConnectionException;
import com.mkrasikoff.figmacomparator.exceptions.AuthenticationException;
import com.mkrasikoff.figmacomparator.models.Project;
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
@RequestMapping("/files")
// TODO: Finish this service in the task FC-## - Add url parsing to simplify user experience with app
public class FileController {

   public static final String MESSAGE_PROJECT_NOT_ADDED = "Error - The project has not been added!";

   @Autowired
   private ProjectsDao projectsDao;

   @Autowired
   private UserDao userDao;

   @Autowired
   private HttpService httpService;

   @GetMapping("/key")
   public ModelAndView getFilesKey() {

      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("project", new Project());
      modelAndView.setViewName("files/filesGetKeyPage");

      return modelAndView;
   }

   @PostMapping("/key")
   public ModelAndView setFilesKey(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult) {

      ModelAndView modelAndView = new ModelAndView();

      if(bindingResult.hasErrors()) {
         modelAndView.setViewName("files/filesGetKeyPage");
         return modelAndView;
      }

      try {
         httpService.doGet(FigmaAPI.INFO_ABOUT_PROJECT + project.getKey(), userDao.getActualToken()); // change api
         projectsDao.addProject(project);
         modelAndView.setViewName("files/filesSuccessPage");
      }
      catch (AuthenticationException | ApiConnectionException exc) {
         modelAndView.addObject("message", MESSAGE_PROJECT_NOT_ADDED);
         modelAndView.setViewName("files/filesGetKeyPage");
      }

      return modelAndView;
   }
}
