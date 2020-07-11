package transpool.logic.user;

import enums.TrempPartType;
import transpool.logic.traffic.item.SubRide;

public class Trempist {

    User user;
    TrempPartType fromPartType;
    TrempPartType toPartType;

    SubRide originalSubRide;
    Driver.Rank rank;

    public Trempist(User user, TrempPartType fromPartType, TrempPartType toPartType, SubRide subRide) {
        this.user = user;
        this.fromPartType = fromPartType;
        this.toPartType = toPartType;
        this.originalSubRide = subRide;
        this.rank = null;
    }

    public Trempist(User user, SubRide subRide){
        this.user = user;
        this.fromPartType = TrempPartType.DEFAULT;
        this.toPartType = TrempPartType.DEFAULT;
        this.originalSubRide = subRide;
    }

    public TrempPartType getFromPartType() {
        return fromPartType;
    }

    public TrempPartType getToPartType() {
        return toPartType;
    }

    public User getUser() {
        return user;
    }

    public boolean notRankedRide(){
        return this.rank == null;
    }

    public void giveRankToRide(int score, String comment){
        this.rank = new Driver.Rank(this.user, score);
        if (comment != null)
            this.rank.setComment(comment);

        this.originalSubRide.getOriginalRide().getRideOwner().addRank(rank);
    }
}
