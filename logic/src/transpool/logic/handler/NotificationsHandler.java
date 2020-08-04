package transpool.logic.handler;

import notification.NotificationsContainer;
import transpool.logic.user.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NotificationsHandler {
    Map<Integer, NotificationsContainer> idToNotificationHandler;

    public NotificationsHandler() {
        idToNotificationHandler = new HashMap<>();
    }

    public synchronized void addUser(User user){
        if (!userExist(user)) {
            idToNotificationHandler.put(user.getID(), new NotificationsContainer());
        }
    }

    public synchronized void addPrivateMessage(String message, User toUser){
        if (userExist(toUser)){
            this.idToNotificationHandler.get(toUser.getID()).addPrivateMessage(message);
        }
    }

    public synchronized void addPublicMessage(String message){
        idToNotificationHandler.values().forEach(notify -> notify.addPublicMessage(message));
    }

    public synchronized void addPublicMessageExceptUser(String message, User user){
        idToNotificationHandler.entrySet().forEach(entry -> {
            if (entry.getKey() != user.getID()){
                entry.getValue().addPublicMessage(message);
            }
        });
    }

    public synchronized void addPublicAndPrivateMessage(String publicMessage, String privateMessage, User privateUser){
        addPrivateMessage(privateMessage, privateUser);
        addPublicMessageExceptUser(publicMessage, privateUser);
    }

    public synchronized List<String> getNewPrivateMessages(User user){
        List<String> newMessages = new LinkedList<>();

        if (userExist(user)) {
            newMessages = this.idToNotificationHandler.get(user.getID()).getNewPrivateMessages();
        }

        return newMessages;
    }

    public synchronized List<String> getNewPublicMessages(User user){
        List<String> newMessages = new LinkedList<>();

        if (userExist(user)) {
            newMessages =  this.idToNotificationHandler.get(user.getID()).getNewPublicMessages();
        }

        return newMessages;
    }

    public synchronized List<String> getNewMessages(User user){
        return this.idToNotificationHandler.get(user.getID()).getNewMessages();
    }

    private synchronized boolean userExist(User user){
        return this.idToNotificationHandler.containsKey(user.getID());
    }
}
