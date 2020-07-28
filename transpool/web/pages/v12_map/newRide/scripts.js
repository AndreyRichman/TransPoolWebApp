function validate(){
  var addStation = document.getElementById("addStation").value;
  var removeStation = document.getElementById("removeStation").value;
  var day = document.getElementById("day").value;
  var time = document.getElementById("time").value;
  var ppk = document.getElementById("ppk").value;
  var capacity = document.getElementById("capacity").value;
  var ppk = document.getElementById("ppk").value;
  var reputable = document.getElementById("reputable").value;
  var path = document.getElementById("path").value;
  var error_message = document.getElementById("error_message");

  error_message.style.padding = "10px";

  var text;
  if(name.length < 5){
    text = "Please Enter valid Name";
    error_message.innerHTML = text;
    return false;
  }
  if(subject.length < 10){
    text = "Please Enter Correct Subject";
    error_message.innerHTML = text;
    return false;
  }
  if(isNaN(phone) || phone.length != 10){
    text = "Please Enter valid Phone Number";
    error_message.innerHTML = text;
    return false;
  }
  if(email.indexOf("@") == -1 || email.length < 6){
    text = "Please Enter valid Email";
    error_message.innerHTML = text;
    return false;
  }
  if(message.length <= 140){
    text = "Please Enter More Than 140 Characters";
    error_message.innerHTML = text;
    return false;
  }
  alert("Form Submitted Successfully!");
  return true;
}