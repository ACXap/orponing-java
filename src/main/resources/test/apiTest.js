"use strict"
async function apiGetLog() {
    const response = await fetch("/log", {
        method: 'POST',
        headers: _getHeadersPost()
    });
    return await _getText(response);
}

async function apiGetLogFile(file) {
    const response = await fetch("/log/read?file=" + file, {
        method: 'GET',
        headers: _getHeadersPost()
    });
    return _getText(response);
}

async function apiClearArchive(password) {
    const response = await fetch("/log/clear?password=" + password);
    return _getJson(response);
}

async function apiGetListServices() {
    const response = await fetch("/orponing_service/all_services");
    return _getJson(response);
}

async function apiStartService(id) {
    const response = await fetch(`/orponing_service/${id}/start`);
    return _getJson(response);
}

async function apiGetStatusService(id) {
    const response = await fetch("/orponing_service/status?service=" + id);
    return _getJson(response);
}

async function apiOrponingAddress(address) {
    return { GlobalId: 12345678, AddressOrpon: "TestAddress", IsValid: true, ParsingLevelCode: "TestLevel", UnparsedParts: "TestParts", QualityCode: "TestCode", CheckStatus: "TestStatus" }
}

async function apiOrponingListAddress(listAddress) {
    return "taskId"
}

async function apiGetStatusTask(id) {
    return { status: "COMPLETED" }
}

async function apiGetResultTask(id) {
    return [{ Id: 1, GlobalId: 12345678, AddressOrpon: "TestAddress", IsValid: true, ParsingLevelCode: "TestLevel", UnparsedParts: "TestParts", QualityCode: "TestCode", CheckStatus: "TestStatus" },
    { Id: 2, GlobalId: 87654321, AddressOrpon: "TestAddress2", IsValid: true, ParsingLevelCode: "TestLevel2", UnparsedParts: "TestParts2", QualityCode: "TestCode2", CheckStatus: "TestStatus2" },
    { Id: 3, GlobalId: 11111111, AddressOrpon: "TestAddress3", IsValid: true, ParsingLevelCode: "TestLevel3", UnparsedParts: "TestParts3", QualityCode: "TestCode3", CheckStatus: "TestStatus3" }];
}

async function _getJson(response) {
    if (response.status == 200) {
        return await response.json();
    }

    throw new Error(response.statusText);
}

async function _getText(response) {
    if (response.status == 200) {
        return await response.text();
    }

    throw new Error(response.statusText);
}