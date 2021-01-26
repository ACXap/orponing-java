list = [];

document.querySelector("#orponing-address").onclick = async () => {
    const address = document.querySelector("#input-address").value;
    if (address) {
        const response = await fetch("/get_globalid?address=" + address);
        const json = await response.json();
        if (json) {
            document.querySelector("#result").hidden = false;
            document.querySelector("#gid").value = json.GlobalId;
            document.querySelector("#addressOrpon").value = json.AddressOrpon;
            document.querySelector("#parsingLevelCode").value = json.ParsingLevelCode;
            document.querySelector("#unparsedParts").value = json.UnparsedParts;
            document.querySelector("#qualityCode").value = json.QualityCode;
            document.querySelector("#checkStatus").value = json.CheckStatus;
        }
    }
}

document.querySelector("#orponing-file").onclick = () => {
    document.querySelector("#await-file").hidden = false;
    document.querySelector("#result-file").hidden = false;
    document.querySelector("#div-file-load").hidden = true;

    const file = document.querySelector("#formFile").files[0];
    if (!file && checkTypeFile(file) === false) {
        alert("Неверный тип файла.<br> Допускается только *.txt и *.csv");
        document.querySelector("#await-file").hidden = true;
        return;
    }

    const reader = new FileReader();
    reader.readAsText(file, "UTF-8");
    reader.onload = readerEvent => { orponingFile(readerEvent.target.result); }
}

document.querySelector("#button-orponing-address").onclick = async () => {
    document.querySelector("#div-form-address").hidden = false;
    document.querySelector("#div-form-file").hidden = true;

    document.querySelector("#result-file").hidden = true;
    if (document.querySelector("#gid").value) {
        document.querySelector("#result").hidden = false;
    }
}

document.querySelector("#button-orponing-file").onclick = async () => {
    document.querySelector("#div-form-address").hidden = true;
    document.querySelector("#div-form-file").hidden = false;

    document.querySelector("#result").hidden = true;
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

    addressInfo.AddressInfoError.forEach(el => {
        data.push(`${getString(el.Address.Id)};${getString(el.Address.Address).replace("\r")};;;;;;${getString(el.Error)}`);
    });

    addressInfo.AddressInfoList.forEach(el => {
        data.push(`${getString(el.Id)};${getString(list.find(e => e.Id == el.Id).Address).replace("\r")};${getString(el.GlobalId)};${getString(el.AddressOrpon)};${getString(el.ParsingLevelCode)};${getString(el.QualityCode)};${getString(el.UnparsedParts)};`);
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

    if (document.querySelector("#div-file-load>a")) {
        document.querySelector("#div-file-load>a").remove();
    }
    document.querySelector("#await-file").hidden = true;
    document.querySelector("#div-file-load").hidden = false;
    document.querySelector("#div-file-load").appendChild(downloadFile);
}