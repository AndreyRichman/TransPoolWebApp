package transpool.logic.time;

import enums.DesiredTimeType;
import enums.RepeatType;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class RequestSchedule extends Schedule{
    DesiredTimeType desiredTimeType;
    int maxDiffInMinutes = 0;


    public RequestSchedule(LocalTime startTime, int day, RepeatType repeatType, DesiredTimeType desiredTimeType) {
        super(startTime, day, repeatType);

        this.desiredTimeType = desiredTimeType;
    }

    public DesiredTimeType getDesiredTimeType() {
        return desiredTimeType;
    }

    @Override
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setMaxDiffInMinutes(int maxDiffInMinutes) {
        this.maxDiffInMinutes = maxDiffInMinutes;
    }

    public int getMaxDiffInMinutes() {
        return maxDiffInMinutes;
    }

    public LocalDateTime getDesiredDateTimeAccordingToTimeType(){
        return this.desiredTimeType == DesiredTimeType.DEPART ? this.startDateTime : this.endDateTime;
    }

    public int getDesiredDayAccordingToTimeType(){
        return this.desiredTimeType == DesiredTimeType.DEPART ? this.getStartDay() : this.getEndDay();
    }
}
