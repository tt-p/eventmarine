package yte.intern.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Message;
import yte.intern.dto.GetEventResponse;
import yte.intern.model.Attendee;
import yte.intern.service.IEventService;
import yte.intern.service.impl.AttendeeService;
import yte.intern.service.impl.EmailService;
import yte.intern.util.IOUtils;
import yte.intern.util.QRCodeUtil;

import java.awt.image.BufferedImage;
import java.util.List;

@RestController()
public class AttendeeController {

    private final IEventService eventService;
    private final AttendeeService attendeeService;
    private final EmailService emailService;

    public AttendeeController(IEventService eventService, AttendeeService attendeeService, EmailService emailService) {
        this.eventService = eventService;
        this.attendeeService = attendeeService;
        this.emailService = emailService;
    }

    @GetMapping("/api/attendee/{userTc}")
    @PreAuthorize("hasAuthority('EXTERNAL')")
    public ResponseEntity<List<GetEventResponse>> getCurrentEventsByUserTc(@PathVariable final Long userTc) {
        return ResponseEntity
                .ok()
                .body(
                        eventService.getCurrentEventsByUserTc(userTc).stream()
                                .map(GetEventResponse::new)
                                .toList()
                );
    }

    @GetMapping("/api/attended/{userTc}")
    @PreAuthorize("hasAuthority('EXTERNAL')")
    public ResponseEntity<List<GetEventResponse>> getAttendedEventsByUserTc(@PathVariable final Long userTc) {
        return ResponseEntity
                .ok()
                .body(
                        eventService.getAttendedEventsByUserTc(userTc).stream()
                                .map(GetEventResponse::new)
                                .toList()
                );
    }

    @GetMapping(value = "/api/attendee/qrcode/{eventId}/{userTc}")
    @PreAuthorize("hasAuthority('EXTERNAL')")
    public ResponseEntity<byte[]> getQrCode(@PathVariable final Long eventId, @PathVariable final Long userTc) {

        Attendee attendee = attendeeService.getByUserTcAndEventId(userTc, eventId);

        BufferedImage bufferedImage = QRCodeUtil.getQRCode(attendee);

        return ResponseEntity.ok().body(IOUtils.toByteArray(bufferedImage, "png"));
    }

    @PostMapping("/api/attend/{eventId}/{userTc}")
    @PreAuthorize("hasAuthority('EXTERNAL')")
    public ResponseEntity<List<String>> attend(@PathVariable final Long eventId, @PathVariable final Long userTc) {

        MessageResponse messageResponse = attendeeService.createAttendee(userTc, eventId);

        if (messageResponse.getMessage() == Message.SUCCESS_ATTENDANCE) {

            Attendee attendee = (Attendee) messageResponse.getT();
            emailService.sendEmailToAttendee(attendee);

            return ResponseEntity
                    .ok()
                    .body(List.of(messageResponse.getMessage().getValue()));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(List.of(messageResponse.getMessage().getValue()));
        }
    }

}
