list = [];

getElement("orponing-address").onclick = async () => {
    try {
        const address = getElement("input-address").value;

        if (address) {
            disableElement("orponing-address");
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
    if (!checkTypeFile(file)) {
        if (!file) {
            alert("А кто файл то будет добавлять?");
        } else {
            alert("Неверный тип файла. Допускается только *.txt и *.csv");
        }
        return;
    }

    disableElement("orponing-file");
    startProcessing("div-form-file", "Обработка запроса...");
    hideElement("result-file");
    try {
        const reader = new FileReader();
        reader.readAsBinaryString(file);

        reader.onload = function (e) {
            try {
                orponingFile(decodeURIComponent(escape(e.target.result)));
            } catch {
                const reader = new FileReader();
                reader.readAsText(file, "windows-1251");
                reader.onload = readerEvent => { orponingFile(readerEvent.target.result); }
            }
        }
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
    return !file || file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv"));
}

function convertStringToAddress(data) {
    list = [];
    if (data) {
        const rows = data.split(/\r\n|\n/);

        if (rows[0].split(";").length > 1) {
            for (const row of rows) {
                const items = row.split(";");
                list.push({ Id: items[0], Address: items[1] });
            }
        } else {
            let index = 1;
            for (const row of rows) {
                list.push({ Id: index++, Address: row });
            }
        }
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

async function requestTask(id) {
    try {
        console.log("get status task id = " + idTask);

        let result = await apiGetStatusTask(idTask);
        console.log(result);

        if (result.status === "COMPLETED") {

            result = await apiGetResultTask(idTask);
            const downloadFile = getDownloadFile();
            downloadFile.href = convertAddressInfoToString(result);
            removeElement("result-file>a");

            displayElement("result-file");
            getElement("result-file").appendChild(downloadFile);

            stopProcessing("div-form-file");
            getElement("orponing-file").classList.remove("disabled");
        } else {
            timerId = setTimeout(() => requestTask(id), 2000);
        }
    } catch (e) {
        notifyError(e);
        stopProcessing("div-form-file");
        getElement("orponing-file").classList.remove("disabled");
    }
}

async function orponingFile(data) {
    try {
        list = convertStringToAddress(data);

        idTask = await apiOrponingListAddress(list);

        setTimeout(() => requestTask(idTask), 2000);
    } catch (e) {
        notifyError(e);
        list = [];

        stopProcessing("div-form-file");
        getElement("orponing-file").classList.remove("disabled");
    }
}