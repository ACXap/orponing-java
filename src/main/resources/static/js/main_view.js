"use strict"
//#region const
const TAB_ADDRESS = "tab-orponing-address";
const FORM_ADDRESS = "div-form-address";
const ORPONING_ADDRESS = "orponing-address";
const INPUT_ADDRESS = "input-address";

const TAB_FILE = "tab-orponing-file";
const FORM_FILE = "div-form-file";
const ORPONING_FILE = "orponing-file";
const INPUT_FILE = "input-file";

const TAB_CLIPBOARD = "tab-orponing-clipboard";
const FORM_CLIPBOARD = "div-form-clipboard";
const ORPONING_CLIPBOARD = "orponing-clipboard";
const INPUT_CLIPBOARD = "input-clipboard";

const DIV_RESULT = "div.result";
//#endregion

getElement(TAB_ADDRESS).onclick = openTabAddress;
getElement(TAB_FILE).onclick = openTabFile;
getElement(TAB_CLIPBOARD).onclick = openTabClip;

getElement(INPUT_ADDRESS).addEventListener("keyup", e => {
    if (e.keyCode != 13) return;
    e.preventDefault();
    getElement(ORPONING_ADDRESS).click();
});

getElement(INPUT_CLIPBOARD).onclick = async () => {
    if (navigator.clipboard) {
        const data = await navigator.clipboard.readText();

        if (data) {
            const result = initListAddressOfClipboard(data);
            if (result.error) {
                notifyError(result.error);
            } else {
                setPreview(FORM_CLIPBOARD, result.previewList);
            }

            getElement(FORM_CLIPBOARD + ">div.count-address").textContent = "Всего записей: " + result.count;
        } else {
            notifyError("В буфере обмена нет подходящих данных");
        }
    }
}

getElement(INPUT_FILE).onchange = (e) => {
    const file = e.currentTarget.files[0];

    initListAddressOfFile(file, (result) => {
        if (result.error) {
            notifyError(result.error);
            getElement(INPUT_FILE).value = "";
        } else {
            setPreview(FORM_FILE, result.previewList);
        }
        getElement(FORM_FILE + ">div.count-address").textContent = "Всего записей: " + result.count;
    });
}

getElement(ORPONING_ADDRESS).onclick = async () => {
    try {
        const address = getElement(INPUT_ADDRESS).value;

        if (address) {
            startProcessing(FORM_ADDRESS, "Обработка запроса...");

            const json = await orponingAddress(address);

            if (json) {
                displayElement(FORM_ADDRESS + ">" + DIV_RESULT);
                getElement("errorInfo").hidden = json.IsValid;
                getElement("gidInfo").hidden = !json.IsValid;
                getElement("addressInfo").hidden = !json.IsValid;

                getElement("gid").value = json.GlobalId;
                getElement("addressOrpon").value = json.AddressOrpon;
                getElement("parsingLevelCode").value = json.ParsingLevelCode;
                getElement("unparsedParts").value = json.UnparsedParts;
                getElement("qualityCode").value = json.QualityCode;
                getElement("checkStatus").value = json.CheckStatus;
                getElement("error").value = json.Error;

                const header = getElement("headerInfoAddress");
                if (json.IsValid) {
                    header.textContent = "Адрес разобран";
                    header.style = "color:green";
                } else {
                    header.textContent = "Адрес разобран c ошибками";
                    header.style = "color:red";
                }
            } else {
                hideElement(FORM_ADDRESS + ">" + DIV_RESULT);
            }
        }
    } catch (e) {
        notifyError(e);
    } finally {
        stopProcessing(FORM_ADDRESS);
    }
}

getElement(ORPONING_FILE).onclick = () => orponingData(FORM_FILE, orponingFile);

getElement(ORPONING_CLIPBOARD).onclick = () => orponingData(FORM_CLIPBOARD, orponingClipboard);

getElement(FORM_FILE).ondragover = (e) => {
    e.stopPropagation();
    e.preventDefault();
};

getElement(FORM_FILE).ondrop = (e) => {
    e.stopPropagation();
    e.preventDefault();

    const files = e.dataTransfer.files;
    initListAddressOfFile(files[0], (result) => {
        if (result.error) {
            notifyError(result.error);
            getElement(INPUT_FILE).value = "";
        } else {
            getElement(INPUT_FILE).files = files;
        }

        setPreview(FORM_FILE, result.previewList);
        getElement(FORM_FILE + ">div.count-address").textContent = "Всего записей: " + result.count;
    });
};

function setPreview(idForm, result) {
    removeElement(idForm + ">div.preview");
    if (result) {
        const header = getHeaderPreview();
        getElement(idForm).insertAdjacentHTML("beforeend", header);
        for (const address of result) {
            const row = getRowPreview(address.Id, address.Address);
            getElement(idForm + ">div.preview>div").insertAdjacentHTML("beforeend", row);
        }
    }
}

function orponingData(idForm, execute) {
    startProcessing(idForm, "Обработка запроса...");
    hideElement(idForm + ">div.result");

    execute((data, error) => {
        if (data) {
            addDownLoadLink(idForm, data);
            removeElement(idForm + ">div.preview");
        } else if (error) {
            notifyError(error);
        }

        stopProcessing(idForm);
    });
}

function openLastTab() {
    const lastTab = window.localStorage.getItem("lastTabName");
    if (lastTab) {
        getElement(lastTab).click();
    } else {
        getElement(TAB_ADDRESS).click();
    }
}

function startProcessing(id, message) {
    const p = getElement(id + ">div.processing");
    if (p) return;

    const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${message}</h5>
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                </div>`;

    getElement(id).insertAdjacentHTML("beforeend", proc);
    disableElement(id + ">div>button.start");
}

function stopProcessing(id) {
    const p = getElement(id + ">div.processing");

    if (p) p.remove();
    enableElement(id + ">div>button.start");
}

function getDownloadFile() {
    const downloadFile = document.createElement("a");
    downloadFile.className = "btn btn-primary";
    downloadFile.download = `load.csv`;
    downloadFile.textContent = "Скачать";

    return downloadFile;
}

function addDownLoadLink(idForm, data) {
    const downloadFile = getDownloadFile();
    downloadFile.href = data;

    removeElement(idForm + ">div.result>a");

    displayElement(idForm + ">div.result");
    getElement(idForm + ">div.result").appendChild(downloadFile);
}

function openTabAddress() {
    window.localStorage.setItem("lastTabName", TAB_ADDRESS);
    displayElement(FORM_ADDRESS);
    if (getElement("gid").value) displayElement(FORM_ADDRESS + ">" + DIV_RESULT);

    closeTab(FORM_FILE);
    closeTab(FORM_CLIPBOARD);
}

function openTabFile() {
    window.localStorage.setItem("lastTabName", TAB_FILE);
    displayElement(FORM_FILE);

    const t = getElement(FORM_FILE + ">div.result>a");
    if (getElement(FORM_FILE + ">div.result>a")) displayElement(FORM_FILE + ">" + DIV_RESULT);

    closeTab(FORM_ADDRESS);
    closeTab(FORM_CLIPBOARD);
}

function openTabClip() {
    window.localStorage.setItem("lastTabName", TAB_CLIPBOARD);
    displayElement(FORM_CLIPBOARD);
    if (getElement(FORM_CLIPBOARD + ">div.result>a")) displayElement(FORM_CLIPBOARD + ">" + DIV_RESULT);

    closeTab(FORM_ADDRESS);
    closeTab(FORM_FILE);
}

function closeTab(formName) {
    hideElement(formName);
    hideElement(formName + ">div.result");
}

function getRowPreview(id, address) {
    const result = parseInt(id);

    let color = "";
    let title = "";

    if (!result) {
        color = "bg-danger";
        title = "Идентификатор должен быть числом";
    }

    return `<div class="row border"><div class="col-2 border-end ${color}" title="${title}">${id}</div><div class="col-10">${address}</div></div>`;
}

function getHeaderPreview() {
    return `<div class="preview">
    <h5 class="text-center">Предварительный обзор данных для обработки (первые 10 записей)</h5>
    <div class="container">
        <div class="row border"><div class="col-2 border-end">Идентификатор</div><div class="col-10">Адрес</div></div>
    </div>
</div>`;
}

openLastTab();