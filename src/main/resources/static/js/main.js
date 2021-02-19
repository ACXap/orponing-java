listAddressOfFile = [];
listAddressOfClipboard = [];

async function orponingAddress(address) {
    const json = await apiOrponingAddress(address);
    return json;
}

async function orponingFile(callBack) {
    if (!listAddressOfFile || listAddressOfFile.length === 0) {
        callBack();
        return;
    }

    orponingListAddress(listAddressOfFile, callBack);
}

async function orponingClipboard(callBack) {
    if (!listAddressOfClipboard || listAddressOfClipboard.length === 0) {
        callBack();
        return;
    }

    orponingListAddress(listAddressOfClipboard, callBack);
}

function initListAddressOfClipboard(data) {
    try {
        listAddressOfClipboard = convertStringToAddress(data);
        return { count: listAddressOfClipboard.length, error: null, previewList: listAddressOfClipboard.slice(0, 9) };
    } catch (e) {
        listAddressOfClipboard.length = 0;
        return { count: 0, error: e };
    }
}

function initListAddressOfFile(file, callBack) {
    if (!isValidFile(file)) {
        const message = !file ? "А кто файл то будет добавлять?" : "Неверный тип файла. Допускается только *.txt и *.csv";
        callBack({ count: 0, error: message })
        return;
    }

    readFileUtfEncoding(file, callBack);
}

function readFileUtfEncoding(file, callBack) {
    try {
        const reader = new FileReader();
        reader.readAsBinaryString(file);

        reader.onload = function (e) {
            try {
                const data = decodeURIComponent(escape(e.target.result));
                convertFileDataToAddress(data, callBack);
            } catch (error) {
                readFileOtherEncoding(file, callBack);
                return;
            }
        }
    } catch (e) {
        callBack({ count: 0, error: e });
    }
}

function readFileOtherEncoding(file, callBack) {
    try {
        const reader = new FileReader();
        reader.readAsText(file, "windows-1251");
        reader.onload = readerEvent => convertFileDataToAddress(readerEvent.target.result, callBack);
    } catch (e) {
        callBack({ count: 0, error: e });
    }
}

function convertFileDataToAddress(data, callBack) {
    try {
        listAddressOfFile = convertStringToAddress(data);
        callBack({ count: listAddressOfFile.length, error: null, previewList: listAddressOfFile.slice(0, 10) });
    } catch (e) {
        listAddressOfFile.length = 0;
        callBack({ count: 0, error: e });
    }
}

function isValidFile(file) {
    return file && (file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv")));
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

function convertAddressInfoToString(addressInfo, list) {
    let dataForSave = "data:application/txt;charset=utf-8,%EF%BB%BF";

    const data = [];
    data.push("id;Address;GlobalId;AddressOrpon;ParsingLevelCode;QualityCode;UnparsedParts;Error");

    addressInfo.forEach(el => {
        data.push(`${el.Id};${list.find(e => e.Id == el.Id).Address};${el.GlobalId ?? ""};${el.AddressOrpon ?? ""};${el.ParsingLevelCode ?? ""};${el.QualityCode ?? ""};${el.UnparsedParts ?? ""};${el.Error ?? ""}`);
    });

    dataForSave += encodeURIComponent(data.join("\r\n"));

    return dataForSave;
}

async function orponingListAddress(list, callBack) {
    try {
        idTask = await apiOrponingListAddress(list);
        setTimeout(() => requestTask(idTask, list, callBack), 2000);
    } catch (e) {
        callBack("", e);
    }
}

async function requestTask(idTask, list, callBack) {
    try {
        let result = await apiGetStatusTask(idTask);

        if (result.status === "COMPLETED") {
            result = await apiGetResultTask(idTask);
            callBack(convertAddressInfoToString(result, list));
        } else {
            timerId = setTimeout(() => requestTask(idTask, list, callBack), 5000);
        }
    } catch (e) {
        callBack("", e);
    }
}