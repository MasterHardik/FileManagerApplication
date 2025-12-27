package com.example.fileManager.service;

import com.example.fileManager.dto.FileNodeDto;
import com.example.fileManager.model.FileNode;

import java.util.List;

public interface FileNodeService {
    List<FileNode> getAllNodes();
    FileNode getNodeById(Long id);
    FileNode createNode(FileNode node);
    FileNode createNodeParent(FileNode dto);
    FileNode renameNode(Long id, String newName);
    void deleteNode(Long id);
    }