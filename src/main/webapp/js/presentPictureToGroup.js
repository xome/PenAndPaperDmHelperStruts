function presentPicture(pictureIdOpener) {
    let presentationWindow = window.open("", "presentationWindow", "popup=true");
    let images = presentationWindow.document.querySelector("img");
    if (images !== null) {
        images.remove();
    }
    presentationWindow.document.write("" +
        "<html>" +
        "<head>" +
        "   <link rel='stylesheet' href='../css/presentationWindow.css' /> " +
        "</head>" +
        "<body>" +
        "   <img src='" + document.getElementById(pictureIdOpener).src + "' />" +
        "</body>" +
        "</html>"
    );
}