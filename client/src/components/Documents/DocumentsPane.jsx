import DirectoryDisplay from "./DirectoryDisplay";
import DocumentFilters from "./DocumentFilters";
import DocumentPreviewTable from "./DocumentPreviewTable";

function DocumentsPane(){
    return (
        <>
            <DirectoryDisplay />
            <DocumentFilters />
            
            <div className="mt-3">
                <DocumentPreviewTable />
            </div>
        </>
    );
}

export default DocumentsPane;