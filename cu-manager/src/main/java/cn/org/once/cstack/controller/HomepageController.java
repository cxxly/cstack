package cn.org.once.cstack.controller;

import cn.org.once.cstack.dto.HomepageResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nicolas on 12/12/2016.
 */
@Controller
@RequestMapping("/homepage")
public class HomepageController {

    @Value("${jenkins.domain : jenkins.cloudunit.dev}")
    private String jenkins;

    @Value("${gitlab.domain : gitlab.cloudunit.dev}")
    private String gitlab;

    @Value("${nexus.domain : nexus.cloudunit.dev}")
    private String nexus;

    @Value("${kibaba.domain : kibana.cloudunit.dev}")
    private String kibana;

    @Value("${sonar.domain: sonar.cloudunit.dev}")
    private String sonar;

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public ResponseEntity<?> listFriends() {
        HomepageResource resource = new HomepageResource(jenkins, gitlab, kibana, nexus, sonar);
        return ResponseEntity.ok(resource);
    }

}
