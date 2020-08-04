package notification;

import java.util.LinkedList;
import java.util.List;

public class NotificationsContainer {
    private int privateMessageIndex;
    private int publicMessageIndex;

    private List<String> privateMessages;
    private List<String> publicMessages;

    public NotificationsContainer() {
        this.privateMessageIndex = 0;
        this.publicMessageIndex = 0;
        this.privateMessages = new LinkedList<>();
        this.publicMessages = new LinkedList<>();
    }

    public synchronized void addPrivateMessage(String message){
        this.privateMessages.add(message);
    }

    public synchronized void addPublicMessage(String message){
        this.publicMessages.add(message);
    }

    public synchronized boolean hasNewPrivateMessages(){
        return this.privateMessageIndex < this.privateMessages.size();
    }

    public synchronized boolean hasNewPublicMessages(){
        return this.publicMessageIndex < this.publicMessages.size();
    }

    public synchronized boolean hasNewMessages(){
        return hasNewPrivateMessages() || hasNewPublicMessages();
    }

    public synchronized List<String> getNewPrivateMessages(){
        List<String> newMessages = new LinkedList<>();

        if (hasNewPrivateMessages()) {
            newMessages = this.privateMessages.subList(this.privateMessageIndex, this.privateMessages.size());
            this.privateMessageIndex = this.privateMessages.size();
        }

        return newMessages;
    }

    public synchronized List<String> getNewPublicMessages(){
        List<String> newMessages = new LinkedList<>();

        if (hasNewPublicMessages()) {
            newMessages = this.publicMessages.subList(this.publicMessageIndex, this.publicMessages.size());
            this.publicMessageIndex = this.publicMessages.size();
        }

        return newMessages;
    }

    public synchronized List<String> getNewMessages() {
        List<String> newPublic = getNewPublicMessages();
        List<String> newPrivate = getNewPrivateMessages();

        newPublic.addAll(newPrivate);

        return newPublic;
    }
}
