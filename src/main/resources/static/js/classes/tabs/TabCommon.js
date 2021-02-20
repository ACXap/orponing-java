"use strict"
export default class TabCommon {
    static listTab = [];

    addTab(tab) {
        TabCommon.listTab.push(tab);
    }

    openForm(isOpenResult) {
        window.localStorage.setItem("lastTabName", this.tab.id);
        this.form.hidden = false;

        if (isOpenResult) this.form.querySelector("div.result").hidden = false;

        this.closeFormsExceptCurrent(this.form);
    }

    closeFormsExceptCurrent(currentForm) {
        for (const tab of TabCommon.listTab) {
            if (tab.form != currentForm) {
                tab.form.hidden = true;
                tab.form.querySelector("div.result").hidden = true;
            }
        }
    }

    startProcessing(message) {
        const p = this.form.querySelector("div.processing");
        if (p) return;

        const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${message}</h5>
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
            tab.open();
        } else {
            listTab[0].open();
        }
    }
}