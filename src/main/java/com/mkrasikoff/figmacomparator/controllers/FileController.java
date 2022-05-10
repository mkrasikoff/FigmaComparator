package com.mkrasikoff.figmacomparator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/files")
public class FileController {

   @GetMapping("/key")
   public ModelAndView getFilesKey() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("files/filesGetKeyPage");

      return modelAndView;
   }

   @PostMapping("/key")
   public ModelAndView setFilesKey() {
      ModelAndView modelAndView = new ModelAndView();

      return modelAndView;
   }
}
