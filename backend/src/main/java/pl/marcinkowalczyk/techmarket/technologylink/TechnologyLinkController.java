package pl.marcinkowalczyk.techmarket.technologylink;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/batch/offers")
public class TechnologyLinkController {

    private final TechnologyLinkService technologyLinkService;

    @GetMapping("link")
    public void link(@RequestParam String name) {
        technologyLinkService.link(name);
    }
}
