package com.mkrasikoff.figmacomparator.daos;

import com.mkrasikoff.figmacomparator.exceptions.ProjectNotFoundException;
import com.mkrasikoff.figmacomparator.models.Project;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(Lifecycle.PER_CLASS)
class ProjectsDaoTest {

   private static final String PROJECT_KEY = "Some project key";
   private static final String PROJECT_NAME = "Some project name";

   private final ProjectsDao projectsDao = new ProjectsDao();

   @BeforeAll
   public void setUp() {
      Project projectOne = new Project("1", "First");
      Project projectSecond = new Project("2", "Second");
      Project projectThird = new Project("3", "Third");

      projectsDao.addProject(projectOne);
      projectsDao.addProject(projectSecond);
      projectsDao.addProject(projectThird);
   }

   @Test
   void addProject_newProjectIsPutToDao_projectSuccessfullyAdded() {

      // GIVEN
      Project newProject = new Project(PROJECT_KEY, PROJECT_NAME);

      // WHEN
      projectsDao.addProject(newProject);

      // THEN
      Project actualProject = projectsDao.getProjectViaKey(PROJECT_KEY);
      assertEquals(newProject, actualProject);
   }

   @Test
   void addProject_emptyProjectIsPutToDao_projectSuccessfullyAddedWithEmptyKey() {

      // GIVEN
      Project newProject = new Project();

      // WHEN
      projectsDao.addProject(newProject);

      // THEN
      Project actualProject = projectsDao.getProjectViaKey("");
      assertEquals(newProject, actualProject);
   }

   @Test
   void getProjectViaKey_projectWithThisKeyExists_projectSuccessfullyReturned() {

      // GIVEN
      String key = "1";

      // WHEN
      Project projectViaKey = projectsDao.getProjectViaKey(key);

      // THEN
      assertEquals(key, projectViaKey.getKey());
   }

   @Test
   void getProjectViaKey_projectWithThisKeyDoesNotExist_projectNotFoundExceptionWasThrown() {

      // GIVEN
      String key = "Strange key";

      // WHEN
      ProjectNotFoundException exc = assertThrows(ProjectNotFoundException.class,
              () -> projectsDao.getProjectViaKey(key));

      // THEN
      assertInstanceOf(ProjectNotFoundException.class, exc);
      assertEquals("Project not found via key.", exc.getMessage());
   }

   @Test
   void getProjectViaName_projectWithThisNameExists_projectSuccessfullyReturned() {

      // GIVEN
      String name = "First";

      // WHEN
      Project projectViaKey = projectsDao.getProjectViaName(name);

      // THEN
      assertEquals(name, projectViaKey.getName());
   }

   @Test
   void getProjectViaName_projectWithThisNameDoesNotExist_projectNotFoundExceptionWasThrown() {

      // GIVEN
      String name = "Strange name";

      // WHEN
      ProjectNotFoundException exc = assertThrows(ProjectNotFoundException.class,
              () -> projectsDao.getProjectViaName(name));

      // THEN
      assertInstanceOf(ProjectNotFoundException.class, exc);
      assertEquals("Project not found via name.", exc.getMessage());
   }
}
