package com.example.fileManager.service;

import com.example.fileManager.dto.FileNodeDto;
import com.example.fileManager.model.FileNode;
import com.example.fileManager.repository.FileNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileNodeServiceImpl implements FileNodeService {

    @Autowired
    private FileNodeRepository repository;

    @Override
    public List<FileNode> getAllNodes() {
        return repository.findAll();
    }

    @Override
    public FileNode getNodeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Node not found"));
    }

    @Override
    public FileNode createNode(FileNode node) {
        return repository.save(node);
    }

    @Override
    public FileNode renameNode(Long id, String newName) {
        FileNode node = getNodeById(id);
        node.setName(newName);
        return repository.save(node);
    }

    @Override
    public void deleteNode(Long id) {
        // Recursive delete of children if any
        deleteChildren(id);
        repository.deleteById(id);
    }

    private void deleteChildren(Long parentId) {
        List<FileNode> children = repository.findByParentId(parentId);
        for (FileNode child : children) {
            deleteChildren(child.getId());
            repository.delete(child);
        }
    }

    public FileNode createNodeParent(FileNode dto) {
        FileNode node = new FileNode();
        node.setName(dto.getName());
        node.setType(dto.getType());

        if (dto.getParent() != null && dto.getParent().getId() != null) {
            FileNode parent = repository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
            node.setParent(parent);
        } else {
            // Root folder: no parent
            node.setParent(null);
        }

        return repository.save(node);
    }

}
