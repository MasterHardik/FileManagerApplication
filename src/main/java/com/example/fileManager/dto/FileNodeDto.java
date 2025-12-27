package com.example.fileManager.dto;

import com.example.fileManager.model.NodeType;

import java.util.ArrayList;
import java.util.List;

public class FileNodeDto {
    private Long id;
    private String name;
    private NodeType type;
    private List<FileNodeDto> children = new ArrayList<>();

    public FileNodeDto() {}

    public FileNodeDto(Long id, String name, NodeType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public NodeType getType() { return type; }
    public void setType(NodeType type) { this.type = type; }

    public List<FileNodeDto> getChildren() { return children; }
    public void setChildren(List<FileNodeDto> children) { this.children = children; }
}