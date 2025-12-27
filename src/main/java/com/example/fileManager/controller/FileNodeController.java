package com.example.fileManager.controller;

import com.example.fileManager.model.FileNode;
import com.example.fileManager.service.FileNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileNodeController {

    @Autowired
    private FileNodeService fileNodeService;

    // Get all nodes as tree
    @GetMapping
    public List<FileNode> getAllFiles() {
        return fileNodeService.getAllNodes();
    }

    // Get single node
    @GetMapping("/{id}")
    public FileNode getFile(@PathVariable Long id) {
        return fileNodeService.getNodeById(id);
    }

    // Create new file/folder
    @PostMapping
    public FileNode createFile(@RequestBody FileNode node) {
        return fileNodeService.createNode(node);
    }

    // Update node (rename)
    @PutMapping("/{id}")
    public FileNode updateFile(@PathVariable Long id, @RequestBody FileNode node) {
        return fileNodeService.renameNode(id, node.getName());
    }

    // Delete node
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileNodeService.deleteNode(id);
    }

    @PostMapping("/api/parent")
    public ResponseEntity<FileNode> createFileNodeParent(@RequestBody FileNode dto) {
        FileNode createdNode = fileNodeService.createNodeParent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNode);
    }

}
