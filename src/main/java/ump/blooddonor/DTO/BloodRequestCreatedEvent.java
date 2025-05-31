package ump.blooddonor.DTO;

import lombok.Data;
import ump.blooddonor.entity.BloodRequest;

@Data
public class BloodRequestCreatedEvent {
    private final BloodRequest bloodRequest;

    public BloodRequestCreatedEvent(BloodRequest bloodRequest) {
        this.bloodRequest = bloodRequest;
    }

    public BloodRequest getBloodRequest() {
        return bloodRequest;
    }
}