package org.sergiob.msvccourses.rest;

import org.sergiob.msvccourses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-users", url = "localhost:8001")
public interface IUserClient {
    @GetMapping("get/{id}")
    User getUser(@PathVariable Long id);
    @PostMapping("/add")
    User create(@RequestBody User user);

}
