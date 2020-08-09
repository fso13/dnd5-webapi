package ru.drudenko.dnd5.webapi.impl.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.drudenko.dnd5.webapi.dto.user.ResetPasswordDto;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;
import ru.drudenko.dnd5.webapi.service.NotificationService;

@Service
@RequiredArgsConstructor
class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${ru.drudenko.dnd5.webapi.contextPath:dnd5-webapi.herokuapp.com}")
    private String contextPath;

    @Override
    public void sendResetPassword(ResetPasswordDto resetPasswordDto) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(resetPasswordDto.getUserDto().getEmail());
            messageHelper.setSubject("Востановление пароля на сайте dnd5-webapi.herokuapp.com");

            Context context = new Context();
            context.setVariable("url", contextPath + "/changePassword?id=" +
                    resetPasswordDto.getUserDto().getId() + "&token=" + resetPasswordDto.getTokenId());
            messageHelper.setText(templateEngine.process("email/resetPassword", context), true);
        };
        mailSender.send(messagePreparator);
    }


    @Override
    public void sendRegistration(UserDto userDto) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(userDto.getEmail());
            messageHelper.setSubject("Регистрация на сайте dnd5-webapi.herokuapp.com");

            Context context = new Context();
            context.setVariable("username", userDto.getUsername());
            context.setVariable("url", contextPath + "/confirm/" + userDto.getTokenRegistration());
            messageHelper.setText(templateEngine.process("email/registration", context), true);
        };
        mailSender.send(messagePreparator);
    }
}
