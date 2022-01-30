package pl.marcinkowalczyk.techmarket.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.marcinkowalczyk.techmarket.presentation.dto.GraphPresentationDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/batch/presentation")
public class PresentationController {

    private final PresentationService presentationService;

    @GetMapping()
    public GraphPresentationDto getPresentation(@RequestParam String name) {
        return presentationService.getPresentation(name);
    }
}
