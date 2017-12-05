package de.aonnet.maildemo

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString
@CompileStatic
class LessonInfo {

    String lessonId
    String userEmail
    String userName
    boolean success
}
