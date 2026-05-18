import DocumentPreview from "./DocumentPreview";

function DocumentPreviewTable({ documents, setDocuments, directoryStack, setDirectoryStack }){
    function removeDocument(id){
        const documentsClone = [...documents].filter(doc => doc.id != id);

        setDocuments(documentsClone);

    }

    return (
        <>
            <div className="flex-container">
                <div className="row">
                    {
                        documents.map(doc => {
                        return(
                            <div className="col-sm-6 col-lg-4 col-xl-3 col-xxl-2" key={doc.id}>
                                <DocumentPreview document={doc} removeDocument={removeDocument}/>
                            </div>
                        )
                    })}

                </div>

            </div>
        </>
    );
}

export default DocumentPreviewTable;