package de.aonnet.maildemo

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString
@CompileStatic
class CommitInfo {
    String revesion
    String authorEmail
    String author
    String msgAnnotated
}
