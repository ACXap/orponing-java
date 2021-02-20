"use strict"
import TabCommon from "./TabCommon.js";
export default class TabAddress extends TabCommon {
    tab;
    form;
    serviceOrponing;

    constructor(serviceOrponing) {
        super();
        this.serviceOrponing = serviceOrponing;
        this.tab = document.querySelector("#tab-orponing-address");
        this.form = document.querySelector("#div-form-address");

        this.addTab(this);

        this.tab.onclick = () => this.open();
        this.form.querySelector("input").addEventListener("keyup", e => {
            if (e.keyCode != 13) return;
            e.preventDefault();
            this.orponing();
        });

        this.form.querySelector("button.start").onclick = () => this.orponing();
    }

    async orponing() {
        try {
            const address = this.form.querySelector("input").value;

            if (address) {
                this.startProcessing("Обработка запроса...");

                const json = await this.serviceOrponing.orponing(address);

                if (json) {
                    this.form.querySelector("div.result").hidden = false;
                    this.form.querySelector("#errorInfo").hidden = json.IsValid;
                    this.form.querySelector("#gidInfo").hidden = !json.IsValid;
                    this.form.querySelector("#addressInfo").hidden = !json.IsValid;

                    this.form.querySelector("#gid").value = json.GlobalId;
                    this.form.querySelector("#addressOrpon").value = json.AddressOrpon;
                    this.form.querySelector("#parsingLevelCode").value = json.ParsingLevelCode;
                    this.form.querySelector("#unparsedParts").value = json.UnparsedParts;
                    this.form.querySelector("#qualityCode").value = json.QualityCode;
                    this.form.querySelector("#checkStatus").value = json.CheckStatus;
                    this.form.querySelector("#error").value = json.Error;

                    const header = this.form.querySelector("#headerInfoAddress");
                    if (json.IsValid) {
                        header.textContent = "Адрес разобран";
                        header.style = "color:green";
                    } else {
                        header.textContent = "Адрес разобран c ошибками";
                        header.style = "color:red";
                    }
                } else {
                    this.form.querySelector("div.result");
                }
            }
        } catch (e) {
            this.notifyError(e);
        } finally {
            this.stopProcessing();
        }
    }

    open() {
        this.openForm(document.querySelector("#gid").value);
    }
}