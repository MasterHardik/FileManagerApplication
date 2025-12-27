package com.example.fileManager.model;

import jakarta.persistence.*;

@Entity
public class FileNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private NodeType type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FileNode parent;

    public FileNode() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public NodeType getType() { return type; }
    public void setType(NodeType type) { this.type = type; }

    public FileNode getParent() { return parent; }
    public void setParent(FileNode parent) { this.parent = parent; }
}