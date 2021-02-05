list = [];

getElement("orponing-address").onclick = async () => {
    try {
        const address = getElement("input-address").value;

        if (address) {
            getElement("orponing-address").classList.add("disabled");
            startProcessing("div-form-address", "Обработка запроса...");

            const json = await apiOrponingAddress(address);

            if (json) {
                displayElement("result");
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
            }
        }
    } catch (e) {
        notifyError(e);
    } finally {
        stopProcessing("div-form-address");
        getElement("orponing-address").classList.remove("disabled");
    }
}

getElement("orponing-file").onclick = () => {
    const file = getElement("formFile").files[0];
    if (!file || checkTypeFile(file) === false) {
        if (!file) {
            alert("А кто файл то будет добавлять?");
        } else {
            alert("Неверный тип файла. Допускается только *.txt и *.csv");
        }
        return;
    }

    getElement("orponing-file").classList.add("disabled");
    startProcessing("div-form-file", "Обработка запроса...");
    hideElement("result-file");
    try {
        const reader = new FileReader();
        reader.readAsText(file, "UTF-8");
        reader.onload = readerEvent => { orponingFile(readerEvent.target.result); }
    } catch (e) {
        notifyError(e);
        stopProcessing("div-form-file");
        getElement("orponing-file").classList.remove("disabled");
    }
}

getElement("tab-orponing-address").onclick = async () => {
    displayElement("div-form-address");
    hideElement("div-form-file");

    hideElement("result-file");
    if (getElement("gid").value) {
        displayElement("result");
    }
}

getElement("tab-orponing-file").onclick = async () => {
    hideElement("div-form-address");
    displayElement("div-form-file");

    hideElement("result");
    if (list.length > 0) {
        displayElement("result-file");
    }
}

getElement("input-address").addEventListener("keyup", (event) => {
    if (event.keyCode === 13) {
        event.preventDefault();
        getElement("orponing-address").click();
    }
})

function startProcessing(id, message) {
    const p = getElement(id).querySelector("div.processing");

    if (p) return;

    const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${message}</h5>
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                </div>`;

    getElement(id).insertAdjacentHTML('beforeend', proc);
}

function stopProcessing(id) {
    const p = getElement(id).querySelector("div.processing");

    if (p) {
        p.remove();
    }
}

function checkTypeFile(file) {
    if (file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv"))) return true;
    return false;
}

function convertStringToAddress(data) {
    list = [];
    if (data) {
        data.split("\r\n").forEach(element => {
            if (element) {
                const a = element.split(";");
                list.push({ Id: a[0], Address: a[1] });
            }
        });
    }

    return list;
}

function convertAddressInfoToString(addressInfo) {
    let dataForSave = "data:application/txt;charset=utf-8,%EF%BB%BF";

    const data = [];
    data.push("id;Address;GlobalId;AddressOrpon;ParsingLevelCode;QualityCode;UnparsedParts;Error");

    addressInfo.forEach(el => {
        data.push(`${el.Id};${list.find(e => e.Id == el.Id).Address};${el.GlobalId ?? ""};${el.AddressOrpon ?? ""};${el.ParsingLevelCode ?? ""};${el.QualityCode ?? ""};${el.UnparsedParts ?? ""};${el.Error ?? ""}`);
    });

    dataForSave += encodeURIComponent(data.join("\r\n"));

    return dataForSave;
}

function getDownloadFile() {
    const downloadFile = document.createElement("a");
    downloadFile.className = "btn btn-primary";
    downloadFile.download = `load.csv`;
    downloadFile.textContent = "Скачать";

    return downloadFile;
}

async function orponingFile(data) {
    try {
        list = convertStringToAddress(data);

        const result = await apiOrponingListAddress(list);

        const downloadFile = getDownloadFile();

        const dataForSave = convertAddressInfoToString(result);

        downloadFile.href = dataForSave;

        if (getElement("result-file>a")) {
            getElement("result-file>a").remove();
        }

        displayElement("result-file");
        getElement("result-file").appendChild(downloadFile);
    } catch (e) {
        notifyError(e);
        list = [];
    } finally {
        stopProcessing("div-form-file");
        getElement("orponing-file").classList.remove("disabled");
    }
}