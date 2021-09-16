package yte.intern.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yte.intern.model.Attendee;
import yte.intern.model.Event;
import yte.intern.model.TraceableEntity;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetEventDetailsResponse extends GetEventResponse {

    private Set<AttendeeInfo> attendees;

    private List<String> attendanceDates;
    private List<Integer> attendanceCounts;

    public GetEventDetailsResponse(Event event) {
        super(event);

        this.attendees = event.getAttendees().stream()
                .map(AttendeeInfo::new)
                .collect(Collectors.toSet());

        attendanceDates = new LinkedList<>();
        attendanceCounts = new LinkedList<>();

        Map<String, Integer> map = new TreeMap<>();

        event.getAttendees().stream()
                .map(TraceableEntity::getCreationDate)
                .map(date -> date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .forEach(dateString -> {
                    if (map.containsKey(dateString))
                        map.put(dateString, map.get(dateString) + 1);
                    else
                        map.put(dateString, 1);
                });

        map.forEach((key, value) -> {
            attendanceDates.add(key);
            attendanceCounts.add(value);
        });

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class AttendeeInfo {

        private Long userTc;
        private String email;
        private String name;
        private String surname;
        private String attendanceDate;

        public AttendeeInfo(Attendee attendee) {
            this.userTc = attendee.getUserProfile().getTcNo();
            this.email = attendee.getUserProfile().getEmail();
            this.name = attendee.getUserProfile().getName();
            this.surname = attendee.getUserProfile().getSurname();
            this.attendanceDate = attendee.getCreationDate()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
    }

}


