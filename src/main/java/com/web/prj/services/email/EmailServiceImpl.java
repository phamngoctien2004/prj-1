package com.web.prj.services.email;

import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender mailSender;

    @Async("emailExecutor")
    public void sendEmail(String email, String content, String subject) {
        try{
            // tạo đối tượng cho phép gửi email nhiều định dạng -> tạo từ mailSender (theo cấu hình gốc)
            MimeMessage message = mailSender.createMimeMessage();

            // tạo messHelper giúp tạo nội dung của email
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom("tienolympia2020@gmail.com");
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content,true);


            mailSender.send(message);
        }catch (MessagingException ex){
            throw new AppException(ErrorCode.SEND_EMAIL_FAILED);
        }

    }
}
