package pl.marcinkowalczyk.techmarket.batchoffer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/batch")
public class BatchExplodeController {

    private final BatchExplodeService batchExplodeService;

    @GetMapping("explode")
    public void explodeBatch(@RequestParam String name) {
        batchExplodeService.explode(name);
    }
}
