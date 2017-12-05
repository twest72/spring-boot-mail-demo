package de.aonnet.maildemo

import groovy.transform.CompileStatic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.mail.ImapIdleChannelAdapter
import org.springframework.integration.mail.ImapMailReceiver
import org.springframework.messaging.MessageChannel

import static java.net.URLEncoder.encode

@Configuration
@CompileStatic
class ImapConfiguration {

    @Bean
    ImapMailReceiver mailReceiver() {
        String userName = 'jenkins@aonnet.de'
        String password = '******************'

        String userNameEncoded = encode(userName, 'UTF-8')
        String passwordEncoded = encode(password, 'UTF-8')
        ImapMailReceiver mailReceiver = new ImapMailReceiver(
                "imaps://${userNameEncoded}:${passwordEncoded}@imap.1und1.de:993/INBOX")

        mailReceiver.javaMailProperties = javaMailProperties()
        mailReceiver.shouldDeleteMessages = false
        mailReceiver.shouldMarkMessagesAsRead = false

        return mailReceiver
    }

    @Bean
    ImapIdleChannelAdapter mailAdapter(ImapMailReceiver mailReceiver) {
        return new ImapIdleChannelAdapter(mailReceiver)
    }

    @Bean
    MessageChannel emails() {
        return new DirectChannel()
    }

    @Bean
    IntegrationFlow integrationFlow(MailListener endpoint, ImapIdleChannelAdapter mailAdapter) {
        IntegrationFlows
                .from(mailAdapter)
                .channel(emails())
                .handle(endpoint)
                .get()
    }

    @SuppressWarnings("GrMethodMayBeStatic")
    private Properties javaMailProperties() {
        Properties javaMailProperties = new Properties()

        javaMailProperties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        javaMailProperties.setProperty("mail.imap.socketFactory.fallback", "false")
        javaMailProperties.setProperty("mail.store.protocol", "imaps")
        javaMailProperties.setProperty("mail.debug", "false")

        return javaMailProperties
    }
}
