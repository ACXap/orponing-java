getElement("tab-orponing-address").onclick = openTabAddress;
getElement("tab-orponing-file").onclick = openTabFile;
getElement("tab-orponing-clipboard").onclick = openTabClip;

function startProcessing(id, message) {
    const p = getElement(id).querySelector("div.processing");

    if (p) return;

    const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${message}</h5>
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                </div>`;

    getElement(id).insertAdjacentHTML("beforeend", proc);
    getElement(id + ">div>button.start").classList.add("disabled");
}

function stopProcessing(id) {
    const p = getElement(id).querySelector("div.processing");

    if (p) {
        p.remove();
    }

    const a = getElement(id + ">div>button.start");

    getElement(id + ">div>button.start").classList.remove("disabled");
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
    displayElement("div-form-address");
    if (getElement("gid").value) {
        displayElement("result-address");
    }

    closeTabFile();
    closeTabClip();
}

function openTabFile() {
    displayElement("div-form-file");
    if (listAddressOfFile.length > 0) {
        displayElement("result-file");
    }

    closeTabAddress();
    closeTabClip();
}

function openTabClip() {
    displayElement("div-form-clipboard");
    if (listAddressOfClipboard.length > 0) {
        displayElement("result-clipboard");
    }

    closeTabAddress();
    closeTabFile();
}

function closeTabAddress() {
    hideElement("div-form-address");
    hideElement("result-address");
}

function closeTabFile() {
    hideElement("div-form-file");
    hideElement("result-file");
}

function closeTabClip() {
    hideElement("div-form-clipboard");
    hideElement("result-clipboard");
}

function stopError(error, idForm) {
    notifyError(error);
    stopProcessing(idForm);
}