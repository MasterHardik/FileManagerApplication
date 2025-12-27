document.addEventListener("DOMContentLoaded", function () {
    const treeContainer = document.getElementById("file-tree");

    function fetchTree() {
        fetch("/api/files/tree")
            .then(res => res.json())
            .then(data => {
                treeContainer.innerHTML = "";
                renderTree(data, treeContainer);
            })
            .catch(err => console.error("Error fetching tree:", err));
    }

    function renderTree(nodes, parent) {
        if (!nodes || nodes.length === 0) return;

        const ul = document.createElement("ul");

        nodes.forEach(node => {
            const li = document.createElement("li");

            // 1️⃣ Icon
            const icon = document.createElement("span");
            icon.textContent = node.type === "FOLDER" ? "📁 " : "📄 ";
            li.appendChild(icon);

            // 2️⃣ Node name
            const nameSpan = document.createElement("span");
            nameSpan.textContent = node.name;
            li.appendChild(nameSpan);

            // 3️⃣ Actions container
            const actions = document.createElement("span");
            actions.style.marginLeft = "10px";

            if (node.type === "FOLDER") {
                const addBtn = document.createElement("button");
                addBtn.textContent = "+";
                addBtn.onclick = () => {
                    // 1️⃣ Ask type first
                    const typeChoice = prompt("Enter type: folder or file").toLowerCase();
                    if (typeChoice !== "folder" && typeChoice !== "file") {
                        alert("Invalid type! Only 'folder' or 'file' allowed.");
                        return;
                    }
                    const type = typeChoice === "folder" ? "FOLDER" : "FILE";

                    // 2️⃣ Ask name
                    const name = prompt("Enter name:");
                    if (!name) return;

                    fetch("/api/files", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({
                            name: name,
                            type: type,
                            parent: { id: node.id }
                        })
                    }).then(fetchTree);
                };
                actions.appendChild(addBtn);
            }

            // Delete button
            const delBtn = document.createElement("button");
            delBtn.textContent = "-";
            delBtn.onclick = () => {
                if (confirm(`Delete "${node.name}"?`)) {
                    fetch(`/api/files/${node.id}`, { method: "DELETE" })
                        .then(fetchTree);
                }
            };
            actions.appendChild(delBtn);

            li.appendChild(actions);

            // Render children recursively
            if (node.children && node.children.length > 0) {
                renderTree(node.children, li);
            }

            // Add class for CSS styling
            li.className = node.type === "FOLDER" ? "folder" : "file";

            ul.appendChild(li);
        });

        parent.appendChild(ul);
    }

    fetchTree();
});
