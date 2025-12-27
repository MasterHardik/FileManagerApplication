package com.example.fileManager.repository;

import com.example.fileManager.model.FileNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileNodeRepository extends JpaRepository<FileNode, Long> {
    List<FileNode> findByParentId(Long parentId);
}
