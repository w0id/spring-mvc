package ru.gb.repository;

import ru.gb.model.Product;

import java.util.Random;

public class ProductRepositoryNameService {

    private static long productSequence = 1L;

    public void InitName(Product product) {
        Random random = new Random();
        product.setId(productSequence++);
        product.setName("Товар №" + random.nextInt(99));
        product.setCost(Math.round(100000d * random.nextDouble()) / 100d);
    }
}