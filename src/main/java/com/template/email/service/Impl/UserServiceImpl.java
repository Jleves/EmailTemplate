package com.template.email.service.Impl;

import com.template.email.controller.UserController;
import com.template.email.model.Confirmation;
import com.template.email.model.User;
import com.template.email.repository.ConfirmationRepository;
import com.template.email.repository.UserRepository;
import com.template.email.service.EmailService;
import com.template.email.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailServiceImple;

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        logger.info("Inicio de registro de usuario");
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exist");
        }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

        logger.info("Registro exitoso");
        //Envio de email
       // emailServiceImple.sendSimpleMailMessage(user.getName(), user.getEmail(),confirmation.getToken());
        //emailServiceImple.sendHtmlEmailWithEmbeddedFiles(user.getName(), user.getEmail(),confirmation.getToken());
        emailServiceImple.sendHtmlEmail(user.getName(), user.getEmail(),confirmation.getToken());
        //emailServiceImple.sendMimeMessageWithEmbeddedFiles(user.getName(), user.getEmail(),confirmation.getToken());
        //emailServiceImple.sendMimeMessageWithAttachments(user.getName(), user.getEmail(),confirmation.getToken());
        logger.info("Envio de email");
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        logger.info("Inicio Confirmacion");
        Confirmation confirmation= confirmationRepository.findByToken(token);
        User user= userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        logger.info("Enabled User true");
        userRepository.save(user);
        confirmationRepository.delete(confirmation);

        logger.info("Verification email ok");
        return Boolean.TRUE;
    }
}
