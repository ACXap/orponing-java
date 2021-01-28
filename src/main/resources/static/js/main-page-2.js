list = [];

function getElement(id) {
    return document.querySelector(`#${id}`);
}

document.querySelector("#orponing-address").onclick = async () => {
    const address = document.querySelector("#input-address").value;
    if (address) {
        const response = await fetch("/get_globalid?address=" + address);
        const json = await response.json();

        if (json) {
            getElement("result").hidden = false;
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
}

getElement("input-address").addEventListener("keyup", (event) => {
    if (event.keyCode === 13) {
        event.preventDefault();
        getElement("orponing-address").click();
    }
})

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

    getElement("await-file").hidden = false;
    getElement("result-file").hidden = false;
    getElement("div-file-load").hidden = true;

    const reader = new FileReader();
    reader.readAsText(file, "UTF-8");
    reader.onload = readerEvent => { orponingFile(readerEvent.target.result); }
}

getElement("button-orponing-address").onclick = async () => {
    getElement("div-form-address").hidden = false;
    getElement("div-form-file").hidden = true;

    getElement("result-file").hidden = true;
    if (getElement("gid").value) {
        getElement("result").hidden = false;
    }
}

getElement("button-orponing-file").onclick = async () => {
    getElement("div-form-address").hidden = true;
    getElement("div-form-file").hidden = false;

    getElement("result").hidden = true;
    if (list.length > 0) {
        getElement("result-file").hidden = false;
    }
}

function checkTypeFile(file) {
    if (file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv"))) return true;
    return false;
}

function convertDataToAddress(data) {
    list = [];
    if (data) {
        data.split("\r\n").forEach(element => {

            if (element) {
                a = element.split(";");
                list.push({ Id: a[0], Address: a[1] });
            }
        });
    }

    return list;
}

async function getAddressInfo(list) {
    const response = await fetch("/get_globalid", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(list)
    });

    const result = await response.json();

    return result;
}

function getDownloadFile() {
    downloadFile = document.createElement("a");
    downloadFile.className = "btn btn-primary text-center";
    downloadFile.download = `load.csv`;
    downloadFile.textContent = "Скачать";

    return downloadFile;
}

function convertAddressInfoToString(addressInfo) {
    dataForSave = "data:application/txt;charset=utf-8,%EF%BB%BF";

    data = [];
    data.push("id;Address;GlobalId;AddressOrpon;ParsingLevelCode;QualityCode;UnparsedParts;Error");

    addressInfo.forEach(el => {
        data.push(`${getString(el.Id)};${getString(list.find(e => e.Id == el.Id).Address).replace("\r")};${getString(el.GlobalId)};${getString(el.AddressOrpon)};${getString(el.ParsingLevelCode)};${getString(el.QualityCode)};${getString(el.UnparsedParts)};${getString(el.Error)}`);
    });

    dataForSave += encodeURIComponent(data.join("\r\n"));

    return dataForSave;
}

function getString(data) {
    if (data) {
        return data;
    }

    return "";
}

async function orponingFile(data) {
    list = convertDataToAddress(data);

    result = await getAddressInfo(list);

    downloadFile = getDownloadFile();

    dataForSave = convertAddressInfoToString(result);

    downloadFile.href = dataForSave;

    if (getElement("div-file-load>a")) {
        getElement("div-file-load>a").remove();
    }
    getElement("await-file").hidden = true;
    getElement("div-file-load").hidden = false;
    getElement("div-file-load").appendChild(downloadFile);
}