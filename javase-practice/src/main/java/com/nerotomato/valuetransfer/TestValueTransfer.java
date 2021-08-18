package com.nerotomato.valuetransfer;

/**
 * 测试JAVA的值传递
 *
 * @Created by nerotomato on 2021/8/18.
 */
public class TestValueTransfer {
    public static void main(String[] args) {
        int age = 26;
        String username = "nero";

        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        System.out.println("Before change: " + user);

        /**
         * Java中只有值传递。
         * 值传递是指在调用函数时将实际参数复制一份传递到函数中，这样在函数中如果对参数进行修改，将不会影响到实际参数。
         *
         * 引用传递是指在调用函数时将实际参数的地址传递到函数中，那么在函数中对参数所进行的修改，将影响到实际参数。
         * 值传递是传递实参副本，函数修改不会影响实参；引用传递是传递实参地址，函数修改会影响实参。
         * 区分值传递还是引用传递的关键在于实参是否被函数所修改，对于user对象来说地址才是实参
         */
        change(user, username, age);

        System.out.println("username:" + username);
        System.out.println("age:" + age);
        System.out.println("After change:" + user);
    }

    public static void change(User user, String username, int age) {
        //值传递是传递实参副本，函数修改不会影响实参
        // 这里user变量副本的实参是指它指向的地址
        // username变量副本的实参是指它指向的地址，所以username = "dante";变量副本地址变了，但是修改不影响main函数的源变量username
        // age变量副本就是age的值拷贝了一份 ,所以对age变量的修改不影响main函数的age
        username = "dante";
        age = 220;
        // 这里user变量副本指向的地址没变，跟main函数里面的对象是一个地址，所以修改的是同一个对象
        user.setUsername(username);
        user.setAge(age);

        user = new User(); // user变量副本的地址变了，指向了新的对象地址,后续的修改只对新对象有效，不影响原来的user对象
        user.setAge(250);
        user.setUsername("vergil");
    }

    /**
     * 静态内部类，定义在类中，任何方法外，用static定义；静态内部类只能访问外部类的静态成员
     */
    static class User {
        private int age;
        private String username;

        public int getAge() {
            return age;
        }

        public String getUsername() {
            return username;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "User{HashCode=" + this.hashCode() +
                    ", age=" + age +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}
