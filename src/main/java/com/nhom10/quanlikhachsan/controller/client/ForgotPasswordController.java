package com.nhom10.quanlikhachsan.controller.client;

import com.nhom10.quanlikhachsan.entity.User;
import com.nhom10.quanlikhachsan.services.UserNotFoundException;
import com.nhom10.quanlikhachsan.services.UserService;
import com.nhom10.quanlikhachsan.ultils.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.springframework.mail.javamail.JavaMailSender;


@Controller
public class ForgotPasswordController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "client/user/forgot_password_form";
    }
    public static String generateRandomString(int length) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, length);
    }
    @PostMapping("/forgot_password")
    public String showForgotPasswordForm(HttpServletRequest request, Model model) throws UserNotFoundException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        User user = userService.findUserByEmail(email);

        if(user == null){
            model.addAttribute("error", "Invalid email");
            return "client/user/forgot_password_form";
        }

        String token = generateRandomString(30);
        userService.updateResetPasswordToken(token,email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        sendEmail(email,resetPasswordLink);
        model.addAttribute("success", "We will be sending a reset password link to your email");
        return "client/user/forgot_password_form";
    }
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@Nhom10.com", "Nhom10 Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "client/user/message";
        }

        return "client/user/reset_password_form";
    }
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("error", "Invalid Token");
            return "client/user/message";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("success", "You have successfully changed your password.");
        }

        return "client/user/message";
    }
}

