package de.aonnet.maildemo

import org.junit.Test

class MailParserTest {

    static String someMailContent = """GENERAL INFO

BUILD SUCCESS
Build URL: http://localhost:8080/jenkins/job/angular-plain-test/13/
Project: angular-plain-test
Date of build: Tue, 28 Nov 2017 09:21:21 +0100
Build duration: 1 Minute 33 Sekunden

CHANGE SET

  \t Revision 4b065e4da0c7b76f709e6de1a38ea3112261b283 by twestphal4.ext: test commit no 6.1
  \t Author Email: twestphal4.ext@gema.de

\t change: edit README.md

  \t Revision f0ebbdb58656970a0f20a391ca19ab2660ba40ba by twestphal4.ext: test commit no 6.2
  \t Author Email: twestphal4.ext@gema.de

\t change: edit README.md

PARSE DATA:
CS_JSON_START{"url":"http://localhost:8080/jenkins/job/angular-plain-test/13/","cs":[{"revesion":"4b065e4da0c7b76f709e6de1a38ea3112261b283","authorEmail":"twestphal4.ext@gema.de","author":"twestphal4.ext","msgAnnotated":"test commit no 6.1"},{"revesion":"f0ebbdb58656970a0f20a391ca19ab2660ba40ba","authorEmail":"twestphal4.ext@gema.de","author":"twestphal4.ext","msgAnnotated":"test commit no 6.2"}]}CS_JSON_END"""


    @Test
    void 'should create BuildInfo from Mail Content'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf(someMailContent)

        assert buildInfo.url == 'http://localhost:8080/jenkins/job/angular-plain-test/13/'
    }

    @Test
    void 'should ignore missing tag'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf('scheisse')

        assert buildInfo.url == null
    }

    @Test
    void 'should ignore second tag'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf("""
CS_JSON_START{"url":"http://test","cs":[]}CS_JSON_END
CS_JSON_START{"url":"00000000000000000000000","cs":[{]}CS_JSON_END
CS_JSON_START{"url":"00000000000000000000000","cs":[{]}CS_JSON_END
""")

        assert buildInfo.url == 'http://test'
    }

    @Test
    void 'should ignore broken json'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf("""CS_JSON_START{"url":"http://test","cs":[{]}CS_JSON_END""")

        assert buildInfo.url == null
    }

    @Test
    void 'should ignore empty content'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf('')

        assert buildInfo.url == null
    }

    @Test
    void 'should ignore blank content'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf('      ')

        assert buildInfo.url == null
    }

    @Test
    void 'should ignore null content'() {

        MailParser mailParser = new MailParser()
        BuildInfo buildInfo = mailParser.buildInfoOf(null)

        assert buildInfo.url == null
    }
}