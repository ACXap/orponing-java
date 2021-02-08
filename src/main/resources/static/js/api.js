async function apiGetLog() {
    const response = await fetch("/log", {
        method: 'POST',
        headers: _getHeadersPost()
    });
    return await _getText(response);
}

async function apiOrponingAddress(address) {
    const response = await fetch("/api/get_global_id?address=" + address);
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

async function apiOrponingListAddress(listAddress) {
    const response = await fetch("/api/get_global_id", {
        method: 'POST',
        headers: _getHeadersPost(),
        body: JSON.stringify(listAddress)
    });
    return _getText(response);
}

async function apiGetStatusTask(id) {
    const response = await fetch("/api/get_global_id/status?id=" + id);
    return _getJson(response);
}

async function apiGetResultTask(id) {
    const response = await fetch("/api/get_global_id/result?id=" + id);
    return _getJson(response);
}

async function _getJson(response) {
    const result = await response.json();

    if (response.status == 200) {
        return result;
    }

    throw new Error(result.message);
}

async function _getText(response) {
    const result = await response.text();

    if (response.status == 200) {
        return result;
    }

    throw new Error(result);
}

function _getHeadersPost() {
    return {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept-Encoding': 'gzip, deflate, br',
        'Content-Encoding': 'gzip, deflate, br'
    };
}