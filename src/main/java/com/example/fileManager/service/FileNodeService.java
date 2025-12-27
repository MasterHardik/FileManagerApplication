package com.example.fileManager.service;

import com.example.fileManager.model.FileNode;

import java.util.List;

public interface FileNodeService {
    List<FileNode> getAllNodes();
    FileNode getNodeById(Long id);
    FileNode createNode(FileNode node);
    FileNode renameNode(Long id, String newName);
    void deleteNode(Long id);
}