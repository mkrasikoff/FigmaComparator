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
@RequestMapping("/projects")
// TODO: Finish this service in the task FC-## - Add url parsing to simplify user experience with app
public class ProjectsController {

   private static final String MESSAGE_PROJECT_NOT_ADDED = "Error - The project has not been added!";

   private static final String VIEW_PROJECTS_GET_KEY_PAGE = "projects/projectsGetKeyPage";
   private static final String VIEW_PROJECTS_SUCCESS_PAGE = "projects/projectsSuccessPage";
   private static final String VIEW_LOGIN_PAGE = "login/loginPage";

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
      modelAndView.setViewName(VIEW_PROJECTS_GET_KEY_PAGE);

      return modelAndView;
   }

   @PostMapping("/key")
   public ModelAndView setFilesKey(@ModelAttribute("project") @Valid Project project, BindingResult bindingResult) {

      ModelAndView modelAndView = new ModelAndView();

      if(bindingResult.hasErrors()) {
         modelAndView.setViewName(VIEW_PROJECTS_GET_KEY_PAGE);
         return modelAndView;
      }

      try {
         httpService.doGet(FigmaAPI.INFO_ABOUT_PROJECT + project.getKey(), userDao.getActualUser().getToken()); // change api
         projectsDao.addProject(project);
         modelAndView.addObject("project_name", project.getName() + " page");
         modelAndView.setViewName(VIEW_PROJECTS_SUCCESS_PAGE);
      }
      catch (ApiConnectionException exc) {
         modelAndView.addObject("message", MESSAGE_PROJECT_NOT_ADDED);
         modelAndView.setViewName(VIEW_PROJECTS_GET_KEY_PAGE);
      }
      catch (AuthenticationException exc) {
         modelAndView.setViewName(VIEW_LOGIN_PAGE);
      }

      return modelAndView;
   }
}
