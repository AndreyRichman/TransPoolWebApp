package enums;

import exception.NotSupportedRideRepeatTimeException;

import java.util.function.Function;

public enum RepeatType {
    UNDEFINED {
        @Override
        int getDevider(){ return 0;}
    },
    ONE_TIME {
        @Override
        int getDevider() {
            return 0;
        }

        @Override
        public Function<Integer, Boolean> getDayMatchComparator(int rideDay) {
            return trempRequestDay -> rideDay == trempRequestDay;
        }

        @Override
        public Function<Integer, Integer> getDaysUntilClosestFutureDayIncludedGetter(int initialDay){
            return futureDay -> initialDay;
        }

        @Override
        public Function<Integer, Integer> getDaysUntilClosestPastDayIncludedGetter(int initialDay){
            return futureDay -> initialDay;
        }
    },
    DAILY {
        @Override
        int getDevider() {
            return 1;
        }
    },
    BIDAIILY {
        @Override
        int getDevider() {
            return 2;
        }
    },
    WEEKLY {
        @Override
        int getDevider() {
            return 7;
        }
    },
    MONTHLY {
        @Override
        int getDevider() {
            return 30;
        }
    };

     public Function<Integer, Boolean> getDayMatchComparator(int rideDay){
        return trempRequestDay -> (trempRequestDay - rideDay) % getDevider() == 0;
    }

    public Function<Integer, Integer> getDaysUntilClosestFutureDayIncludedGetter(int initialDay){
         return futureDay ->
                 (int) (Math.ceil((double)(futureDay - initialDay) / getDevider() ) * getDevider());
    }

    public Function<Integer, Integer> getDaysUntilClosestPastDayIncludedGetter(int initialDay){
        return futureDay ->
                ((futureDay - initialDay) / getDevider() ) * getDevider();
    }

    abstract int getDevider();

    public static RepeatType getRepeatTypeFromString(String repeatType) throws NotSupportedRideRepeatTimeException {

        RepeatType type;
        switch(repeatType){
            case "OneTime" : {
                type = RepeatType.ONE_TIME;
                break;
            }
            case "Daily" : {
                type = RepeatType.DAILY;
                break;
            }
            case "BiDaily" : {
                type = RepeatType.BIDAIILY;
                break;
            }
            case "Weekly" : {
                type = RepeatType.WEEKLY;
                break;
            }
            case "Monthly" : {
                type = RepeatType.MONTHLY;
                break;
            }
            default: {
                throw new NotSupportedRideRepeatTimeException(repeatType);
            }
        }
        return type;
    }
}
