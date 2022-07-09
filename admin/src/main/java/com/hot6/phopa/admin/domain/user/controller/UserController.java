package com.hot6.phopa.admin.domain.user.controller;

import com.hot6.phopa.admin.domain.user.service.UserAdminService;
import com.hot6.phopa.core.domain.user.model.dto.UserDTO;
import com.hot6.phopa.core.domain.user.model.entity.UserEntity;
import com.hot6.phopa.core.domain.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/admin/v1/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {

    private final UserAdminService userService;

    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO userDto
    ) {
        UserEntity userEntity = userService.createUser(userDto);
        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @GetMapping()
    public ResponseEntity<String> getSample() {
        return ResponseEntity.ok("get okay");
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
