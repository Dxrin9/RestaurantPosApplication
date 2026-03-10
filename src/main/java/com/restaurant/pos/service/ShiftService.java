package com.restaurant.pos.service;

import com.restaurant.pos.entity.Shift;
import com.restaurant.pos.entity.User;
import com.restaurant.pos.repository.ShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShiftService {

    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    /** Start a new shift for user. Throws if already has active shift. */
    public Shift startShift(User user) {
        Optional<Shift> active = shiftRepository.findByUserAndEndTimeIsNull(user);
        if (active.isPresent()) {
            throw new IllegalStateException("Shift already started for " + user.getUsername());
        }
        Shift shift = Shift.builder()
                .user(user)
                .shiftDate(LocalDate.now())
                .startTime(LocalDateTime.now())
                .build();
        return shiftRepository.save(shift);
    }

    /** End the active shift for user, calculate worked minutes. */
    public Shift endShift(User user) {
        Shift shift = shiftRepository.findByUserAndEndTimeIsNull(user)
                .orElseThrow(() -> new IllegalStateException("No active shift for " + user.getUsername()));

        LocalDateTime end = LocalDateTime.now();
        shift.setEndTime(end);
        shift.setWorkedMinutes((int) Duration.between(shift.getStartTime(), end).toMinutes());
        return shiftRepository.save(shift);
    }

    /** Get active shift for user (null if not started) */
    public Optional<Shift> getActiveShift(User user) {
        return shiftRepository.findByUserAndEndTimeIsNull(user);
    }

    /** All currently active (not ended) shifts — for admin live view */
    public List<Shift> getAllActiveShifts() {
        return shiftRepository.findByEndTimeIsNull();
    }

    /** All shifts for today — for daily report */
    public List<Shift> getTodayShifts() {
        return shiftRepository.findByShiftDate(LocalDate.now());
    }
}
