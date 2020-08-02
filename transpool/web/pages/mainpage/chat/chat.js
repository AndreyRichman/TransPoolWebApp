const toggleChatboxBtn = document.querySelector(".js-chatbox-toggle");
const chatbox = document.querySelector(".js-chatbox");
const chatboxMsgDisplay = document.querySelector(".js-chatbox-display");
const chatboxForm = document.querySelector(".js-chatbox-form");
var chatVersion = 0;


// Use to create chat bubble when user submits text
// Appends to display
const createChatBubble = input => {
    const chatSection = document.createElement("p");
    chatSection.textContent = "Andrey: " + input;
    chatSection.classList.add("chatbox__display-chat");

    chatboxMsgDisplay.appendChild(chatSection);
};

// Toggle the visibility of the chatbox element when clicked
// And change the icon depending on visibility
toggleChatboxBtn.addEventListener("click", () => {
    chatbox.classList.toggle("chatbox--is-visible");

    if (chatbox.classList.contains("chatbox--is-visible")) {
        toggleChatboxBtn.innerHTML = '<i class="fas fa-chevron-down"></i>';
    } else {
        toggleChatboxBtn.innerHTML = '<i class="fas fa-chevron-up"></i>';
    }
});

// Form input using method createChatBubble
// To append any user message to display
chatboxForm.addEventListener("submit", e => {
    const chatInput = document.querySelector(".js-chatbox-input").value;

    $.ajax({
        url: '/transpool_war_exploded/sendchat',
        data: chatInput,
        timeout: 2000,
        error: function() {
            console.error("Failed to submit");
        },
        success: function(r) {
            console.log("INPUT: " + chatInput);
        }
    });

    // createChatBubble(chatInput);

    e.preventDefault();
    chatboxForm.reset();
});

function appendToChatArea(entries) {

    // add the relevant entries
    $.each(entries || [], appendChatEntry);


    chatboxForm.reset();

}

function appendChatEntry(index, entry){

    const chatSection = document.createElement("p");
    chatSection.textContent = entry.username + entry.chatString;
    chatSection.classList.add("chatbox__display-chat");

    chatboxMsgDisplay.appendChild(chatSection);
}

function ajaxChatContent() {
    $.ajax({
        url: '/transpool_war_exploded/chat',
        data: "chatversion=" + chatVersion,
        dataType: 'json',
        success: function(data) {
            // console.log("Server chat version: " + data.version + ", Current chat version: " + chatVersion);
            if (data.version !== chatVersion) {
                chatVersion = data.version;
                appendToChatArea(data.entries);
            }
            triggerAjaxChatContent();
        },
        error: function(error) {
            triggerAjaxChatContent();
        }
    });
}

function triggerAjaxChatContent() {
    setTimeout(ajaxChatContent, refreshRate);
}

//activate the timer calls after the page is loaded
$(function() {

    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxChatContent();
});