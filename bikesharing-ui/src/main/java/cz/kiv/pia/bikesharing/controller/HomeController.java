package cz.kiv.pia.bikesharing.controller;

import cz.kiv.pia.bikesharing.domain.Location;
import cz.kiv.pia.bikesharing.domain.Stand;
import cz.kiv.pia.bikesharing.service.StandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final StandService standService;

    public HomeController(StandService standService) {
        this.standService = standService;
    }

    @GetMapping("/")
    public String home(Model model) {
        var standVOs = standService.getAll().stream()
                .map(StandVO::new)
                .toList();

        model.addAttribute("stands", standVOs);

        return "home";
    }

    private record StandVO(
            String name,
            LocationVO location
    ) {
        public StandVO(Stand stand) {
            this(stand.getName(), new LocationVO(stand.getLocation()));
        }
    }

    private record LocationVO(
            Double longitude,
            Double latitude
    ) {
        public LocationVO(Location location) {
            this(location.getLongitude(), location.getLatitude());
        }
    }
}
