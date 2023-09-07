package edu.auburn.cpsc4970.webapp5a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class WebAPIController {
   /**
    * Logging class
    */
   private static final Logger logger = LoggerFactory.getLogger(WebAPIController.class);

   @ModelAttribute
   public void setResponseHeader(HttpServletResponse response,
                                 HttpSession session) {
      // Set and Header values here.
   response.setHeader("Content-Security-Policy", "default-src 'none'; script-src 'self'; connect-src 'self'; img-src 'self'; style-src 'self';base-uri 'self';form-action 'self'");
   }

   @GetMapping(path="/getapi", produces="application/json")
   public String handleGetAPI(Model model,
                              HttpSession httpSession,
                              HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse) {
      logger.info("Received from request: {}",httpServletRequest.getQueryString());
      httpServletResponse.setContentType("application/json");
      return "{}";
   }

   @GetMapping(path="/postapi", produces="application/json")
   public String handlePostAPI(Model model,
                              HttpSession httpSession,
                              HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse) {
      try {
         logger.info("Received from request: {}",httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
      } catch (IOException e) {
         logger.error("Error reading request body {}",e);
      };
      httpServletResponse.setContentType("application/json");
      return "{}";
   }

}
