"use strict"
function setActiveLink() {
    for (const el of document.querySelectorAll("a")) {
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

function removeElement(id) {
    if (getElement(id)) {
        getElement(id).remove();
    }
}

function disableElement(id) {
    getElement(id).classList.add("disabled");
}

function enableElement(id) {
    getElement(id).classList.remove("disabled");
}

function notifyError(e) {
    console.error(e);
    alert(e);
}