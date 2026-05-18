import DocumentPreview from "./DocumentPreview";

function DocumentPreviewTable({ documents, setDocuments, directoryStack, setDirectoryStack }){
    return (
        <>
            <div className="flex-container">
                <div className="row">
                    {
                        documents.map(doc => {
                        return(
                            <div className="col-sm-6 col-lg-4 col-xl-3 col-xxl-2" key={doc.id}>
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