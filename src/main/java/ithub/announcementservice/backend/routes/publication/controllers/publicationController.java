package ithub.announcementservice.backend.routes.publication.controllers;

import ithub.announcementservice.backend.routes.publication.servicies.publicationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class publicationController {
    private final publicationService _publicationService;

    public publicationController(publicationService publicationService) {
        _publicationService = publicationService;
    }
}
