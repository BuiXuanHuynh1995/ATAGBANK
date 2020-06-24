package com.atag.atagbank.controller;

import com.atag.atagbank.model.ConfirmationToken;
import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.EmailSenderService;
import com.atag.atagbank.service.confirmationToken.IConfirmationTokenService;
import com.atag.atagbank.model.Role;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@SessionAttributes("currentUser")
public class MainController {

    @ModelAttribute("currentUser")
    public MyUser getCurrentUser() {
        return new MyUser();
    }

    @Autowired
    MyUserService myUserService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private IConfirmationTokenService confirmationTokenService;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        return new ModelAndView("index");
    }

    @GetMapping("/login-form")
    public ModelAndView getLoginForm(@ModelAttribute MyUser currentUser) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

   @PostMapping("/login-form")
    public ModelAndView login(@ModelAttribute MyUser currentUser, HttpSession session) {
        MyUser loginUser = myUserService.findByUserName(currentUser.getUsername());
        if (loginUser != null && currentUser.getPassword().equals(loginUser.getPassword())) {
            if (loginUser.isEnabled()) {
                session.setAttribute("currentUser", loginUser);
                session.setAttribute("currentUserName", loginUser.getName());
                ModelAndView modelAndView = new ModelAndView("index");
                modelAndView.addObject("currentUser", loginUser);
                return modelAndView;
            } else {
                return new ModelAndView("login", "deactivated", "Account is deactivated. Please contact Admin to active!");
            }
        }
        return new ModelAndView("login", "notFound", "Wrong username or password!");
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        MyUser user = new MyUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid MyUser user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        MyUser userExists = myUserService.findByUserName(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }

        MyUser existingUser = myUserService.findByEmail(user.getEmail());
        if(existingUser != null)
        {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (bindingResult.hasFieldErrors()) {
            modelAndView.setViewName("registration");
        } else {
            myUserService.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenService.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("huynhxuanbui@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("email", user.getEmail());
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.setViewName("successfulRegisteration");

        }
        return modelAndView;

    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            MyUser user = token.getUser();
            user.setEnabled(true);
            myUserService.saveUser(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }

}