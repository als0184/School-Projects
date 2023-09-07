package edu.auburn.cpsc4970.webapp5a;

import edu.auburn.cpsc4970.auth.AULoginException;
import edu.auburn.cpsc4970.auth.AuthProviderFactory;
import edu.auburn.cpsc4970.auth.AuthProviderInterface;
import edu.auburn.cpsc4970.auth.UserSession;
import edu.auburn.cpsc4970.database.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class WebPageController {
    /**
     * Logging class
     */
    private static final Logger logger = LoggerFactory.getLogger(WebPageController.class);

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response,
                                  HttpSession session) {
      //  response.setHeader("Cache-Control", "no-cache");
     //   response.setHeader("Content-Security-Policy", "frame-src 'none'; default-src 'self'; frame-ancestors 'self';");
     //   response.setHeader("X-Content-Type-Options", "nosniff");

    }

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {

        UserSession session = (UserSession) httpSession.getAttribute("user-session");
        if (session == null)  {
            logger.info("No User session, sending to login.");
            return "login";
        }
        return "index";
    }

    /**
     * Send to login form.
     */
    @GetMapping("/login")
    public String login(Model model, HttpSession httpSession) {
        return "login";
    }

    /**
     * Logout by invalidating the http session and sending to login form.
     */
    @GetMapping("/logout")
    public String lougout(Model model, HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    /**
     * Handle the login request.
     */
    @PostMapping("/login_handler")
    public String loginHandler(Model model,
                               @RequestParam(name="uname") String userName,
                               @RequestParam(name="psw") String password,
                               HttpSession httpSession) {

        logger.info("Attempting to login: {}, {}",userName,password);

        try {
            AuthProviderInterface authProviderInterface = AuthProviderFactory.getAuthProvider();
            UserSession session = authProviderInterface.login(userName,password);
            httpSession.setAttribute("user-session", session);
            httpSession.setMaxInactiveInterval(30);


        } catch (AULoginException e) {
            logger.error("Login Exception: {}",e.toString());
            model.addAttribute("message","Login failed.");
            return "login";
        }
        return "index";
    }

}

