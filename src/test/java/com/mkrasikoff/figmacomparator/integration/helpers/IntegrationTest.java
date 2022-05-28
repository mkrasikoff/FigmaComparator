package com.mkrasikoff.figmacomparator.integration.helpers;

import com.mkrasikoff.figmacomparator.FigmaComparatorApplication;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DirtiesContext
@PropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FigmaComparatorApplication.class,
        properties = "exception-messages"
)
public abstract class IntegrationTest {

   @Value("${testing.token}")
   protected String token;
}
