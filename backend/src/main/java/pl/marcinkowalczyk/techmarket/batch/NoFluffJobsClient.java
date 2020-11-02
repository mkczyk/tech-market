package pl.marcinkowalczyk.techmarket.batch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "NoFluffJobs", url = "${no-fluff-jobs-service.url}")
public interface NoFluffJobsClient {

    @GetMapping("/todos/1")
    String getBatch();
}
