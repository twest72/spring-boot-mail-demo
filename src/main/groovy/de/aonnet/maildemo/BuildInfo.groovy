package de.aonnet.maildemo

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString
@CompileStatic
class BuildInfo {

    String url
    List<CommitInfo> commits = []
}
