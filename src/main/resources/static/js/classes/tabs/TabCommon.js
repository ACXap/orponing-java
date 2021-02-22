"use strict"
export default class TabCommon {
    static listTab = [];
    tab;
    form;

    addTab(tab) {
        TabCommon.listTab.push(tab);
    }

    openForm() {
        window.localStorage.setItem("lastTabName", this.tab.id);
        this.form.hidden = false;
        this.closeOthersForms();
    }

    closeOthersForms() {
        TabCommon.listTab.forEach(tab => tab.form.hidden = tab.form != this.form);
    }

    startProcessing(message) {
        const m = message ?? "Обработка запроса...";
        const p = this.form.querySelector("div.processing");
        if (p) return;

        const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${m}</h5>
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                </div>`;

        this.form.insertAdjacentHTML("beforeend", proc);
        this.form.querySelector("button.start").classList.add("disabled");
    }

    stopProcessing() {
        const p = this.form.querySelector("div.processing");
        if (p) p.remove();
        this.form.querySelector("button.start").classList.remove("disabled");
    }

    notifyError(e) {
        console.error(e);
        alert(e);
    }

    static openLastTab() {
        const tabName = window.localStorage.getItem("lastTabName");
        const tab = this.listTab.filter(t => t.tab.id == tabName)[0];
        if (tab) {
            tab.openForm();
        } else {
            listTab[0].openForm();
        }
    }
}