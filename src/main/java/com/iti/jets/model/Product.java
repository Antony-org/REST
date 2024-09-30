package com.iti.jets.model;

import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;

@XmlRootElement
public class Product implements Serializable {
    private Long id;
    private String name;
    private double price;
    private final Set<Link> links = new HashSet<>();


    public Product() {}
    
    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlElement(name = "links")
    public Set<Link> getLinks() {
        return links;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", links=" + links + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId() == product.getId() && Double.compare(getPrice(), product.getPrice()) == 0 && Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    public static class Link {
        private String rel;
        private String href;

        public Link() {
        }

        public Link(String rel, String href) {
            this.rel = rel;
            this.href = href;
        }

        @XmlElement
        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        @XmlElement
        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        @Override
        public String toString() {
            return "Link{" + "rel='" + rel + '\'' + ", href='" + href + '\'' + '}';
        }
    }
}