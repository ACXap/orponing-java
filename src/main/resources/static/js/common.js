function setActiveLink() {
    for (el of document.querySelectorAll("a")) {
        if (el.href == window.location.href) {
            el.classList.add("active");
            break;
        }
    }
}

function getElement(id) {
    return document.querySelector(`#${id}`);
}

function hideElement(id) {
    getElement(id).hidden = true;
}

function displayElement(id) {
    getElement(id).hidden = false;
}