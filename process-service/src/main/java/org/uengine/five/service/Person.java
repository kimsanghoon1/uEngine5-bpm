package org.uengine.five.service;

import java.util.List;

public class Person {
    String name;
    int age;

    List<Address> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
  
    // Getters and setters (or lombok annotations) go here
  }


// public class Person extends AddressExtends {
//     String name;
//     int age;


//     public String getName() {
//       return name;
//     }

//     public void setName(String name) {
//       this.name = name;
//     }

//     public int getAge() {
//       return age;
//     }

//     public void setAge(int age) {
//       this.age = age;
//     }


//     // Getters and setters (or lombok annotations) go here
//   }
