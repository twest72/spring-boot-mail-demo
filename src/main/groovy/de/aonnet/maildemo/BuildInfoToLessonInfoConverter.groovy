package de.aonnet.maildemo

import groovy.transform.CompileStatic
import org.springframework.core.convert.converter.Converter

@CompileStatic
class BuildInfoToLessonInfoConverter implements Converter<BuildInfo, LessonInfo> {

    @Override
    LessonInfo convert(BuildInfo buildInfo) {
        LessonInfo lessonInfo = new LessonInfo()

        addsLastCommitData(buildInfo, lessonInfo)

        return lessonInfo
    }

    private void addsLastCommitData(BuildInfo buildInfo, LessonInfo lessonInfo) {
        if (!buildInfo?.commits) {
            return
        }

        CommitInfo lastCommit = buildInfo.commits.last()
        if (lastCommit) {
            lessonInfo.userName = lastCommit.author
            lessonInfo.userEmail = lastCommit.authorEmail
        }
    }
}
