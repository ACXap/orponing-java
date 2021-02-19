//#region const
TAB_ADDRESS = "tab-orponing-address";
FORM_ADDRESS = "div-form-address";
RESULT_ADDRESS = "result-address";
ORPONING_ADDRESS = "orponing-address";
INPUT_ADDRESS = "input-address";

TAB_FILE = "tab-orponing-file";
FORM_FILE = "div-form-file";
RESULT_FILE = "result-file";
ORPONING_FILE = "orponing-file";
INPUT_FILE = "input-file";

TAB_CLIPBOARD = "tab-orponing-clipboard";
FORM_CLIPBOARD = "div-form-clipboard";
RESULT_CLIPBOARD = "result-clipboard";
ORPONING_CLIPBOARD = "orponing-clipboard";
INPUT_CLIPBOARD = "input-clipboard";
//#endregion

getElement(TAB_ADDRESS).onclick = openTabAddress;
getElement(TAB_FILE).onclick = openTabFile;
getElement(TAB_CLIPBOARD).onclick = openTabClip;
openLastTab();

getElement(INPUT_ADDRESS).addEventListener("keyup", e => {
    if (e.keyCode != 13) return;
    e.preventDefault();
    getElement(ORPONING_ADDRESS).click();
});

getElement(INPUT_CLIPBOARD).onclick = async () => {
    if (navigator.clipboard) {
        const data = await navigator.clipboard.readText();

        const result = initListAddressOfClipboard(data);
        if (result.error) notifyError(result.error);
        getElement(FORM_CLIPBOARD + ">div.count-address").textContent = "Всего записей: " + result.count;
    }
}

getElement(INPUT_FILE).onchange = (e) => {
    const file = e.currentTarget.files[0];

    initListAddressOfFile(file, (result) => {
        if (result.error) notifyError(error);
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
                displayElement(RESULT_ADDRESS);
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

                header = getElement("headerInfoAddress");
                if (json.IsValid) {
                    header.textContent = "Адрес разобран";
                    header.style = "color:green";
                } else {
                    header.textContent = "Адрес разобран c ошибками";
                    header.style = "color:red";
                }
            } else {
                hideElement(RESULT_ADDRESS);
            }
        }
    } catch (e) {
        notifyError(e);
    } finally {
        stopProcessing(FORM_ADDRESS);
    }
}

getElement(ORPONING_FILE).onclick = () => {
    startProcessing(FORM_FILE, "Обработка запроса...");
    hideElement(RESULT_FILE);

    orponingFile((data, error) => {
        if (data) {
            addDownLoadLink(FORM_FILE, data);
        } else if (error) {
            notifyError(error);
        }

        stopProcessing(FORM_FILE);
    });
}

getElement(ORPONING_CLIPBOARD).onclick = () => {
    startProcessing(FORM_CLIPBOARD, "Обработка запроса...");
    hideElement(RESULT_CLIPBOARD);

    orponingClipboard((data, error) => {
        if (data) {
            addDownLoadLink(FORM_CLIPBOARD, data);
        } else if (error) {
            notifyError(error);
        }

        stopProcessing(FORM_CLIPBOARD);
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
    if (getElement("gid").value) displayElement(RESULT_ADDRESS);

    closeTab(FORM_FILE);
    closeTab(FORM_CLIPBOARD);
}

function openTabFile() {
    window.localStorage.setItem("lastTabName", TAB_FILE);
    displayElement(FORM_FILE);

    const t = getElement(FORM_FILE + ">div.result>a");
    if (getElement(FORM_FILE + ">div.result>a")) displayElement(RESULT_FILE);

    closeTab(FORM_ADDRESS);
    closeTab(FORM_CLIPBOARD);
}

function openTabClip() {
    window.localStorage.setItem("lastTabName", TAB_CLIPBOARD);
    displayElement(FORM_CLIPBOARD);
    if (getElement(FORM_CLIPBOARD + ">div.result>a")) displayElement(RESULT_CLIPBOARD);

    closeTab(FORM_ADDRESS);
    closeTab(FORM_FILE);
}

function closeTab(formName) {
    hideElement(formName);
    hideElement(formName + ">div.result");
}