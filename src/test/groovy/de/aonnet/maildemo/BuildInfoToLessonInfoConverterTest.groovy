package de.aonnet.maildemo

import org.junit.Test

class BuildInfoToLessonInfoConverterTest {

    BuildInfoToLessonInfoConverter buildInfoToLessonInfoConverter = new BuildInfoToLessonInfoConverter()

    @Test
    void 'should convert empty BuildInfo'() {

        LessonInfo lessonInfo = buildInfoToLessonInfoConverter.convert new BuildInfo()

        assert lessonInfo
        assert !lessonInfo.success
    }

    @Test
    void 'should convert user name'() {

        LessonInfo lessonInfo = buildInfoToLessonInfoConverter.convert new BuildInfo(commits: [new CommitInfo(author: 'twest')])

        assert lessonInfo
        assert !lessonInfo.success
        assert lessonInfo.userName == 'twest'
    }

    @Test
    void 'should convert user email'() {

        LessonInfo lessonInfo = buildInfoToLessonInfoConverter.convert new BuildInfo(commits: [new CommitInfo(authorEmail: 'twest@test.de')])

        assert lessonInfo
        assert !lessonInfo.success
        assert lessonInfo.userEmail == 'twest@test.de'
    }
}