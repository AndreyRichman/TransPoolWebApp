package transpool.logic.user;

import enums.TrempPartType;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrempistsManager {


    private HashMap<Integer, LinkedList<Trempist>> allTrempists;
    private HashMap<Integer, LinkedList<Trempist>> justJoinedTrempists;
    private HashMap<Integer, LinkedList<Trempist>> leavingTrempists;

    public TrempistsManager() {
        allTrempists = new HashMap<>();
        justJoinedTrempists = new HashMap<>();
        leavingTrempists = new HashMap<>();
    }

    public void addTrempist(Trempist trempistToAdd, int onDay){

        addTrempistToMap(allTrempists, onDay, trempistToAdd);

        if (trempistToAdd.fromPartType == TrempPartType.FIRST)
            addTrempistToMap(justJoinedTrempists, onDay, trempistToAdd);

        if (trempistToAdd.toPartType == TrempPartType.LAST)
            addTrempistToMap(leavingTrempists, onDay, trempistToAdd);
    }

    private void addTrempistToMap(HashMap<Integer, LinkedList<Trempist>> map, int dayKey, Trempist trempistToAdd){
        if (!map.containsKey(dayKey))
            map.put(dayKey, new LinkedList<>());

        map.get(dayKey).add(trempistToAdd);
    }

    public void addTrempistToAllTrempists(int dayKey, Trempist trempistToAdd){
        if (!this.allTrempists.containsKey(dayKey))
            this.allTrempists.put(dayKey, new LinkedList<>());

        this.allTrempists.get(dayKey).add(trempistToAdd);
    }

    public List<Trempist> getAllTrempists(int onDay) {
        if (!allTrempists.containsKey(onDay))
            allTrempists.put(onDay, new LinkedList<>());

        return allTrempists.get(onDay);
    }

    public List<Trempist> getJustJoinedTrempists(int onDay) {
        if (!justJoinedTrempists.containsKey(onDay))
            justJoinedTrempists.put(onDay, new LinkedList<>());

        return justJoinedTrempists.get(onDay);
    }

    public List<Trempist> getJustJoinedTrempistsAllDays(){
        return this.justJoinedTrempists.values().stream().flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    public List<Trempist> getLeavingTrempistsAllDays() {
        return this.leavingTrempists.values().stream().flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    public List<Trempist> getLeavingTrempists(int onDay) {
        if (!leavingTrempists.containsKey(onDay))
            leavingTrempists.put(onDay, new LinkedList<>());

        return leavingTrempists.get(onDay);
    }

    public boolean haveTrempistsOnAnyDay(){
        return this.allTrempists.entrySet().stream().filter(dayToList -> dayToList.getValue().size() > 0)
                .map(Map.Entry::getKey).count() > 0;
    }

    public HashMap<Integer, LinkedList<Trempist>> getAllTrempists() {
        return allTrempists;
    }

//    public List<Pair<Integer, Trempist>> getDayToTrempistPairs(){
//        List<Pair<Integer, Trempist>> trempistsPairs = new LinkedList<>();
//
//        for (Map.Entry<Integer, LinkedList<Trempist>> dayToTrempistEntry : this.getAllTrempists().entrySet()) {
//            if (dayToTrempistEntry.getValue().size() > 0){
//                for (Trempist trempist : dayToTrempistEntry.getValue()) {
//                    trempistsPairs.add(new Pair<>(dayToTrempistEntry.getKey(), trempist));
//                }
//            }
//        }
//
//        return trempistsPairs;
//    }
}
