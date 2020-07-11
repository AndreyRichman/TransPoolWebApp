package transpool.logic.time;

import enums.DesiredTimeType;
import enums.RepeatType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Schedule {
    private RepeatType repeatType;
    public static LocalDateTime minDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;



    public Schedule(LocalTime startTime, int day, RepeatType repeatType){
        this.repeatType = repeatType;
        initStartAndEndDateTimes(day, startTime);
    }

    public Schedule(Schedule startSchedule, Schedule endSchedule){
        this.repeatType = RepeatType.UNDEFINED;
        setStartDateTime(startSchedule.getStartDateTime());
        setEndDateTime(endSchedule.getEndDateTime());
    }

    public static Schedule createScheduleMixFromSchedules(Schedule startSchedule, Schedule endSchedule){
        Schedule schedule = new Schedule(startSchedule, endSchedule);
        schedule.setRepeatType(startSchedule.getRepeatType());

        return schedule;
    }

    //TODO: add validation for hour & day
    private void initStartAndEndDateTimes(int day,LocalTime startTime){
        int hour = startTime.getHour();
        int minutes = startTime.getMinute();
        this.startDateTime = this.minDateTime.plusDays(day).plusHours(hour).plusMinutes(minutes);
        this.endDateTime = this.startDateTime;
    }

    public void addHoursFromStart(int hoursToAdd){
        this.endDateTime = this.endDateTime.plusHours(hoursToAdd);
    }

    public void addMintuesFromStart(int minutesDuration){

        LocalDateTime expectedEndTime = this.getStartDateTime().plusMinutes(minutesDuration);// startTime.plusMinutes(duration);

        int minutesAtEnd = expectedEndTime.getMinute();
        int sheerit = minutesAtEnd % 5;
        int minutesToAdd = sheerit > 2 ? 5 - sheerit: -sheerit;

        this.endDateTime = expectedEndTime.plusMinutes(minutesToAdd);
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
//        this.endDateTime = startDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getStartDay(){
        return (int)Duration.between(this.startDateTime, minDateTime).abs().toDays();
    }

    public static int getDayOfDateTime(LocalDateTime localDateTime){
        return (int)Duration.between(localDateTime, minDateTime).abs().toDays();
    }

    public int getEndDay() {
        return (int)Duration.between(this.endDateTime, minDateTime).abs().toDays();
    }

    public LocalTime getStartTime() {
        return this.startDateTime.toLocalTime();
    }

    public LocalTime getEndTime() {
        return this.endDateTime.toLocalTime();
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

        public void setRepeatType(RepeatType repeatType) {
        this.repeatType = repeatType;
    }

    public boolean scheduleIsRelevantForTimeAndDay(LocalTime time, int day, DesiredTimeType timeType, int minutesDiffLimit){
        LocalDateTime desiredDateTime = minDateTime.plusHours(time.getHour()).plusDays(day);

        return timeType == DesiredTimeType.DEPART ?
                startDaysRangeMatchesDateTime(desiredDateTime, minutesDiffLimit) :
                endDaysRangeMatchesDateTime(desiredDateTime, minutesDiffLimit);
    }

    public boolean scheduleIsRelevantForRequestSchedule(RequestSchedule requestSchedule){
        LocalDateTime desiredDateTime = requestSchedule.getDesiredDateTimeAccordingToTimeType();
        int minutesDiffLimit = requestSchedule.getMaxDiffInMinutes();

        return requestSchedule.getDesiredTimeType() == DesiredTimeType.DEPART ?
                startDaysRangeMatchesDateTime(desiredDateTime, minutesDiffLimit) :
                endDaysRangeMatchesDateTime(desiredDateTime, minutesDiffLimit);
    }

    private boolean startDaysRangeMatchesDateTime(LocalDateTime dateTimeToCheck, int maxMinutesDiff){
        int dayToCheck = (int) Duration.between(minDateTime, dateTimeToCheck).abs().toDays();
        return daysRangeContainsDay(this.getStartDay(), dayToCheck)
                && startDateTimeIsNearDateTime(this.getStartDateTime(), dateTimeToCheck, maxMinutesDiff);
    }

    private boolean endDaysRangeMatchesDateTime(LocalDateTime dateTimeToCheck, int maxMinutesDiff){
        int dayToCheck = (int) Duration.between(minDateTime, dateTimeToCheck).abs().toDays();
        return daysRangeContainsDay(this.getEndDay(), dayToCheck)
                && startDateTimeIsNearDateTime(this.getEndDateTime(), dateTimeToCheck, maxMinutesDiff);
    }

    private boolean daysRangeContainsDay(int thisDay, int dayToCheck){
        return thisDay <= dayToCheck &&
                repeatType.getDayMatchComparator(thisDay).apply(dayToCheck);
    }

    private boolean startDateTimeIsNearDateTime(LocalDateTime thisDateTime, LocalDateTime dateTimeToCheck, int minutesDiffLimit){
        return Duration.between(thisDateTime, dateTimeToCheck).abs().toMinutes() <= minutesDiffLimit;
    }

    public Schedule createClone(){
        return new Schedule(this.getStartTime(), this.getStartDay(), this.getRepeatType());
    }

    public Schedule createCloneWithNewStartDateTime(LocalDateTime newDateTime){
        Schedule newSchedule = createClone();
        newSchedule.setStartDateTime(newDateTime);
        newSchedule.setEndDateTime(newDateTime);

        return newSchedule;
    }

    public boolean hasInstanceStartsAfterDateTime(LocalDateTime dateTime){  //this can show options days after
        return (this.getStartDateTime().isEqual(dateTime) || this.getStartDateTime().isAfter(dateTime))
                ||
                (this.getRepeatType() != RepeatType.ONE_TIME && this.getRepeatType() != RepeatType.UNDEFINED);
    }

//    public boolean hasInstanceEndsAfterDateTime(LocalDateTime dateTime){    //this will show after according mainly time
//        return (this.getEndDateTime().isEqual(dateTime) || this.getEndDateTime().isAfter(dateTime))
//                ||
//                (this.getRepeatType() != RepeatType.ONE_TIME && this.getRepeatType() != RepeatType.UNDEFINED);
//    }

    public boolean hasInstanceContainingDateTime(LocalDateTime dateTime){
        return this.getRepeatType() == RepeatType.ONE_TIME ?
                (this.getStartDateTime().equals(dateTime) || this.getStartDateTime().isBefore(dateTime))
                        &&
                (this.getEndDateTime().equals(dateTime) || this.getEndDateTime().isAfter(dateTime))
                :
                currentDaysContainsDayOf(dateTime)
                &&
                currentTimesContainsTimeOf(dateTime);
    }

    private boolean currentTimesContainsTimeOf(LocalDateTime dateTime) {
        LocalTime timeToCheck = dateTime.toLocalTime();

        boolean timeIsAfterStart = timeToCheck.equals(this.getStartTime()) || timeToCheck.isAfter(this.getStartTime());
        boolean timeIsBeforeEnd = timeToCheck.equals(this.getEndTime()) || timeToCheck.isBefore(this.getEndTime());

        return this.getStartDay() == this.getEndDay() ?
                   timeIsAfterStart && timeIsBeforeEnd : timeIsAfterStart || timeIsBeforeEnd;
    }

    private boolean currentDaysContainsDayOf(LocalDateTime dateTime) {
        return daysRangeContainsDay(this.getStartDay(), getDayOfDateTime(dateTime))
                ||
                daysRangeContainsDay(this.getEndDay(), getDayOfDateTime(dateTime));
    }


    public boolean hasInstanceEndsBeforeDateTime(LocalDateTime dateTime){
        return this.getEndDateTime().isEqual(dateTime) || this.getEndDateTime().isBefore(dateTime);
    }

//    public boolean hasInstanceStartsBeforeDateTime(LocalDateTime dateTime){
//        return this.getStartDateTime().isEqual(dateTime) || this.getStartDateTime().isBefore(dateTime);
//    }


    public LocalDateTime getClosestDateTimeAfter(LocalDateTime dateTime, LocalDateTime originalDateTime){
        int day = this.repeatType
                .getDaysUntilClosestFutureDayIncludedGetter(getDayOfDateTime(originalDateTime))
                .apply(getDayOfDateTime(dateTime));
        int daysAfter = day - getDayOfDateTime(originalDateTime);

        return originalDateTime.plusDays(daysAfter);
    }

    public LocalDateTime getClosestDateTimeBefore(LocalDateTime dateTime, LocalDateTime originalDateTime){
        int day = this.repeatType
                .getDaysUntilClosestPastDayIncludedGetter(getDayOfDateTime(originalDateTime))
                .apply(getDayOfDateTime(dateTime));
        int daysBefore = getDayOfDateTime(originalDateTime) - day;

        return originalDateTime.plusDays(daysBefore);
    }



//    public void updateWithClosestDayAfter(LocalDateTime dateTime){
//
//    }

//    public boolean isBeforeDateTime(LocalDateTime dateTime){
//
//    }

//    public boolean withinDateRange(LocalDateTime start, LocalDateTime end){
//        this.getStartDateTime()
//    }
}
