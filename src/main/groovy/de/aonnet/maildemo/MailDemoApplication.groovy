package de.aonnet.maildemo

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CompileStatic
class MailDemoApplication {

    static void main(String[] args) {
        SpringApplication.run MailDemoApplication, args
    }
}
