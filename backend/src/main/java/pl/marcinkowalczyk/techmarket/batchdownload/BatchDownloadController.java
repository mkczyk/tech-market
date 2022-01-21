package pl.marcinkowalczyk.techmarket.batchdownload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/batch/offers")
public class BatchDownloadController {

    private final BatchDownloadService batchDownloadService;

    @GetMapping("download")
    public void explodeBatch(@RequestParam String name, @RequestParam Integer number) {
        batchDownloadService.download(name, number);
    }
}
