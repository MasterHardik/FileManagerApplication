document.addEventListener("DOMContentLoaded", function () {
    const treeContainer = document.getElementById("file-tree");

    function fetchTree() {
        fetch("/api/files/tree")
            .then(res => res.json())
            .then(data => {
                treeContainer.innerHTML = "";
                renderTree(data, treeContainer);
            });
    }

    function renderTree(nodes, parent) {
        const ul = document.createElement("ul");

        nodes.forEach(node => {
            const li = document.createElement("li");
            li.textContent = node.name;

            const actions = document.createElement("span");
            actions.style.marginLeft = "10px";

            // + only for folders
            if (node.type === "FOLDER") {
                const addBtn = document.createElement("button");
                addBtn.textContent = "+";
                addBtn.onclick = () => {
                    const name = prompt("Name:");
                    const type = prompt("folder/file").toUpperCase();
                    fetch("/api/files", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ name, type, parent: { id: node.id } })
                    }).then(fetchTree);
                };
                actions.appendChild(addBtn);
            }

            const delBtn = document.createElement("button");
            delBtn.textContent = "-";
            delBtn.onclick = () => {
                if (confirm("Delete?")) {
                    fetch(`/api/files/${node.id}`, { method: "DELETE" })
                        .then(fetchTree);
                }
            };
            actions.appendChild(delBtn);

            li.appendChild(actions);

            if (node.children && node.children.length > 0) {
                renderTree(node.children, li);
            }

            ul.appendChild(li);
        });

        parent.appendChild(ul);
    }

    fetchTree();
});



//document.addEventListener("DOMContentLoaded", function () {
//    const treeContainer = document.getElementById("file-tree");
//
//    // Fetch and render tree
//    function fetchTree() {
//        fetch("/api/files")
//            .then(response => response.json())
//            .then(data => {
//                treeContainer.innerHTML = "";
//                renderTree(data, treeContainer);
//            });
//    }
//
//    // Recursive tree rendering
//    function renderTree(nodes, parentElement) {
//        const ul = document.createElement("ul");
//        nodes.forEach(node => {
//            const li = document.createElement("li");
//            li.textContent = node.name;
//            li.dataset.id = node.id;
//
//            // Buttons container
//            const actions = document.createElement("span");
//            actions.style.marginLeft = "10px";
//
//            // Add button (+)
//            const addBtn = document.createElement("button");
//            addBtn.textContent = "+";
//            addBtn.onclick = () => {
//                const newName = prompt("Enter new folder/file name:");
//                if (!newName) return;
//                const type = prompt("Enter type: folder or file:").toLowerCase() === "folder" ? "FOLDER" : "FILE";
//                fetch("/api/files", {
//                    method: "POST",
//                    headers: { "Content-Type": "application/json" },
//                    body: JSON.stringify({ name: newName, type: type, parent: { id: node.id } })
//                }).then(fetchTree);
//            };
//            actions.appendChild(addBtn);
//
//            // Delete button (-)
//            const delBtn = document.createElement("button");
//            delBtn.textContent = "-";
//            delBtn.onclick = () => {
//                if (confirm(`Are you sure you want to delete "${node.name}"?`)) {
//                    fetch(`/api/files/${node.id}`, { method: "DELETE" })
//                        .then(fetchTree);
//                }
//            };
//            actions.appendChild(delBtn);
//
//            // Rename button
//            const renameBtn = document.createElement("button");
//            renameBtn.textContent = "Rename";
//            renameBtn.onclick = () => {
//                const newName = prompt("Enter new name:", node.name);
//                if (!newName) return;
//                fetch(`/api/files/${node.id}`, {
//                    method: "PUT",
//                    headers: { "Content-Type": "application/json" },
//                    body: JSON.stringify({ name: newName })
//                }).then(fetchTree);
//            };
//            actions.appendChild(renameBtn);
//
//            li.appendChild(actions);
//
//            // Render children if exist
//            if (node.children && node.children.length > 0) {
//                renderTree(node.children, li);
//            }
//
//            ul.appendChild(li);
//        });
//        parentElement.appendChild(ul);
//    }
//
//    fetchTree();
//});
