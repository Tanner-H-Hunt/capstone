import DocumentPreview from "./DocumentPreview";

function DocumentPreviewTable({ documents, setDocuments, directoryStack, setDirectoryStack }){
    return (
        <>
            <div className="flex-container">
                <div className="row">
                    {
                        documents.map(doc => {
                        return(
                            <div className="col-4" key={doc.id}>
                                <DocumentPreview document={doc}/>
                            </div>
                        )
                    })}

                </div>

            </div>
        </>
    );
}

export default DocumentPreviewTable;