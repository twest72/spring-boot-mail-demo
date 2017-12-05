package de.aonnet.maildemo

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import org.springframework.integration.annotation.MessageEndpoint
import org.springframework.integration.annotation.ServiceActivator

import javax.mail.internet.MimeMessage

@Log4j
@MessageEndpoint
@CompileStatic
class MailListener {

    @ServiceActivator
    void processMail(MimeMessage mail) {
        String contentType = mail.getContentType()
        String to = mail.getHeader('To').join(', ')
        String date = mail.getHeader('Date').join(', ')
        String subject = mail.getHeader('Subject').join(', ')
        String from = mail.getHeader('From').join(', ')

        String content = mail.inputStream.getText('UTF-8')
        log.info "Mail $date from $from to $to subject: '$subject' (content type: $contentType)"
        log.info "Mail message: '$content'"
    }
}
