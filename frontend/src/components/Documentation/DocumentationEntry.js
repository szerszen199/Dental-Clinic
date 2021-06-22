export class DocumentationEntry {

    constructor(creationTime, modificationTime, wasDone, toBeDone, id, version, doctorLogin, etag) {
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.wasDone = wasDone;
        this.toBeDone = toBeDone;
        this.id = id;
        this.version = version;
        this.doctorLogin = doctorLogin;
        this.etag = etag;
    }

}