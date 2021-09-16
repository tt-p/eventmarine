package yte.intern.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Message;
import yte.intern.dto.CreateEventRequest;
import yte.intern.dto.GetEventDetailsResponse;
import yte.intern.dto.GetEventResponse;
import yte.intern.dto.UpdateEventRequest;
import yte.intern.model.Event;
import yte.intern.service.IEventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INTERNAL')")
    public ResponseEntity<List<GetEventResponse>> getAllEvents() {
        return ResponseEntity.ok().body(
                eventService.getAllEvents()
                        .stream()
                        .map(GetEventResponse::new)
                        .toList()
        );
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyAuthority('INTERNAL')")
    public ResponseEntity<List<GetEventDetailsResponse>> getAllEventsWithDetails() {
        return ResponseEntity.ok().body(
                eventService.getAllEvents()
                        .stream()
                        .map(GetEventDetailsResponse::new)
                        .toList()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('INTERNAL')")
    public ResponseEntity<List<String>> createEvent(@Valid @RequestBody CreateEventRequest createEventRequest) {
        Event event = createEventRequest.toEntity();
        eventService.createEvent(event);
        return ResponseEntity.ok().body(
                List.of(Message.CREATED_EVENT.getValue())
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('INTERNAL')")
    public ResponseEntity<List<String>> updateEvent(@PathVariable final Long id, @Valid @RequestBody UpdateEventRequest updateStudentRequest) {

        MessageResponse messageResponse = eventService.updateByIdCheckEventStarted(id, updateStudentRequest.toEntity());

        if (messageResponse.getMessage() == Message.UPDATED_EVENT) {
            return ResponseEntity.ok().body(
                    List.of(messageResponse.getMessage().getValue())
            );
        } else {
            return ResponseEntity.badRequest().body(
                    List.of(messageResponse.getMessage().getValue() + ": Event already started")
            );
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('INTERNAL')")
    public ResponseEntity<List<String>> deleteEvent(@PathVariable Long id) {

        MessageResponse messageResponse = eventService.deleteByIdCheckEventStarted(id);

        if (messageResponse.getMessage() == Message.DELETED_EVENT) {
            return ResponseEntity.ok().body(
                    List.of(messageResponse.getMessage().getValue())
            );
        } else {
            return ResponseEntity.badRequest().body(
                    List.of(messageResponse.getMessage().getValue() + ": Event already started")
            );
        }
    }

}
