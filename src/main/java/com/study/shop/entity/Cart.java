package com.study.shop.entity;

import java.util.HashMap;

public class Cart {
    private HashMap<Integer, Integer> productList = new HashMap();

    public void addProduct(int id, int count) {
        Integer key = id;
        Integer value = productList.get(key);
        if (value != null) {
            value += count;
        } else {
            value = count;
        }
        productList.put(key, value);
    }

    public int getCountById(int id) {
        Integer key = id;
        return productList.get(key);
    }

    public void deleteProduct(int id) {
        Integer key = id;
        productList.remove(key);
    }
    public void decreaseCount(int id) {
        Integer key = id;
        Integer value = productList.get(key);
        if (value != null) {
            if(value > 1) {
                value--;
                productList.put(key, value);
            }
            else {
                deleteProduct(id);
            }
        }

    }

    public void clear() {
        productList.clear();
    }

    public int getItemCount() {
        int result = 0;
        for (Integer value : productList.values()) {
            result += value;
        }

        return result;
    }

    public HashMap<Integer, Integer> getProductList() {
        return productList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productList=" + productList +
                '}';
    }
}
