"use strict"
export default class Api {
    async apiGetLog() {
        return "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi eligendi esse repudiandae consequuntur blanditiis ea aut neque doloribus odit exercitationem provident natus fuga, laborum nisi cupiditate enim eum fugiat tempore?";
    }

    async apiGetLogFile(file) {
        return "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi eligendi esse repudiandae consequuntur blanditiis ea aut neque doloribus odit exercitationem provident natus fuga, laborum nisi cupiditate enim eum fugiat tempore?Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi eligendi esse repudiandae consequuntur blanditiis ea aut neque doloribus odit exercitationem provident natus fuga, laborum nisi cupiditate enim eum fugiat tempore?Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi eligendi esse repudiandae consequuntur blanditiis ea aut neque doloribus odit exercitationem provident natus fuga, laborum nisi cupiditate enim eum fugiat tempore?Lorem ipsum dolor sit amet consectetur adipisicing elit. Quasi eligendi esse repudiandae consequuntur blanditiis ea aut neque doloribus odit exercitationem provident natus fuga, laborum nisi cupiditate enim eum fugiat tempore?";
    }

    async apiClearArchive(password) {
        return { status: "COMPLETED" }
    }

    async apiGetListServices() {
        return [{ id: "service-test1", icon: "server", name: "Service1", description: "Test1" }, { id: "service-test2", icon: "server", name: "Service2", description: "Test2" }, { id: "service-test3", icon: "server", name: "Service3", description: "Test3" }];
    }

    async apiStartService(id) {
        const response = await fetch(`/orponing_service/${id}/start`);
        return _getJson(response);
    }

    async apiGetStatusService(id) {
        return { idService: id, status: "START", message: "Test", date: "20210101" }
    }

    async apiOrponingAddress(address) {
        return { GlobalId: 12345678, AddressOrpon: "TestAddress", IsValid: true, ParsingLevelCode: "TestLevel", UnparsedParts: "TestParts", QualityCode: "TestCode", CheckStatus: "TestStatus" }
    }

    async apiOrponingListAddress(listAddress) {
        return (1000 - 0.5 + Math.random() * (10000 - 1000 + 1)).toString();
    }

    async apiGetStatusTask(id) {
        return { status: "COMPLETED" }
    }

    async apiGetResultTask(id) {
        return [{ Id: 1, GlobalId: 12345678, AddressOrpon: "TestAddress", IsValid: true, ParsingLevelCode: "TestLevel", UnparsedParts: "TestParts", QualityCode: "TestCode", CheckStatus: "TestStatus" },
        { Id: 2, GlobalId: 87654321, AddressOrpon: "TestAddress2", IsValid: true, ParsingLevelCode: "TestLevel2", UnparsedParts: "TestParts2", QualityCode: "TestCode2", CheckStatus: "TestStatus2" },
        { Id: 3, GlobalId: 11111111, AddressOrpon: "TestAddress3", IsValid: true, ParsingLevelCode: "TestLevel3", UnparsedParts: "TestParts3", QualityCode: "TestCode3", CheckStatus: "TestStatus3" }];
    }
}