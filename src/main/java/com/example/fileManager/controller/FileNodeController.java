package com.example.fileManager.controller;

import com.example.fileManager.dto.FileNodeDto;
import com.example.fileManager.model.FileNode;
import com.example.fileManager.service.FileNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileNodeController {

    @Autowired
    private FileNodeService fileNodeService;

    // 🔥 TREE ENDPOINT (UI USES THIS)
    @GetMapping("/tree")
    public List<FileNodeDto> getFileTree() {
        return fileNodeService.getFileTree();
    }

    // CRUD APIs
    @PostMapping
    public FileNode createFile(@RequestBody FileNode node) {
        return fileNodeService.createNode(node);
    }

    @PutMapping("/{id}")
    public FileNode updateFile(@PathVariable Long id, @RequestBody FileNode node) {
        return fileNodeService.renameNode(id, node.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileNodeService.deleteNode(id);
    }
}