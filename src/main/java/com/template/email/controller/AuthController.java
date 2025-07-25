package com.template.email.controller;

import com.template.email.Exception.ResourceNotFoundException;

import com.template.email.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import java.time.LocalDateTime;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;




    @GetMapping("/check")
    public ModelAndView confirmUserAccount(@RequestParam("token") String token) throws ResourceNotFoundException {

        Boolean isSuccess = userService.verifyToken(token);
          boolean  verificar = true;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Confirmacion"); // Nombre de la plantilla HTML
        modelAndView.addObject("verificar", verificar); // Pasar datos a la plantilla si es necesario
        modelAndView.addObject("timeStamp", LocalDateTime.now().toString()); // Ejemplo de pasar datos a la plantilla

        return modelAndView;
    }


}
