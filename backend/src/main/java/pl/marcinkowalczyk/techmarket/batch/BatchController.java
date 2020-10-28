package pl.marcinkowalczyk.techmarket.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchService batchService;

    @GetMapping("run")
    public void runBatch(@RequestParam String name) {
        batchService.runBatch(name);
    }
}
