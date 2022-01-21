package pl.marcinkowalczyk.techmarket.nofluffjobs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "NoFluffJobs", url = "${no-fluff-jobs-service.url}")
public interface NoFluffJobsClient {

    @GetMapping("/posting")
    String getBatch();

    @GetMapping("/posting/{identifier}")
    String getOffer(@PathVariable("identifier") String identifier);
}
