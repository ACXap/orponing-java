"use strict"
export default class CommonUi {
    setActiveLink() {
        for (const el of document.querySelectorAll("a")) {
            if (el.href == window.location.href) {
                el.classList.add("active");
                break;
            }
        }
    }

    getElement(id) {
        return document.querySelector(`#${id}`);
    }

    hideElement(id) {
        this.getElement(id).hidden = true;
    }

    displayElement(id) {
        this.getElement(id).hidden = false;
    }

    removeElement(id) {
        if (this.getElement(id)) {
            this.getElement(id).remove();
        }
    }

    disableElement(id) {
        this.getElement(id).classList.add("disabled");
    }

    enableElement(id) {
        this.getElement(id).classList.remove("disabled");
    }

    notifyError(e) {
        console.error(e);
        alert(e);
    }
}