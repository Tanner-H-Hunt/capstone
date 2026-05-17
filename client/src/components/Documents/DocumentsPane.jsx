import DirectoryDisplay from "./DirectoryDisplay";
import DocumentFilters from "./DocumentFilters";
import DocumentPreviewTable from "./DocumentPreviewTable";

function DocumentsPane({ documents, setDocuments, directoryStack, setDirectoryStack }){
    return (
        <>
            <DirectoryDisplay />
            <DocumentFilters />
            
            <div className="mt-3">
                <DocumentPreviewTable documents={documents} setDocuments={setDocuments} directoryStack={directoryStack} setDirectoryStack={setDirectoryStack}/>
            </div>
        </>
    );
}

export default DocumentsPane;