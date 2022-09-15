package org.sergiob.mscv.usuarios.mscv.usuarios.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "host.docker.internal:8002")
public interface ICourseClient {
    @DeleteMapping("/deleteStudent/{id}")
    void deleteStudent(@PathVariable Long id);
}
