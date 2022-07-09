package com.hot6.phopa.api.domain.domainName.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/admin/v1/test", produces = "application/json")
@RequiredArgsConstructor
public class DomainController {

    @PostMapping()
    public ResponseEntity<String> postSample() {
        return ResponseEntity.ok("post okay");
    }

    @GetMapping()
    public ResponseEntity<String> getSample() {
        return ResponseEntity.ok("get okay");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> onlyAdmin() {
        return ResponseEntity.ok("only Admin");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getSample(@PathVariable Long id) {
        return ResponseEntity.ok("get okay" + id);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<String> patchSample(@PathVariable Long id) {
        return ResponseEntity.ok("patch okay" + id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSample(@PathVariable Long id) {
        return ResponseEntity.ok("delete okay" + id);
    }
}
