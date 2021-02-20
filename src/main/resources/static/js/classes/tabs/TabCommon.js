"use strict"
export default class TabCommon {
    static listTab = [];
    static listForm = [];

    addTab(tab) {
        TabCommon.listTab.push(tab);
    }
    addForm(form) {
        TabCommon.listForm.push(form);
    }

    openForm(currentTab, currentForm, isOpenResult) {
        window.localStorage.setItem("lastTabName", currentTab.id);
        currentForm.hidden = false;

        if (isOpenResult) currentForm.querySelector("div.result");

        this.closeFormsExceptCurrent(currentForm);
    }

    closeFormsExceptCurrent(currentForm) {
        for (const form of TabCommon.listForm) {
            if (form != currentForm) {
                form.hidden = true;
                form.querySelector("div.result").hidden = true;
            }
        }
    }

    startProcessing(form, message) {
        const p = form.querySelector("div.processing");
        if (p) return;

        const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${message}</h5>
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                </div>`;

        form.insertAdjacentHTML("beforeend", proc);
        form.querySelector("button.start").classList.add("disabled");
    }

    stopProcessing(form) {
        const p = form.querySelector("div.processing");
        if (p) p.remove();
        form.querySelector("button.start").classList.remove("disabled");
    }

    notifyError(e) {
        console.error(e);
        alert(e);
    }

    static openLastTab() {
        const tabName = window.localStorage.getItem("lastTabName");
        const tab = this.listTab.filter(t => t.tab.id == tabName);
        if (tab) {
            tab.open();
        } else {
            listTab[0].open();
        }
    }
}