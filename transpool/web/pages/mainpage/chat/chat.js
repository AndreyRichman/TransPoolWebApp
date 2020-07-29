const toggleChatboxBtn = document.querySelector(".js-chatbox-toggle");
const chatbox = document.querySelector(".js-chatbox");
const chatboxMsgDisplay = document.querySelector(".js-chatbox-display");
const chatboxForm = document.querySelector(".js-chatbox-form");

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

    createChatBubble(chatInput);

    e.preventDefault();
    chatboxForm.reset();
});