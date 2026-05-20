import Note from "../shapes/Note";

function NotesScene({ notes, addNote }){
    function createNewNote(){
        addNote("NOTE");
    }

    return (
        <div>

            {notes != undefined ? 
                notes.map(note => {
                    return (
                        <Note attributes={note} key={note.documentElementId}/>
                    );
                })
            
                : 
            <></>}
            <div className="text-center">
                <button className="btn btn-secondary" onClick={createNewNote}>New note</button>

            </div>
        </div>
    );
}

export default NotesScene;