document.querySelector("#button-orponing").onclick = async () => {
    const address = document.querySelector("#input-address").value;
    if(address) {
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