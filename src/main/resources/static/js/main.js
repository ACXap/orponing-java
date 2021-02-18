listAddressOfFile = [];
listAddressOfClipboard = [];

getElement("orponing-address").onclick = async () => {
    try {
        const address = getElement("input-address").value;

        if (address) {
            startProcessing("div-form-address", "Обработка запроса...");

            const json = await apiOrponingAddress(address);

            if (json) {
                displayElement("result-address");
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
    }
}

getElement("orponing-file").onclick = () => {
    const file = getElement("formFile").files[0];
    if (!isValidFile(file)) {
        if (!file) {
            alert("А кто файл то будет добавлять?");
        } else {
            alert("Неверный тип файла. Допускается только *.txt и *.csv");
        }
        return;
    }

    startProcessing("div-form-file", "Обработка запроса...");
    hideElement("result-file");

    readFileUtfEncoding(file);
}

getElement("orponing-clipboard").onclick = () => {
    if (!listAddressOfClipboard || listAddressOfClipboard.length == 0) return;

    startProcessing("div-form-clipboard", "Обработка запроса...");
    hideElement("result-clipboard");

    orponingListAddress(listAddressOfClipboard, "div-form-clipboard");
}

getElement("formClipboard").onclick = async () => {
    try {
        if (navigator.clipboard) {
            const data = await navigator.clipboard.readText();
            listAddressOfClipboard = convertStringToAddress(data);
            getElement("div-form-clipboard>div.count-address").textContent = "Всего записей: " + list.length;
        }
    } catch (e) {
        getElement("div-form-clipboard>div.count-address").textContent = "Всего записей: 0";
        notifyError(e);
    }
}

getElement("input-address").addEventListener("keyup", e => {
    if (e.keyCode != 13) return;
    e.preventDefault();
    getElement("orponing-address").click();
})

function readFileUtfEncoding(file) {
    try {
        const reader = new FileReader();
        reader.readAsBinaryString(file);

        reader.onload = function (e) {

            let data = "";

            try {
                data = decodeURIComponent(escape(e.target.result));
            } catch (error) {
                readFileOtherEncoding(file);
                return;
            }

            orponingFileData(data);
        }
    } catch (e) {
        stopError(e, "div-form-file");
    }
}

function readFileOtherEncoding(file) {
    try {
        const reader = new FileReader();
        reader.readAsText(file, "windows-1251");
        reader.onload = readerEvent => orponingFileData(readerEvent.target.result);
    } catch (e) {
        stopError(e, "div-form-file");
    }
}

function orponingFileData(data) {
    listAddressOfFile.length = 0;
    listAddressOfFile = convertStringToAddress(data);
    getElement("div-form-file>div.count-address").textContent = "Всего записей: " + listAddressOfFile.length;

    if (listAddressOfFile.length > 0) {
        orponingListAddress(listAddressOfFile, "div-form-file");
    }
}

function isValidFile(file) {
    return file && (file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv")));
}

function convertStringToAddress(data) {
    list = [];

    try {
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
    } catch (e) {
        notifyError(e);
    } finally {
        return list;
    }
}

function convertAddressInfoToString(addressInfo) {
    let dataForSave = "data:application/txt;charset=utf-8,%EF%BB%BF";

    const data = [];
    data.push("id;Address;GlobalId;AddressOrpon;ParsingLevelCode;QualityCode;UnparsedParts;Error");

    addressInfo.forEach(el => {
        data.push(`${el.Id};${listAddressOfFile.find(e => e.Id == el.Id).Address};${el.GlobalId ?? ""};${el.AddressOrpon ?? ""};${el.ParsingLevelCode ?? ""};${el.QualityCode ?? ""};${el.UnparsedParts ?? ""};${el.Error ?? ""}`);
    });

    dataForSave += encodeURIComponent(data.join("\r\n"));

    return dataForSave;
}

async function orponingListAddress(list, idForm) {
    try {
        idTask = await apiOrponingListAddress(list);
        setTimeout(() => requestTask(idTask, idForm), 2000);
    } catch (e) {
        stopError(e, idForm);
    }
}

async function requestTask(idTask, idForm) {
    try {
        let result = await apiGetStatusTask(idTask);

        if (result.status === "COMPLETED") {

            result = await apiGetResultTask(idTask);
            addDownLoadLink(idForm, convertAddressInfoToString(result));
            stopProcessing(idForm);
        } else {
            timerId = setTimeout(() => requestTask(idTask), 5000);
        }
    } catch (e) {
        stopError(e, idForm);
    }
}