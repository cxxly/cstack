package cn.org.once.cstack.controller;

import cn.org.once.cstack.dto.AboutResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/about")
public class AboutController {
    @Value("${api.version}")
    private String version;
    @Value("${api.timestamp}")
    private String timestamp;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> about() {
        AboutResource resource = new AboutResource(version, timestamp);
        return ResponseEntity.ok(resource);
    }
}
