package ithub.announcementservice.backend.routes.moderation.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.routes.domain.DTOs.AnnouncementDTO;
import ithub.announcementservice.backend.routes.moderation.servicies.moderationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/draft")
public class moderationController {
    private final moderationService _moderationService;

    public moderationController(moderationService moderationService) {
        _moderationService = moderationService;
    }


}
