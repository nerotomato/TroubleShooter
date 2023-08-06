package com.nerotomato.factorymethod;

public class FactoryMethodDemo {
    public static void main(String[] args) {
        Application application = new Application();
        ProductA productA = application.getObject();
        productA.methodA();
    }
}

class ProductA{
    public void methodA(){
        System.out.println("ProductA methodA executed.");
    }
}

class Application{
    private ProductA createProduct(){
        return new ProductA();
    }

    ProductA getObject(){
        return createProduct();
    }
}