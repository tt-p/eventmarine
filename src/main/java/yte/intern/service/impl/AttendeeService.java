package yte.intern.service.impl;

import org.springframework.stereotype.Service;
import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Message;
import yte.intern.model.Attendee;
import yte.intern.model.Event;
import yte.intern.model.UserProfile;
import yte.intern.repository.AttendeeRepository;
import yte.intern.repository.EventRepository;
import yte.intern.repository.UserProfileRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final UserProfileRepository userProfileRepository;
    private final EventRepository eventRepository;

    public AttendeeService(AttendeeRepository attendeeRepository, UserProfileRepository userProfileRepository, EventRepository eventRepository) {
        this.attendeeRepository = attendeeRepository;
        this.userProfileRepository = userProfileRepository;
        this.eventRepository = eventRepository;
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public List<Attendee> getAllAttendeesByUserTc(Long userTc) {

        UserProfile userProfile = userProfileRepository.findByTcNo(userTc).orElseThrow(
                () -> new EntityNotFoundException("User Not Found.")
        );

        return getAllAttendeesByUserProfile(userProfile);
    }

    public List<Attendee> getAllAttendeesByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event Not Found.")
        );

        return getAllAttendeesByEvent(event);
    }

    public Attendee getByUserTcAndEventId(Long userTc, Long eventId) {

        UserProfile userProfile = userProfileRepository.findByTcNo(userTc).orElseThrow(
                () -> new EntityNotFoundException("User Not Found.")
        );

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event Not Found.")
        );

        return getByUserProfileAndEvent(userProfile, event);
    }

    public List<Attendee> getAllAttendeesByUserProfile(UserProfile userProfile) {
        return attendeeRepository.findAllByUserProfile(userProfile);
    }

    public List<Attendee> getAllAttendeesByEvent(Event event) {
        return attendeeRepository.findAllByEvent(event);
    }

    public Attendee getByUserProfileAndEvent(UserProfile userProfile, Event event) {
        return attendeeRepository.findByUserProfileAndEvent(userProfile, event);
    }

    @Transactional
    public MessageResponse createAttendee(Long userTc, Long eventId) {

        UserProfile userProfile = userProfileRepository.findByTcNo(userTc).orElseThrow(
                () -> new EntityNotFoundException("User Not Found.")
        );

        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event Not Found.")
        );

        if (attendeeRepository.existsByUserProfileAndEvent(userProfile, event))
            return new MessageResponse(Message.ATTENDANCE_ALREADY_ATTENDED);


        Integer quota = event.getQuota();
        Integer attendeeCount = attendeeRepository.countAllByEvent(event);

        if (attendeeCount >= quota)
            return new MessageResponse(Message.ATTENDANCE_OVER_QUOTA);

        Attendee attendee = new Attendee(userProfile, event);

        Attendee savedAttendee = attendeeRepository.save(attendee);

        if (savedAttendee.getId() > 0)
            return new MessageResponse<>(Message.SUCCESS_ATTENDANCE, savedAttendee);
        else
            return new MessageResponse<>(Message.ERROR_ATTENDANCE);
    }

}
