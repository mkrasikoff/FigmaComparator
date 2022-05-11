package com.mkrasikoff.figmacomparator.daos;

import com.mkrasikoff.figmacomparator.exceptions.ProjectNotFoundException;
import com.mkrasikoff.figmacomparator.models.Project;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProjectsDao {

   private final Map<String, Project> projects;

   ProjectsDao() {
      this.projects = new HashMap<>();
   }

   public void addProject(Project project) {
      projects.put(project.getKey(), project);
   }

   public Project getProjectViaKey(String key) {
      for (Map.Entry<String, Project> entry : projects.entrySet()) {
         if(entry.getKey().equals(key)) return entry.getValue();
      }
      throw new ProjectNotFoundException("Project not found via key.");
   }

   public Project getProjectViaName(String name) {
      for (Map.Entry<String, Project> entry : projects.entrySet()) {
         if(entry.getValue().getName().equals(name)) return entry.getValue();
      }
      throw new ProjectNotFoundException("Project not found via name.");
   }
}
