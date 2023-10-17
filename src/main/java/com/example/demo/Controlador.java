package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
public class Controlador {

    @PostMapping("/register_products")
    public Products register_products(@RequestBody Products products) throws SQLException, ClassNotFoundException {
        String id = UUID.randomUUID().toString();
        id = id.replaceAll("-", "");
        id = id.substring(0, 5);
        String name = products.getName();
        int amount = products.getAmount();
        String description = products.getDescription();
        String category = products.getCategory();

        if (id == null || id.equals("") || id.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                amount <= 0 || description == null || description.equals("") || description.length() < 0 || category == null || category.equals("") ||
                category.length() < 0) {

            return new Products(null, null, 0, null, null);
        } else {
            BD bd = new BD();
            products = bd.register(id, name, amount, description, category);
        }
        return products;
    }

    @PostMapping("/edit_product")
    public Products edit_product(@RequestBody Products products) throws SQLException, ClassNotFoundException {

        String id = products.getId();
        int amount = products.getAmount();

        if (id == null || id.equals("") || id.length() < 0 || amount <= 0) {

            return new Products(null, null, 0, null, null);
        } else {
            BD bd = new BD();
            products = bd.edit(id, amount);
        }
        return products;
    }

    @GetMapping("/search_all")
    public List<Products> search() throws SQLException, ClassNotFoundException {

        BD bd = new BD();
        List<Products> list = bd.search();

        return list;
    }
    @DeleteMapping("/delete_one")
    public Products delete_one (@RequestBody Products products) throws SQLException, ClassNotFoundException {

        String id = products.getId();
        if (products.getId() == null || products.getId().equals("") || products.getId().length() < 0) {
            return new Products(null, null, 0, null, null);
        } else {

            int f = BD.Delete(id);
            if (f == 0) {
                return new Products(Errors.error_delete_one, null, 0, null, null);
            }
        }

        return products;
    }
    @DeleteMapping("/delete_all")
    public String delete_all () throws SQLException, ClassNotFoundException {

        int f = BD.Delete_all();

        return "Se han eliminado todos los productos";
    }
}

