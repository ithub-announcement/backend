package ithub.announcementservice.backend.routes.draft.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ithub.announcementservice.backend.app.types.response.Response;
import ithub.announcementservice.backend.routes.domain.DTOs.AnnouncementDTO;
import ithub.announcementservice.backend.routes.draft.servicies.DraftService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequestMapping("/draft")
public class DraftController {
    private final DraftService _draftService;

    public DraftController(DraftService draftService) {
        _draftService = draftService;
    }

    @GetMapping("/")
    public Response GetAllDrafts(){
        try {
            return null;
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка запроса");
        }
    }

    @GetMapping("/{uuid}")
    public Response GetDraftByUUID(@PathVariable UUID uuid){
        try {
            return null;
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка запроса");
        }
    }

    @PostMapping("/save/{uuid}")
    public Response SaveDraft(@PathVariable UUID uuid, @RequestBody AnnouncementDTO announcementDTO){
        try {
            return null;
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка запроса");
        }
    }

    @PostMapping("/add")
    public Response AddDraft(@RequestBody AnnouncementDTO announcementDTO){
        try {
            return null;
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка запроса");
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public Response DeleteDraft(@PathVariable UUID uuid){
        try {
            return null;
        }catch (Exception err){
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка запроса");
        }
    }
}
