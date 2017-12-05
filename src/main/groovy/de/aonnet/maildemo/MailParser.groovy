package de.aonnet.maildemo

import groovy.json.JsonException
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j

import static org.apache.commons.lang3.StringUtils.*

@Log4j
@CompileStatic
class MailParser {

    BuildInfo buildInfoOf(String mailContent) {
        String buildJsonString = contentOfTag 'CS_JSON', mailContent
        log.info "Mail buildJson: $buildJsonString"

        if (isBlank(buildJsonString)) {
            return new BuildInfo()
        }

        Map buildJson = buildInfoMapOf buildJsonString
        BuildInfo buildInfo = buildInfoOfMap buildJson
        log.info "Mail buildInfo: $buildInfo"

        return buildInfo
    }

    private BuildInfo buildInfoOfMap(Map buildJson) {
        BuildInfo buildInfo = new BuildInfo(url: buildJson.url as String)

        buildJson.cs.each { Map changeSet ->
            buildInfo.commits << new CommitInfo(
                    revesion: changeSet.revesion as String,
                    author: changeSet.author as String,
                    authorEmail: changeSet.authorEmail as String,
                    msgAnnotated: changeSet.msgAnnotated as String
            )
        }
        return buildInfo
    }

    private Map buildInfoMapOf(String buildJsonString) {
        try {
            return new JsonSlurper().parseText(buildJsonString) as Map
        } catch (JsonException e) {
            log.error "Broken JSON: '$buildJsonString'"
            return [:]
        }
    }

    private String contentOfTag(String tag, String content) {
        String tagContent = trimToEmpty substringBetween(content, "${tag}_START", "${tag}_END")
        log.info "Mail Tag $tag: '$tagContent'"
        return tagContent
    }

}
